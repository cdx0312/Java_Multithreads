import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by cdxu0 on 2017/7/10.
 */
public class ThreadCooperation {
    private static Account account = new Account();

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(new DepositTask());
        executor.execute(new WitthDrawTask());
        executor.shutdown();

        System.out.println("Thread 1\t\t Thread 2\t\tBalance");
    }

    public static class DepositTask implements Runnable {
        @Override
        public void run(){
            try {
                while (true) {
                    account.deposit((int)(Math.random() * 10) + 1);
                    Thread.sleep(1000);
                }
            }
            catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }
    }

    public static class WitthDrawTask implements Runnable {
        @Override
        public void run() {
            while (true) {
                account.withdraw((int)(Math.random()*10) + 1);
            }
        }
    }

    private static class Account {
        //create a new lock
        private static Lock lock = new ReentrantLock();
        //create a condition
        private static Condition newDeposit = lock.newCondition();

        private int balance = 0;

        public int getBalance(){
            return balance;
        }

        public void withdraw(int amount) {
            lock.lock();
            try {
                while (balance < amount) {
                    System.out.println("\t\t\twait for deposit");
                    newDeposit.await();
                }
                balance = balance - amount;
                System.out.println("\t\t\tWithdraw " + amount + "\t\t" + getBalance());
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void deposit(int amount) {
            lock.lock();
            try {
                balance += amount;
                System.out.println("Deposit " + amount + "\t\t\t\t\t" + getBalance());
                //signal thread waiting on the condition
                newDeposit.signal();
            } finally {
                lock.unlock();
            }
        }
    }
}

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by cdxu0 on 2017/7/10.
 */
public class AccountWithSyncUsingLock {
    private static Account account = new Account();

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            executor.execute(new AddPennyTask());
        }
        executor.shutdown();

        while (!executor.isTerminated()){
        }
        System.out.println("what is balance? "  + account.getBalancee());
    }

    private static class AddPennyTask implements Runnable {
        public void run() {
            account.deposit(1);
        }
    }

    private static class Account {
        private static Lock lock = new ReentrantLock();    //create a lock
        private int balancee = 0;

        public int getBalancee() {
            return balancee;
        }

        public void deposit(int amount) {
            lock.lock();                                   //acquire lock
            try {
                int newBalance = balancee + amount;
                Thread.sleep(5);
                balancee = newBalance;
            } catch (InterruptedException ex){
            } finally {
                lock.unlock();                             //release lock
            }
        }
    }
}


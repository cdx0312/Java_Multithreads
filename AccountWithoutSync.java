import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by cdxu0 on 2017/7/9.
 */
public class AccountWithoutSync {
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
        private int balancee = 0;

        public int getBalancee() {
            return balancee;
        }

        public void deposit(int amount) {
            int newBalance = balancee + amount;
            try {
                Thread.sleep(5);
            }
            catch (InterruptedException ex){
            }
            balancee = newBalance;
        }
    }
}

/*
what is balance? 10
 */

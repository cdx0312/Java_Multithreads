import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Created by cdxu0 on 2017/7/11.
 */
public class ParallelMax {

    public static void main(String[] args) {
        final int N = 9000000;
        int[] list = new int[N];
        for (int i = 0; i < N; i++)
            list[i] = i;
        long startTime = System.currentTimeMillis();
        System.out.println("\nThe maximum number is " + max(list));
        long endTime = System.currentTimeMillis();
        System.out.println("The number of processor is " + Runtime.getRuntime().availableProcessors());
        System.out.println("Time is " + (endTime - startTime) + " milliseconds");
        startTime = System.currentTimeMillis();
        int max = list[0];
        for (int i = 1; i < N; i++)
            if (list[i] > max)
                max = list[i];
        endTime = System.currentTimeMillis();
        System.out.println("The time spend with one loop is " + (endTime - startTime) + " ms");
    }

    public static int max(int[] list) {
        RecursiveTask<Integer> task = new MaxTask(list,0,list.length);
        ForkJoinPool pool = new ForkJoinPool();
        return pool.invoke(task);
    }

    private static class MaxTask extends RecursiveTask<Integer> {
        private final static int THRESHOLD = 1000;
        private int[] list;
        private int low;
        private int high;

        public MaxTask(int[] list, int low, int high) {
            this.list =list;
            this.low = low;
            this.high = high;
        }

        @Override
        public Integer compute() {
            if (high - low < THRESHOLD) {
                int max = list[low];
                for (int i = low; i < high; i++)
                    if (list[i] > max)
                        max = list[i];
                return new Integer(max);
            } else {
                int mid = (low + high) / 2;
                RecursiveTask<Integer> left = new MaxTask(list,low,mid);
                RecursiveTask<Integer> right = new MaxTask(list,mid,high);
                right.fork();
                left.fork();
                return new Integer(Math.max(left.join().intValue(),right.join().intValue()));
            }
        }
    }
}

/*
The maximum number is 8999999
The number of processor is 4
Time is 51 milliseconds
The time spend with one loop is 89 ms
 */

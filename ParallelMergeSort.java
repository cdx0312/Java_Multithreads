import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Created by cdxu0 on 2017/7/10.
 */
public class ParallelMergeSort {
    public static void main(String[] args) {
        final int SIZE = 7000000;
        int[] list1 = new int[SIZE];
        int[] list2 = new int[SIZE];

        for (int i = 0; i < list1.length; i++)
            list1[i] = list2[i] = (int)(Math.random()*10000000);
        long startTime = System.currentTimeMillis();
        parallelMergeSort(list1);
        long endTime = System.currentTimeMillis();
        System.out.println("\nParallel time with " + Runtime.getRuntime().availableProcessors()
                + " procesors is " + (endTime - startTime) + " ms");

        startTime = System.currentTimeMillis();
        MergeSort.mergeSort(list2);
        endTime = System.currentTimeMillis();
        System.out.println("\nSequential time is " + (endTime - startTime) + " ms");
    }

    public static void parallelMergeSort(int[] list) {
        RecursiveAction mainTask = new SortTask(list);          //定义一个结束返回值的任务
        ForkJoinPool pool = new ForkJoinPool();                //使用全部可用的处理器来创建一个ForkJoinPool
        pool.invoke(mainTask);                                  //执行任务，并在结束的时候返回其结果
    }

    private static class SortTask extends RecursiveAction {
        private final int THRESHOLD = 500;
        private int[] list;

        SortTask(int[] list) {
            this.list = list;
        }

        @Override
        protected void compute() {
            if (list.length < THRESHOLD)
                Arrays.sort(list);
            else {
                int[] firstHalf = new int[list.length / 2];
                System.arraycopy(list, 0, firstHalf, 0, list.length/2);
                int[] secondHalf = new int[list.length - list.length / 2];
                System.arraycopy(list,list.length/2,secondHalf,0,list.length - list.length/2);
                //分解给定的任务，并继续执行
                invokeAll(new SortTask(firstHalf), new SortTask(secondHalf));
                MergeSort.merge(firstHalf,secondHalf,list);
            }
        }
    }
}

/*
Parallel time with 4 procesors is 1494 ms

Sequential time is 3292 ms
 */

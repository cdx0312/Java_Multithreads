/**
 * Created by cdxu0 on 2017/7/4.
 */
public class MergeSort {
    public static void main(String[] args) {
        int[] list = {2,12,322,45,123,456,121,35,1,456,213,486,1323,25};
        mergeSort(list);
        print(list);
    }

    public static void mergeSort(int[] list) {
        if (list.length > 1) {
            int[] firstHalf = new int[list.length/2];
            System.arraycopy(list, 0,firstHalf, 0, list.length/2);
            mergeSort(firstHalf);

            int[] seconedHalf = new int[list.length - list.length/2];
            System.arraycopy(list,list.length/2, seconedHalf,0,list.length-list.length/2);
            mergeSort(seconedHalf);
            merge(firstHalf, seconedHalf, list);
        }
    }

    public static void merge(int[] list1, int[] list2, int[] temp) {
        int curr1 = 0, curr2 = 0, curr3 = 0;
        while (curr1 < list1.length && curr2 < list2.length) {
            if (list1[curr1] < list2[curr2])
                temp[curr3++] = list1[curr1++];
            else
                temp[curr3++] = list2[curr2++];
        }
        while (curr1 < list1.length)
            temp[curr3++] = list1[curr1++];
        while (curr2 < list2.length)
            temp[curr3++] = list2[curr2++];
    }

    public static void print(int[] list){
        for (int i = 0; i < list.length; i++)
            System.out.print(list[i] + " ");
        System.out.println();
    }
}

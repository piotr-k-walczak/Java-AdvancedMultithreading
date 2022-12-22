package sort;

import java.util.concurrent.ForkJoinTask;

public class Sort {

    public static <T extends Comparable> T[] mergeSort(T[] array) {
        ForkJoinTask.invokeAll(new MergeSortTask<>(array));
        return array;
    }

    public static <T extends Comparable> T[] quickSort(T[] array) {
        ForkJoinTask.invokeAll(new QuickSortTask<>(array));
        return array;
    }
}

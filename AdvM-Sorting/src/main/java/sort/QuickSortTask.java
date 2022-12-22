package sort;

import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class QuickSortTask<T extends Comparable> extends RecursiveAction {
    private T[] array;
    private int left, right;

    public QuickSortTask(T[] array) {
        this.array = array;
        this.left = 0;
        this.right = array.length - 1;
    }

    public QuickSortTask(T[] array, int left, int right) {
        this.array = array;
        this.left = left;
        this.right = right;
    }

    @Override
    protected void compute() {
        ForkJoinTask.invokeAll(createSubtasks());
    }

    private List<QuickSortTask<T>> createSubtasks(){
        if(left < right){
            int pivot = partition(array, left, right);
            return List.of(
                    new QuickSortTask<>(array,left, pivot),
                    new QuickSortTask<>(array, pivot + 1, right));
        }
        return List.of();
    }

    private int partition(T[] array, int low, int high) {
        T pivot = array[low];
        int i = low - 1;
        int j  = high + 1;
        while (true){
            do {
                i++;
            }
            while (array[i].compareTo(pivot) < 0);

            do {
                j--;
            }
            while (array[j].compareTo(pivot) > 0);
            if (i >= j) {
                return j;
            }

            swap(array, i, j);
        }
    }

    private void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}

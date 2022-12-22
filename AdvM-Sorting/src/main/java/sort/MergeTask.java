package sort;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeTask<T extends Comparable> extends RecursiveAction {

    private final T[] array;
    private final int left;
    private final int middle;
    private final int right;

    public MergeTask(T[] array, int left, int middle, int right) {
        this.array = array;
        this.left = left;
        this.middle = middle;
        this.right = right;
    }

    @Override
    protected void compute() {
        T[] leftArray = Arrays.copyOfRange(array, left, middle + 1);
        T[] rightArray = Arrays.copyOfRange(array, middle + 1, right + 1);

        int i = 0;
        int j = 0;
        int index = left;

        while (i < leftArray.length && j < rightArray.length) {
            if (leftArray[i].compareTo(rightArray[j]) <= 0) {
                array[index] = leftArray[i];
                i++;
            } else {
                array[index] = rightArray[j];
                j++;
            }
            index++;
        }
        while (i < leftArray.length) {
            array[index] = leftArray[i];
            i++;
            index++;
        }

        while (j < rightArray.length) {
            array[index] = rightArray[j];
            j++;
            index++;
        }
    }
}

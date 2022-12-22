package sort;

import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class MergeSortTask<T extends Comparable> extends RecursiveAction {

    private final T[] array;
    private final int left;
    private final int right;

    public MergeSortTask(T[] array){
        this(array, 0, array.length - 1);
    }

    public MergeSortTask(T[] array, int left, int right) {
        this.array = array;
        this.left = left;
        this.right = right;
    }

    @Override
    protected void compute() {
        ForkJoinTask.invokeAll(createSubtasks());
    }

    private List<RecursiveAction> createSubtasks() {
        if (left < right) {
            int middle = (right + left) / 2;
            return List.of(
                    new MergeSortTask<>(array, left, middle),
                    new MergeSortTask<>(array, middle + 1, right),
                    new MergeTask<>(array, left, middle, right)
            );
        }
        return List.of();
    }
}


package factorial;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class FactorialRecursiveTask extends RecursiveTask<BigInteger> {

    public final BigInteger THRESHOLD = BigInteger.valueOf(10000);
    private final BigInteger start;
    private final BigInteger end;

    public FactorialRecursiveTask(BigInteger factorial) {
        this.start = BigInteger.ONE;
        this.end = factorial;
    }

    public FactorialRecursiveTask(BigInteger start, BigInteger end) {
        this.start = start;
        this.end = end;
    }

    public static BigInteger calc(BigInteger factorial) {
        FactorialRecursiveTask task = new FactorialRecursiveTask(factorial);
        return task.compute();
    }

    @Override
    protected BigInteger compute() {
        if (end.subtract(start).compareTo(THRESHOLD) < 0) {
            return ForkJoinTask.invokeAll(createSubtasks())
                    .stream()
                    .map(ForkJoinTask::join)
                    .reduce(BigInteger.ONE, BigInteger::multiply);
        } else {
            return process(start, end);
        }
    }

    private List<FactorialRecursiveTask> createSubtasks() {
        return List.of(
                new FactorialRecursiveTask(start, start.add(end).divide(BigInteger.TWO)),
                new FactorialRecursiveTask(start.add(end).divide(BigInteger.TWO).add(BigInteger.ONE), end)
        );
    }

    private BigInteger process(BigInteger start, BigInteger end) {
        BigInteger res = BigInteger.ONE;
        for (BigInteger i = start; i.compareTo(end) <= 0; i = i.add(BigInteger.ONE)) {
            res = res.multiply(i);
        }
        return res;
    }
}

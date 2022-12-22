package factorial;

import org.openjdk.jmh.annotations.Benchmark;

import java.math.BigInteger;

public class Benchmarks {

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }


    @Benchmark
    public void forkJoinFactorial() {
        FactorialRecursiveTask.calc(BigInteger.valueOf(98765));
    }

    @Benchmark
    public void regularFactorial() {
        RegularFactorial.calc(BigInteger.valueOf(98765));
    }
}

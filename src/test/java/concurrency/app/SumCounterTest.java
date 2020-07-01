package concurrency.app;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class SumCounterTest {
    private static final long SUM_OF_NUMBERS = 500000500000L;
    private static final long AMOUNT_OF_NUMBERS = 1000000L;
    private static final int AMOUNT_OF_THREADS = 10000;
    private List<Long> numbers;

    @Before
    public void setNumbers() {
        numbers = LongStream.rangeClosed(1, AMOUNT_OF_NUMBERS)
                .boxed()
                .collect(Collectors.toList());
    }

    @Test
    public void countSumForkJoinIsOk () {
        SumForkJoinCounter sumForkJoinCounter = new SumForkJoinCounter(numbers);
        long actuallySumOfNumbers = sumForkJoinCounter.compute();
        Assert.assertEquals(SUM_OF_NUMBERS, actuallySumOfNumbers);
    }

    @Test
    public void countSumExecutorServiceIsOk () {
        SumExecutorServiceCounter executorServiceCounter = new SumExecutorServiceCounter(AMOUNT_OF_THREADS, numbers);
        long actuallySumOfNumbers = executorServiceCounter.execute();
        Assert.assertEquals(SUM_OF_NUMBERS, actuallySumOfNumbers);
    }
}

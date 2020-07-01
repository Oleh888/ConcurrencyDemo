package concurrency.app;

import org.junit.Assert;
import org.junit.Test;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class SumCounterTest {
    private final long SUM_OF_NUMBERS = 500000500000L;
    private final long AMOUNT_OF_NUMBERS = 1000000L;

    @Test
    public void countSumIsOk () {
        List<Long> numbers = LongStream.rangeClosed(1, AMOUNT_OF_NUMBERS)
                .boxed()
                .collect(Collectors.toList());
        SumForkJoinCounter sumForkJoinCounter = new SumForkJoinCounter(numbers);
        long startTime = System.currentTimeMillis();
        long actuallySumOfInt = sumForkJoinCounter.compute();
        Assert.assertEquals(SUM_OF_NUMBERS, actuallySumOfInt);
        System.out.println("For calculation was spent " + (System.currentTimeMillis() - startTime) + " milliseconds");
    }
}

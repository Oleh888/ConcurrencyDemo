package concurrency.app;

import java.util.List;
import java.util.concurrent.Callable;

public class ListSumThread implements Callable<Long> {
    private final int startIndex;
    private final int endIndex;
    private final List<Long> numbers;

    public ListSumThread(int startIndex, int endIndex, List<Long> numbers) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.numbers = numbers;
    }

    @Override
    public Long call() throws Exception {
        int currentIndex = startIndex;
        long sum = 0;
        while (currentIndex < endIndex) {
            sum += numbers.get(currentIndex);
            currentIndex++;
        }
        return sum;
    }
}

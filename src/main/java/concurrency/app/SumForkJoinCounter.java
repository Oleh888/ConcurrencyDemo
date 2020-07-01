package concurrency.app;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class SumForkJoinCounter extends RecursiveTask<Long> {
    private static final int THRESHOLD = 10;
    private final List<Long> numbers;

    public SumForkJoinCounter(List<Long> numbers) {
        super();
        this.numbers = numbers;
    }

    @Override
    protected Long compute() {
        if (numbers.size() - 1 > THRESHOLD) {
            return ForkJoinTask.invokeAll(createSubTasks(numbers.size() - 1))
                    .stream()
                    .mapToLong(ForkJoinTask::join)
                    .sum();
        }
        return numbers.stream().mapToLong(x -> x).sum();
    }

    private Collection<RecursiveTask<Long>> createSubTasks(int end) {
        final List<RecursiveTask<Long>> dividedTasks = new ArrayList<>();
        List<Long> firstHalfOfNumbers = LongStream
                .rangeClosed(numbers.get(0), numbers.get((end) / 2))
                .boxed()
                .collect(Collectors.toList());
        List<Long> secondHalfOfNumbers = LongStream
                .rangeClosed(numbers.get((end) / 2 + 1), numbers.get(end))
                .boxed()
                .collect(Collectors.toList());
        dividedTasks.add(new SumForkJoinCounter(firstHalfOfNumbers));
        dividedTasks.add(new SumForkJoinCounter(secondHalfOfNumbers));
        return dividedTasks;
    }
}

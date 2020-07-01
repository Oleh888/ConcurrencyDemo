package concurrency.app;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

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
        dividedTasks.add(new SumForkJoinCounter(numbers.subList(0, numbers.size() / 2)));
        dividedTasks.add(new SumForkJoinCounter(numbers.subList(numbers.size() / 2, numbers.size())));
        return dividedTasks;
    }
}

package concurrency.app;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class SumForkJoinCounter extends RecursiveTask<Long> {
    private static final int THRESHOLD = 1000;
    private final List<Long> numbers;

    public SumForkJoinCounter(List<Long> numbers) {
        super();
        this.numbers = numbers;
    }

    @Override
    protected Long compute() {
        if (numbers.size() - 1 > THRESHOLD) {
            return ForkJoinTask.invokeAll(createSubTasks())
                    .stream()
                    .mapToLong(ForkJoinTask::join)
                    .sum();
        }
        return numbers.stream().reduce(0L, Long::sum);
    }

    private Collection<RecursiveTask<Long>> createSubTasks() {
        final List<RecursiveTask<Long>> dividedTasks = new ArrayList<>();
        dividedTasks.add(new SumForkJoinCounter(numbers.subList(0, numbers.size() / 2)));
        dividedTasks.add(new SumForkJoinCounter(numbers.subList(numbers.size() / 2,
                numbers.size())));
        return dividedTasks;
    }
}

package concurrency.app;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SumExecutorServiceCounter {
    private final int executor;
    private final List<Long> numbers;

    public SumExecutorServiceCounter(int executor, List<Long> numbers) {
        this.executor = executor;
        this.numbers = numbers;
    }

    public Long execute() {
        ExecutorService executorService = Executors.newFixedThreadPool(executor);
        List<Future<Long>> results = new ArrayList<Future<Long>>(executor);
        for (int i = 0; i < executor; i++) {
            int potionOfNumbers = numbers.size() / executor;
            Callable<Long> task = new ListSumThread(potionOfNumbers * i,
                    potionOfNumbers * i + potionOfNumbers, numbers);
            results.add(executorService.submit(task));
        }
        return sum(results);
    }

    private long sum(List<Future<Long>> results) {
        long sum = 0;
        for (Future<Long> result : results) {
            try {
                sum += result.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException("Can not count sum of the list of Future ", e);
            }
        }
        return sum;
    }
}

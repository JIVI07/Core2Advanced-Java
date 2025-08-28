// ExecutorServiceDemo.java
// Demonstrating Java ExecutorService framework with various examples

import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorServiceDemo {
    
    private static final AtomicInteger taskCounter = new AtomicInteger(1);
    private static final Random random = new Random();
    
    public static void main(String[] args) {
        System.out.println("ExecutorService Demo Started\n");
        
        try {
            // Demo 1: SingleThreadExecutor
            demoSingleThreadExecutor();
            
            // Demo 2: FixedThreadPool
            demoFixedThreadPool();
            
            // Demo 3: CachedThreadPool
            demoCachedThreadPool();
            
            // Demo 4: ScheduledThreadPool
            demoScheduledThreadPool();
            
            // Demo 5: Callable and Future
            demoCallableAndFuture();
            
            // Demo 6: InvokeAll and InvokeAny
            demoInvokeAllAndInvokeAny();
            
            // Demo 7: CompletionService
            demoCompletionService();
            
            // Demo 8: Custom ThreadPoolExecutor
            demoCustomThreadPool();
            
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        
        System.out.println("\nExecutorService Demo Completed");
    }
    
    // Simple Runnable task
    static class SimpleTask implements Runnable {
        private final int taskId;
        
        public SimpleTask(int taskId) {
            this.taskId = taskId;
        }
        
        @Override
        public void run() {
            System.out.println("Task " + taskId + " started by " + 
                             Thread.currentThread().getName());
            try {
                // Simulate some work
                Thread.sleep(1000 + random.nextInt(1000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Task " + taskId + " completed by " + 
                             Thread.currentThread().getName());
        }
    }
    
    // Callable task that returns a result
    static class CalculationTask implements Callable<Integer> {
        private final int number;
        
        public CalculationTask(int number) {
            this.number = number;
        }
        
        @Override
        public Integer call() throws Exception {
            System.out.println("Calculating square of " + number + " by " + 
                             Thread.currentThread().getName());
            
            // Simulate some computation time
            Thread.sleep(500 + random.nextInt(500));
            
            if (number % 7 == 0) {
                throw new RuntimeException("Bad number: " + number);
            }
            
            return number * number;
        }
    }
    
    // Demo 1: SingleThreadExecutor
    private static void demoSingleThreadExecutor() throws InterruptedException {
        System.out.println("=== Demo 1: SingleThreadExecutor ===");
        
        ExecutorService executor = Executors.newSingleThreadExecutor();
        
        for (int i = 0; i < 5; i++) {
            executor.execute(new SimpleTask(taskCounter.getAndIncrement()));
        }
        
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println("SingleThreadExecutor demo completed\n");
    }
    
    // Demo 2: FixedThreadPool
    private static void demoFixedThreadPool() throws InterruptedException {
        System.out.println("=== Demo 2: FixedThreadPool (4 threads) ===");
        
        ExecutorService executor = Executors.newFixedThreadPool(4);
        
        for (int i = 0; i < 8; i++) {
            executor.execute(new SimpleTask(taskCounter.getAndIncrement()));
        }
        
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println("FixedThreadPool demo completed\n");
    }
    
    // Demo 3: CachedThreadPool
    private static void demoCachedThreadPool() throws InterruptedException {
        System.out.println("=== Demo 3: CachedThreadPool ===");
        
        ExecutorService executor = Executors.newCachedThreadPool();
        
        for (int i = 0; i < 10; i++) {
            executor.execute(new SimpleTask(taskCounter.getAndIncrement()));
        }
        
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println("CachedThreadPool demo completed\n");
    }
    
    // Demo 4: ScheduledThreadPool
    private static void demoScheduledThreadPool() throws InterruptedException {
        System.out.println("=== Demo 4: ScheduledThreadPool ===");
        
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        
        // Schedule a task to run after 2 seconds delay
        scheduler.schedule(() -> {
            System.out.println("Scheduled task executed after 2 seconds by " + 
                             Thread.currentThread().getName());
        }, 2, TimeUnit.SECONDS);
        
        // Schedule a task to run repeatedly every 1 second after initial 1 second delay
        ScheduledFuture<?> periodicTask = scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Periodic task executed by " + 
                             Thread.currentThread().getName());
        }, 1, 1, TimeUnit.SECONDS);
        
        // Schedule the periodic task to stop after 5 seconds
        scheduler.schedule(() -> {
            periodicTask.cancel(false);
            System.out.println("Periodic task cancelled");
        }, 5, TimeUnit.SECONDS);
        
        Thread.sleep(6000);
        scheduler.shutdown();
        System.out.println("ScheduledThreadPool demo completed\n");
    }
    
    // Demo 5: Callable and Future
    private static void demoCallableAndFuture() throws InterruptedException, ExecutionException {
        System.out.println("=== Demo 5: Callable and Future ===");
        
        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Future<Integer>> futures = new ArrayList<>();
        
        for (int i = 1; i <= 6; i++) {
            Future<Integer> future = executor.submit(new CalculationTask(i));
            futures.add(future);
        }
        
        System.out.println("All tasks submitted, waiting for results...");
        
        for (int i = 0; i < futures.size(); i++) {
            try {
                Integer result = futures.get(i).get();
                System.out.println("Result for task " + (i + 1) + ": " + result);
            } catch (ExecutionException e) {
                System.out.println("Task " + (i + 1) + " failed: " + e.getCause().getMessage());
            }
        }
        
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("Callable and Future demo completed\n");
    }
    
    // Demo 6: InvokeAll and InvokeAny
    private static void demoInvokeAllAndInvokeAny() throws InterruptedException, ExecutionException {
        System.out.println("=== Demo 6: InvokeAll and InvokeAny ===");
        
        ExecutorService executor = Executors.newFixedThreadPool(4);
        List<Callable<Integer>> tasks = new ArrayList<>();
        
        for (int i = 1; i <= 5; i++) {
            tasks.add(new CalculationTask(i * 2));
        }
        
        // invokeAll - waits for all tasks to complete
        System.out.println("Using invokeAll():");
        List<Future<Integer>> allResults = executor.invokeAll(tasks);
        
        for (Future<Integer> future : allResults) {
            try {
                System.out.println("Result: " + future.get());
            } catch (ExecutionException e) {
                System.out.println("Task failed: " + e.getCause().getMessage());
            }
        }
        
        // invokeAny - returns the result of the first completed task
        System.out.println("\nUsing invokeAny():");
        Integer firstResult = executor.invokeAny(tasks);
        System.out.println("First completed task result: " + firstResult);
        
        executor.shutdown();
        System.out.println("InvokeAll and InvokeAny demo completed\n");
    }
    
    // Demo 7: CompletionService
    private static void demoCompletionService() throws InterruptedException, ExecutionException {
        System.out.println("=== Demo 7: CompletionService ===");
        
        ExecutorService executor = Executors.newFixedThreadPool(3);
        CompletionService<Integer> completionService = new ExecutorCompletionService<>(executor);
        
        for (int i = 1; i <= 6; i++) {
            completionService.submit(new CalculationTask(i));
        }
        
        System.out.println("Processing results as they complete:");
        
        for (int i = 0; i < 6; i++) {
            Future<Integer> completedFuture = completionService.take();
            try {
                Integer result = completedFuture.get();
                System.out.println("Completed task result: " + result);
            } catch (ExecutionException e) {
                System.out.println("Completed task failed: " + e.getCause().getMessage());
            }
        }
        
        executor.shutdown();
        System.out.println("CompletionService demo completed\n");
    }
    
    // Demo 8: Custom ThreadPoolExecutor
    private static void demoCustomThreadPool() throws InterruptedException {
        System.out.println("=== Demo 8: Custom ThreadPoolExecutor ===");
        
        int corePoolSize = 2;
        int maxPoolSize = 4;
        long keepAliveTime = 10L;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(10);
        
        ThreadPoolExecutor customExecutor = new ThreadPoolExecutor(
            corePoolSize,
            maxPoolSize,
            keepAliveTime,
            TimeUnit.SECONDS,
            workQueue,
            new ThreadPoolExecutor.CallerRunsPolicy() // Handler for rejected tasks
        );
        
        // Monitor thread pool statistics
        ScheduledExecutorService monitor = Executors.newSingleThreadScheduledExecutor();
        monitor.scheduleAtFixedRate(() -> {
            System.out.printf("Pool stats: Active=%d, Queue=%d, Completed=%d%n",
                customExecutor.getActiveCount(),
                customExecutor.getQueue().size(),
                customExecutor.getCompletedTaskCount());
        }, 0, 500, TimeUnit.MILLISECONDS);
        
        for (int i = 0; i < 15; i++) {
            final int taskId = taskCounter.getAndIncrement();
            customExecutor.execute(() -> {
                System.out.println("Custom pool task " + taskId + " started by " + 
                                 Thread.currentThread().getName());
                try {
                    Thread.sleep(800 + random.nextInt(400));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Custom pool task " + taskId + " completed");
            });
        }
        
        customExecutor.shutdown();
        customExecutor.awaitTermination(15, TimeUnit.SECONDS);
        monitor.shutdown();
        System.out.println("Custom ThreadPoolExecutor demo completed\n");
    }
}

// Additional examples for complex scenarios
class AdvancedExecutorServiceExamples {
    
    // Example of using CountDownLatch with ExecutorService
    public static void demoCountDownLatch() throws InterruptedException {
        System.out.println("=== Advanced: CountDownLatch with ExecutorService ===");
        
        ExecutorService executor = Executors.newFixedThreadPool(3);
        CountDownLatch latch = new CountDownLatch(3);
        
        for (int i = 0; i < 3; i++) {
            final int taskId = i + 1;
            executor.execute(() -> {
                try {
                    System.out.println("Task " + taskId + " started");
                    Thread.sleep(1000 + new Random().nextInt(1000));
                    System.out.println("Task " + taskId + " completed");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            });
        }
        
        // Wait for all tasks to complete
        latch.await();
        System.out.println("All tasks completed, main thread continues");
        
        executor.shutdown();
    }
    
    // Example of using CompletableFuture (Java 8+)
    public static void demoCompletableFuture() {
        System.out.println("=== Advanced: CompletableFuture ===");
        
        CompletableFuture.supplyAsync(() -> {
            System.out.println("Async computation started");
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
            return 42;
        })
        .thenApply(result -> result * 2)
        .thenAccept(result -> System.out.println("Final result: " + result))
        .thenRun(() -> System.out.println("All operations completed"));
    }
}

// Main class to run all demos
class RunAllDemos {
    public static void main(String[] args) throws Exception {
        ExecutorServiceDemo.main(args);
        AdvancedExecutorServiceExamples.demoCountDownLatch();
        AdvancedExecutorServiceExamples.demoCompletableFuture();
    }
}
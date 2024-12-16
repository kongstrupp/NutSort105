import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

public class InterruptResetExample {
    public static void main(String[] args) {
        AtomicReference<ExecutorService> executor = new AtomicReference<>(Executors.newSingleThreadExecutor()); // Single thread for the function
        Runnable interruptableFunction = () -> {
            try {
                nestedFunction();
            } catch (InterruptedException e) {
                System.out.println("Function interrupted!");
            }
        };

        Thread interrupterThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(2000); // Wait for 2 seconds
                    System.out.println("Restarting function...");
                    executor.get().shutdownNow(); // Interrupt and stop current execution
                    executor.set(Executors.newSingleThreadExecutor()); // Restart the thread pool
                    executor.get().submit(interruptableFunction); // Restart the function
                } catch (InterruptedException e) {
                    System.out.println("Interrupter thread stopped.");
                    break;
                }
            }
        });

        executor.get().submit(interruptableFunction); // Start the function
        interrupterThread.start();
    }

    private static void nestedFunction() throws InterruptedException {
        while (true) { // Outer loop
            System.out.println("In outer loop...");
            Thread.sleep(500); // Simulate work

            while (true) { // Nested loop
                System.out.println("In nested loop...");
                Thread.sleep(500); // Simulate work
            }
        }
    }
}

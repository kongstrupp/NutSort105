public class ThreadInterruptionExample {
    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    System.out.println("Thread 1 is running...");
                    Thread.sleep(500); // Simulates some work
                } catch (InterruptedException e) {
                    System.out.println("Thread 1 interrupted during sleep!");
                    Thread.currentThread().interrupt(); // Preserve interrupted status
                }
            }
            System.out.println("Thread 1 exiting.");
        });

        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(2000); // Wait for 2 seconds
                System.out.println("Thread 2 interrupting Thread 1...");
                thread1.interrupt();
            } catch (InterruptedException e) {
                System.out.println("Thread 2 interrupted!");
            }
        });

        thread1.start();
        thread2.start();
    }
}


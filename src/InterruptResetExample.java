public class InterruptResetExample {
    public static void main(String[] args) {
        Thread loopThread = new Thread(() -> {
            while (true) {
                boolean restart = runLoop();
                if (!restart) {
                    break; // Exit the outer loop if restarting is not required
                }
            }
        });

        Thread interrupterThread = new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(2000); // Wait for 2 seconds
                    loopThread.interrupt(); // Interrupt the loop thread
                }
            } catch (InterruptedException e) {
                System.out.println("Interrupter thread stopped.");
            }
        });

        loopThread.start();
        interrupterThread.start();
    }

    public static boolean runLoop() {
        int counter = 0; // Initialize the counter
        try {
            while (true) { // Outer loop
                System.out.println("Outer loop: Counter " + counter);

                while (true) { // Inner loop
                    counter++;
                    System.out.println(counter);
                    Thread.sleep(100); // Simulate work in the inner loop
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted! Exiting both loops and restarting.");
            return true; // Restart the entire function
        }
    }
}

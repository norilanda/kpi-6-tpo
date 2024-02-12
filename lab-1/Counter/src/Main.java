public class Main {
    public static void main(String[] args) {
        var iterationsNumber = 100_000;

        System.out.println("Async");
        changeCounterAsync(iterationsNumber);
        System.out.println("\nSync Method");
        changeCounterSyncMethod(iterationsNumber);
        System.out.println("\nSync Block");
        changeCounterSyncBlock(iterationsNumber);
        System.out.println("\nSync Lock Object");
        changeCounterSyncLockObject(iterationsNumber);
    }

    private static void changeCounter(Counter counter, Thread threadIncrement, Thread threadDecrement) {
        threadIncrement.start();
        threadDecrement.start();

        try {
            threadIncrement.join();
            threadDecrement.join();
        } catch (InterruptedException e) {
            System.out.println("Error during joining");
        }

        System.out.println("Counter value: " + counter.getValue());
    }

    private static void changeCounterAsync(int iterationsNumber) {
        var counter = new Counter();
        var threadIncrement = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < iterationsNumber; i++) {
                    counter.increment();
                }
            }
        });

        var threadDecrement = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < iterationsNumber; i++) {
                    counter.decrement();
                }
            }
        });
        changeCounter(counter, threadIncrement, threadDecrement);
    }

    private static void changeCounterSyncMethod(int iterationsNumber) {
        var counter = new Counter();
        var threadIncrement = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < iterationsNumber; i++) {
                    counter.incrementSyncMethod();
                }
            }
        });

        var threadDecrement = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < iterationsNumber; i++) {
                    counter.decrementSyncMethod();
                }
            }
        });
        changeCounter(counter, threadIncrement, threadDecrement);
    }

    private static void changeCounterSyncBlock(int iterationsNumber) {
        var counter = new Counter();
        var threadIncrement = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < iterationsNumber; i++) {
                    counter.incrementSyncBlock();
                }
            }
        });

        var threadDecrement = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < iterationsNumber; i++) {
                    counter.decrementSyncBlock();
                }
            }
        });
        changeCounter(counter, threadIncrement, threadDecrement);
    }
    private static void changeCounterSyncLockObject(int iterationsNumber) {
        var counter = new Counter();
        var threadIncrement = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < iterationsNumber; i++) {
                    synchronized (counter) {
                        counter.increment();
                    }
                }
            }
        });

        var threadDecrement = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < iterationsNumber; i++) {
                    synchronized (counter) {
                        counter.decrement();
                    }
                }
            }
        });
        changeCounter(counter, threadIncrement, threadDecrement);
    }
}
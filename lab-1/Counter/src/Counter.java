public class Counter {
    private int value = 0;
    private final Object lock = new Object();

    public int getValue() {
        return value;
    }

    public void increment() {
        value += 1;
    }

    public void decrement() {
        value -= 1;
    }

    public synchronized void incrementSyncMethod() {
        value += 1;
    }

    public synchronized void decrementSyncMethod() {
        value -= 1;
    }

    public void incrementSyncBlock() {
        synchronized (lock) {
            value += 1;
        }
    }

    public synchronized void decrementSyncBlock() {
        synchronized (lock) {
            value -= 1;
        }
    }
}

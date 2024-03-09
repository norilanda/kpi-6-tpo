package ProducerConsumerNumbers;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Drop implements IDrop {
    private final int[] buffer;

    private int putIndex = 0, takeIndex = 0, count = 0;

    private Lock locker = new ReentrantLock();
    private Condition elementAdded = locker.newCondition();
    private Condition elementRemoved = locker.newCondition();
    public Drop (int bufferSize) {
        buffer = new int[bufferSize];
    }

    public void put(int number) {
        locker.lock();
        try {
            while (count == buffer.length) {
                try {
                    elementRemoved.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            buffer[putIndex] = number;
            putIndex++;
            count++;
            if(putIndex == buffer.length) {
                putIndex = 0;
            }
            elementAdded.signalAll();
        }
        finally {
            locker.unlock();
        }
    }

    public int take() {
        locker.lock();
        int number;
        try {
            while (count == 0) {
                try {
                    elementAdded.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            number = buffer[takeIndex];
            takeIndex++;
            count--;
            if(takeIndex == buffer.length) {
                takeIndex = 0;
            }
            elementRemoved.signalAll();

            return number;
        }
        finally {
            locker.unlock();
        }
    }
}

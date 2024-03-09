package Bank;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockerBank extends Bank implements IBank{
    private final Lock locker = new ReentrantLock();
    private final Condition hasEnoughMoney = locker.newCondition();
    public LockerBank(int n, int initialBalance) {
        super(n, initialBalance);
    }

    @Override
    public void transfer(int from, int to, int amount) {
        locker.lock();
        try {
            while (accounts[from] < amount) {
                hasEnoughMoney.await();
            }
            accounts[from] -= amount;
            accounts[to] += amount;
            ntransacts++;
            hasEnoughMoney.signalAll();
            if (ntransacts % NTEST == 0)
                test();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            locker.unlock();
        }
    }
}

package Bank;

public class SyncBank extends Bank implements IBank{
    public SyncBank(int n, int initialBalance) {
        super(n, initialBalance);
    }

    @Override
    public synchronized void transfer(int from, int to, int amount) {
        while (accounts[from] < amount) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        accounts[from] -= amount;
        accounts[to] += amount;
        notifyAll();

        ntransacts++;
        if (ntransacts % NTEST == 0)
            test();
    }
}

package Bank;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicBankNotWorking implements IBank{
    public static final int NTEST = 10000;
    protected final AtomicInteger[] accounts;
    protected AtomicInteger ntransacts;
    public AtomicBankNotWorking(int n, int initialBalance) {
        accounts = new AtomicInteger[n];
        int i;
        for (i = 0; i < accounts.length; i++)
            accounts[i] = new AtomicInteger(initialBalance);
        ntransacts = new AtomicInteger(0);
    }

    @Override
    public void transfer(int from, int to, int amount) {
        // This won't help to sync between actions
        // =======================
        accounts[from].getAndAdd(-amount);
        accounts[to].getAndAdd(amount);
        int sum = calculateSum();
        // =======================

        var transactionNumber =  ntransacts.incrementAndGet();
        if (transactionNumber % NTEST == 0)
            test(transactionNumber, sum);
    }

    public synchronized void test(int transactionNumber, int sum) {
        System.out.println("Transactions:" + transactionNumber
                + " Sum: " + sum);
    }

    private int calculateSum() {
        int sum = 0;
        for (int i = 0; i < accounts.length; i++)
            sum += accounts[i].get() ; // get vs getAcquire ??

        return sum;
    }

    public int size(){
        return accounts.length;
    }
}

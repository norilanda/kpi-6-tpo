package Bank;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicBankReference implements IBank{
    protected final AtomicReference<TransactionState> accounts;
    private final int accountNumber;
    public AtomicBankReference(int n, int initialBalance) {

        accountNumber = n;
        accounts = new AtomicReference(new TransactionState(n, initialBalance));
    }

    @Override
    public void transfer(int from, int to, int amount) {
        var updatedAccountState = accounts.updateAndGet(prevArrayState -> {
            var arrayStateCopy =  prevArrayState.accounts.clone();
            
            arrayStateCopy[from] -=amount;
            arrayStateCopy[to] += amount;

            int newTransactionNumber = prevArrayState.transactionNumber + 1;
            return new TransactionState(arrayStateCopy, newTransactionNumber);
        }); // will updatedAccountState be the same object as value of the accounts???

        int sum = calculateSum(updatedAccountState.accounts);

        if (updatedAccountState.shouldDisplayTransaction())
            test(updatedAccountState.transactionNumber, sum);
    }

    public synchronized void test(int transactionNumber, int sum) {
        System.out.println("Transactions:" + transactionNumber
                + " Sum: " + sum);
    }

    private int calculateSum(int[] updatedArray) {
        int sum = 0;
        for (int i = 0; i < accountNumber; i++)
            sum += updatedArray[i];

        return sum;
    }

    public int size(){
        return accountNumber;
    }
}

class TransactionState {
    public static final int NTEST = 10000;
    public int[] accounts;
    int transactionNumber;

    public TransactionState(int n, int initialBalance) {
        accounts = new int[n];
        Arrays.fill(accounts, initialBalance);
        transactionNumber = 0;
    }

    public TransactionState(int[] accounts, int transaction) {
        this.accounts = accounts;
        this.transactionNumber = transaction;
    }

    public boolean shouldDisplayTransaction() {
        return transactionNumber % NTEST == 0;
    }
}
import Bank.AtomicBankReference;
import Bank.IBank;
import Bank.TransferThread;

public class Main {
    public static final int NACCOUNTS = 5;
    public static final int INITIAL_BALANCE = 1000;
    public static final int MAX_AMOUNT_TO_TRANSFER = (int)(INITIAL_BALANCE*1.2);
    public static void main(String[] args) {
//        Bank b = new Bank(NACCOUNTS, INITIAL_BALANCE);
//        Bank b = new SyncBank(NACCOUNTS, INITIAL_BALANCE);
//        Bank b = new LockerBank(NACCOUNTS, INITIAL_BALANCE);
//        IBank b = new AtomicBankNotWorking(NACCOUNTS, INITIAL_BALANCE);
        IBank b = new AtomicBankReference(NACCOUNTS, INITIAL_BALANCE);

        int i;
        for (i = 0; i < NACCOUNTS; i++){
            TransferThread t = new TransferThread(b, i,
                    MAX_AMOUNT_TO_TRANSFER
            );
            t.setPriority(Thread.NORM_PRIORITY + i % 2);
            t.start () ;
        }
    }
}
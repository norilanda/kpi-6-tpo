package Bank;

public class TransferThread extends Thread {
    private IBank bank;
    private int fromAccount;
    private int maxAmount;
    private static final int REPS = 1000;
    public TransferThread(IBank b, int from, int max){
        bank = b;
        fromAccount = from;
        maxAmount = max;
    }
    @Override
    public void run(){
        while (true) {
            for (int i = 0; i < REPS; i++) {
                int toAccount = getRandomAccountIndex();
                int amount = calculateAmountToTransfer(maxAmount);
                bank.transfer(fromAccount, toAccount, amount);
            }
        }
    }

    private int getRandomAccountIndex() {
        return (int) (bank.size() * Math.random());
    }

    private int calculateAmountToTransfer(int maxAmount) {
        return (int) (maxAmount * Math.random());
//        return (int) (maxAmount * Math.random()/REPS);
//        return maxAmount;
    }
}
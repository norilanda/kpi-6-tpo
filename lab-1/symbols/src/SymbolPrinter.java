public class SymbolPrinter implements Runnable{

    protected static int charsPrinted = 0;
    protected static int CHAR_NUMBER = 5_000;
    protected final char symbol;
    public SymbolPrinter(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public void run() {
        for (int i = 0; i < CHAR_NUMBER; i++) {
            System.out.print(symbol);
            charsPrinted++;

            if (charsPrinted > 0 && charsPrinted % 100 == 0) {
                System.out.println();
            }
        }
    }
}

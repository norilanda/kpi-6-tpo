

public class SymbolPrinter {

    protected int charsPrinted = 0;
    protected static int CHAR_NUMBER = 5_000;

    public void PrintSymbol(char symbol) {
        for (int i = 0; i < CHAR_NUMBER; i++) {
            System.out.print(symbol);
            synchronized (this) {
                charsPrinted++;

                if (charsPrinted > 0 && charsPrinted % 100 == 0) {
                    System.out.println();
                }
            }
        }
    }
}

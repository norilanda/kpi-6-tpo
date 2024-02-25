public class SymbolPrinterSyncSleep extends SymbolPrinter{

    @Override
    public void PrintSymbol(char symbol) {
        for (int i = 0; i < CHAR_NUMBER; i++) {
            System.out.print(symbol);
            try {
                Thread.sleep(9);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (this) {
                charsPrinted++;

                if (charsPrinted > 0 && charsPrinted % 100 == 0) {
                    System.out.println();
                }
            }
        }
    }
}

public class SymbolPrinterSyncWaitNotify extends SymbolPrinter{
    private char prevPrintedSymbol = '|';

    @Override
    public synchronized void PrintSymbol(char symbol) {
        for (int i = 0; i < CHAR_NUMBER; i++) {
            while (prevPrintedSymbol == symbol) {
                try {
                    wait();
                } catch (InterruptedException e) {}
            }
            System.out.print(symbol);

            prevPrintedSymbol = symbol;
            charsPrinted++;

            if (charsPrinted > 0 && charsPrinted % 100 == 0) {
                System.out.println();
            }
            notifyAll();
        }
    }
}

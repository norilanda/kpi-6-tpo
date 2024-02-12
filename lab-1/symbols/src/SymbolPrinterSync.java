public class SymbolPrinterSync extends SymbolPrinter{
    public SymbolPrinterSync(char symbol) {
        super(symbol);
    }

    @Override
    public void run() {
        for (int i = 0; i < CHAR_NUMBER; i++) {
            System.out.print(symbol);
            charsPrinted++;

            if (charsPrinted > 0 && charsPrinted % 100 == 0) {
                System.out.println();
            }
            try {
                Thread.sleep(9);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

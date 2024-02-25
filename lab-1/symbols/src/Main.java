public class Main {
    public static void main(String[] args) {
//        printSymbolsAsync();
//        printSymbolsSyncSleep();
        printSymbolsSyncWaitNotify();
    }

    public static void printSymbolsAsync() {
        var printer = new SymbolPrinter();
        var threadHorizontal = new Thread(new Runnable() {
            @Override
            public void run() {
                printer.PrintSymbol('-');
            }
        });
        var threadVertical = new Thread(new Runnable() {
            @Override
            public void run() {
                printer.PrintSymbol('|');
            }
        });

        threadHorizontal.start();
        threadVertical.start();
    }

    public static void printSymbolsSyncSleep() {
        var printer = new SymbolPrinterSyncSleep();
        var threadHorizontal = new Thread(new Runnable() {
            @Override
            public void run() {
                printer.PrintSymbol('-');
            }
        });
        var threadVertical = new Thread(new Runnable() {
            @Override
            public void run() {
                printer.PrintSymbol('|');
            }
        });

        threadHorizontal.start();
        threadVertical.start();
    }

    public static void printSymbolsSyncWaitNotify() {
        var printer = new SymbolPrinterSyncWaitNotify();

        var threadHorizontal = new Thread(new Runnable() {
            @Override
            public void run() {
                printer.PrintSymbol('-');
            }
        });
        var threadVertical = new Thread(new Runnable() {
            @Override
            public void run() {
                printer.PrintSymbol('|');
            }
        });

        threadHorizontal.start();
        threadVertical.start();
    }
}
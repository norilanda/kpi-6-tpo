public class Main {
    public static void main(String[] args) {
//        printSymbolsAsync();
        printSymbolsSync();
    }

    public static void printSymbolsAsync() {
        var threadHorizontal = new Thread(new SymbolPrinter('-'));
        var threadVertical = new Thread(new SymbolPrinter('|'));

        threadHorizontal.start();
        threadVertical.start();
    }

    public static void printSymbolsSync() {
        var threadHorizontal = new Thread(new SymbolPrinterSync('-'));
        var threadVertical = new Thread(new SymbolPrinterSync('|'));

        threadHorizontal.start();
        threadVertical.start();
    }
}
package ProducerConsumerNumbers;

public class ConsumerSync {
    public final int totalNumberToProcess;
    private int numberProcessed = 0;

    private final int[] results;

    public ConsumerSync(int totalNumberToProcess, int numberToGeneratePerProducer) {
        this.totalNumberToProcess = totalNumberToProcess;
        results = new int[numberToGeneratePerProducer];
    }

    public synchronized boolean checkIfCanTake() {
        if (numberProcessed != totalNumberToProcess) {
            // we assume that the next number will be processed by a consumer
            // that invoked this method.
            numberProcessed++;
            return true;
        }
        return false;
    }

    public void setResult(int takenNumber) {
        synchronized (results) {
            results[takenNumber]++;
        }
    }

    public int[] getResults() {
        return results;
    }
}

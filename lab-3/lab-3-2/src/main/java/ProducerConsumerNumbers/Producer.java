package ProducerConsumerNumbers;

public class Producer implements Runnable{
    private IDrop drop;
    private int numberToGenerate;
    public Producer(IDrop drop, int numberToGenerate) {
        this.drop = drop;
        this.numberToGenerate = numberToGenerate;
    }
    @Override
    public void run() {
        for (int i = 0; i < numberToGenerate; i++) {
            drop.put(i);
        }
        System.out.println("\nProducer " + Thread.currentThread().getName() + " has finished the job");
    }
}

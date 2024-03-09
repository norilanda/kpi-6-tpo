package ProducerConsumerNumbers;

public class Consumer implements Runnable {
    private IDrop drop;
    private ConsumerSync consumerSync;
    public Consumer(IDrop drop, ConsumerSync consumerSync) {
        this.drop = drop;
        this.consumerSync = consumerSync;
    }
    @Override
    public void run() {
        while (consumerSync.checkIfCanTake()) {
            var number = drop.take();
            System.out.print(number + ", ");
            consumerSync.setResult(number);
        }
        System.out.println("\nConsumer " + Thread.currentThread().getName() + " has finished the job");
    }
}

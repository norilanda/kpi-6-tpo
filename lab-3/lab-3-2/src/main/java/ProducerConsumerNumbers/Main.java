package ProducerConsumerNumbers;


import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        int bufferSize = 10;
        int numberToGeneratePerProducer = 10;
        int producersNumber = 4;
        int consumerNumbers = 2;

        demo(bufferSize, numberToGeneratePerProducer, producersNumber, consumerNumbers);
    }

    public static int[] demo(
            int bufferSize, int numberToGeneratePerProducer,
            int producersNumber, int consumerNumbers) {
        int totalNumber = numberToGeneratePerProducer * producersNumber;

        IDrop drop = new Drop(bufferSize);
        ConsumerSync consumerSync = new ConsumerSync(totalNumber, numberToGeneratePerProducer);

        for (int i = 0; i < producersNumber; i++) {
            (new Thread(new Producer(drop, numberToGeneratePerProducer))).start();
        }

        var consumerThreads = new ArrayList<Thread>();
        for (int i = 0; i < consumerNumbers; i++) {
            var consumer = new Thread(new Consumer(drop, consumerSync));
            consumerThreads.add(consumer);
            consumer.start();
        }

        for (var consumer : consumerThreads) {
            try {
                consumer.join();
            } catch (InterruptedException e) {
                System.out.println(e);
                throw new RuntimeException(e);
            }
        }

        return consumerSync.getResults();
    }
}

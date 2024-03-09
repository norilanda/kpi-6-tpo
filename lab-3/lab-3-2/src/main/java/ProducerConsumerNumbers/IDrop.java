package ProducerConsumerNumbers;

public interface IDrop {
    public void put(int number);
    public int take();
}

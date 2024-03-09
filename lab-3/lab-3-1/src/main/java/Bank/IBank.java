package Bank;

public interface IBank {
    void transfer(int from, int to, int amount);
    int size();
}

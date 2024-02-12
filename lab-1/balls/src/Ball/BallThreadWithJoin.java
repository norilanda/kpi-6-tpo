package Ball;

public class BallThreadWithJoin extends BallThread{
    private Thread threadToWait;

    public BallThreadWithJoin(Ball ball, Thread threadToWait) {
        super(ball);
        this.threadToWait = threadToWait;
    }

    public BallThreadWithJoin(Ball ball) {
        super(ball);
    }

    public void run() {
        if(threadToWait != null) {
            try {
                threadToWait.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        super.run();
    }
}

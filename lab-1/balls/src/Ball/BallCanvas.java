package Ball;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BallCanvas extends JPanel {
    private BallsInPocketChangedEventHandler ballsInPocketChangedHandler;
    private final ArrayList<Pocket> pockets = new ArrayList<>();
    private ArrayList<Ball> balls = new ArrayList<>();

    private int ballsInPockets = 0;

    public List<Pocket> getPockets() {
        return Collections.unmodifiableList(pockets);
    }

    public void add(Ball b){
        balls.add(b);
    }
    public void add(Pocket pocket) {
        pockets.add(pocket);
    }

    private Lock lock = new ReentrantLock();
    public void onBallInPocket(Ball ball) {
        lock.lock();
        try {
            ballsInPockets++;
            if (ballsInPocketChangedHandler != null)
                ballsInPocketChangedHandler.onBallsInPocketChanged(ballsInPockets);

            balls.remove(ball);
            System.out.println("Removed ball in the thread: " + Thread.currentThread().getName());
        } finally {
            lock.unlock();
        }
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        for (Pocket pocket : pockets) {
            pocket.draw(g2);
        }
        for (Ball b : balls) {
            b.draw(g2);
        }
    }

    public void setBallsChangedHandler(BallsInPocketChangedEventHandler handler) {
        ballsInPocketChangedHandler = handler;
    }
}
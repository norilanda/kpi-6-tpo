package WeekTime;

import java.util.concurrent.atomic.AtomicInteger;

public class WeekTimeThread implements Runnable {
    private AtomicInteger weekNumber = new AtomicInteger(0);
    private final int totalWeekNumber;

    public WeekTimeThread(int totalWeekNumber) {
        this.totalWeekNumber = totalWeekNumber;
    }

    public int getCurrentWeek() {
        return weekNumber.get();
    }

    @Override
    public void run() {
        for (int i = 0; i < totalWeekNumber; i++) {
            try {
                Thread.sleep(1000 * (i + 1));
                weekNumber.incrementAndGet();
                synchronized (this) {
                    notifyAll();
                    System.out.println("Week " + i + " is over! Starting a new " + (i + 1)  + " week!\n");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

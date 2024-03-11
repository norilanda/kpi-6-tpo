package Teachers;

import WeekTime.WeekTimeThread;

public class TeacherBase {
    protected final WeekTimeThread weekTime;

    public TeacherBase(WeekTimeThread weekTime) {
        this.weekTime = weekTime;
    }

    protected void waitForACurrentWeek(int week) {
        while (week > weekTime.getCurrentWeek()) {
            synchronized (weekTime) {
                try {
                    weekTime.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

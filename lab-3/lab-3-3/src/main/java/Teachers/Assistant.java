package Teachers;

import GradeBook.GradeBookPage;
import WeekTime.WeekTimeThread;

public class Assistant extends TeacherBase implements Runnable {
    private static int assistanceNumberTotal = 0;
    private final GradeBookPage gradeBookPage;
    private final int markToSet;
    private final int indexToSet;
    private final int assistanceNumber;

    public Assistant(GradeBookPage page, int mark, int index, WeekTimeThread week) {
        super(week);
        gradeBookPage = page;
        markToSet = mark;
        indexToSet = index;
        assistanceNumber = assistanceNumberTotal;
        assistanceNumberTotal++;
    }
    @Override
    public void run() {
        for (int week = 0; week < gradeBookPage.getWeekNumber(); week++) {
            waitForACurrentWeek(week);

            for (int j = 0; j < gradeBookPage.getGroup().getTotalNumber(); j++) {
                if (j % indexToSet == 0) {
                    gradeBookPage.addMark(j, week, markToSet);
                }
            }
            System.out.println("Assistant " + assistanceNumber + " added marks for the week " + week);
        }
    }
}

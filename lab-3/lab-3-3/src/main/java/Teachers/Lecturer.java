package Teachers;

import GradeBook.GradeBook;
import WeekTime.WeekTimeThread;

public class Lecturer extends TeacherBase implements Runnable{
    private final GradeBook gradeBook;
    public Lecturer(GradeBook gradeBook, WeekTimeThread weekTime) {
        super(weekTime);
        this.gradeBook = gradeBook;
    }
    
    @Override
    public void run() {
        int mark = 50;

        for (int week = 0; week < gradeBook.getWeekNumber(); week++) {
            waitForACurrentWeek(week);

            for (int i = 0; i < gradeBook.length(); i++) {
                var groupPage = gradeBook.getPageForGroup(i);
                for (int j = 0; j < groupPage.getGroup().getTotalNumber(); j++) {
                    groupPage.addMark(j, week, mark);
                }
            }
            System.out.println("Lecturer added marks for the week " + week);
        }
    }

}

import GradeBook.GradeBook;
import GradeBook.GradeBookPage;
import GradeBook.Group;
import InputOuput.InputOutput;
import Teachers.Assistant;
import Teachers.Lecturer;
import WeekTime.WeekTimeThread;

public class Main {
    public static void main(String[] args) {
        int weekNumber = 4;

        var gradeBook = CreateGradeBook(weekNumber);
        var week = new WeekTimeThread(weekNumber);

        var lecturerThread = new Thread(new Lecturer(gradeBook, week));
        var assistantThread1 = new Thread(new Assistant(
                gradeBook.getPageForGroup(0), 20, 1, week));
        var assistantThread2 = new Thread(new Assistant(
                gradeBook.getPageForGroup(1), 25, 2, week));
        var assistantThread3 = new Thread(new Assistant(
                gradeBook.getPageForGroup(2), 30, 3, week));

        var weekThread = new Thread(week);

        lecturerThread.start();
        assistantThread1.start();
        assistantThread2.start();
        assistantThread3.start();

        weekThread.start();
        try {
            lecturerThread.join();
            assistantThread1.join();
            assistantThread2.join();
            assistantThread3.join();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        InputOutput.PrintGradeBook(gradeBook);
    }

    public static GradeBook CreateGradeBook(int weekNumber) {
        var studentsNames1 = new String[] {
                "Alice",
                "Oliver",
                "Eddie",
        };
        var studentsNames2 = new String[] {
                "Alice",
                "John",
                "Edward",
                "Harald",
                "Amy",
        };
        var studentsNames3 = new String[] {
                "Bob",
                "Nancy",
                "Sam Carter",
                "Ronnie",
                "Mike",
                "Alice",
                "John",
                "Edward",
        };

        var group1 = new Group(studentsNames1);
        var group2 = new Group(studentsNames2);
        var group3 = new Group(studentsNames3);


        var groupGradeBookPage1 = new GradeBookPage(group1, weekNumber);
        var groupGradeBookPage2 = new GradeBookPage(group2, weekNumber);
        var groupGradeBookPage3 = new GradeBookPage(group3, weekNumber);

        return new GradeBook(new GradeBookPage[] {
                groupGradeBookPage1,
                groupGradeBookPage2,
                groupGradeBookPage3
        });
    }
}

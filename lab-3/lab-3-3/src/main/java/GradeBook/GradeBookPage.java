package GradeBook;

import java.util.concurrent.atomic.AtomicInteger;

public class GradeBookPage {
    private final AtomicInteger[][] page;
    private final Group group;

    public GradeBookPage(Group group, int weekNumber) {
        this.group = group;
        page = new AtomicInteger[group.getTotalNumber()][weekNumber];
        for (int i = 0; i < page.length; i++) {
            page[i] = new AtomicInteger[weekNumber];
            for (int j = 0; j < page[i].length; j++) {
                page[i][j] = new AtomicInteger(0);
            }
        }
    }

    public int addMark(String studentName, int weekNumber, int mark) {
        var studentIndex = group.getStudentIndex(studentName);
        if (studentIndex == null) {
            throw new IllegalArgumentException("Student was not found");
        }

        return addMarkInternal(studentIndex, weekNumber, mark);
    }

    public int addMark(int studentIndex, int weekNumber, int mark) {
        return addMarkInternal(studentIndex, weekNumber, mark);
    }

    private int addMarkInternal(int studentIndex, int weekIndex, int mark) {
        var gradeBookCell = page[studentIndex][weekIndex];
        return gradeBookCell.addAndGet(mark);
    }

    public int getMark(int studentIndex, int weekIndex) {
        return page[studentIndex][weekIndex].get();
    }

    public Group getGroup() {
        return group;
    }

    public int getWeekNumber() {
        return page[0].length;
    }
}

package InputOuput;

import GradeBook.GradeBook;

public class InputOutput {
    public static void PrintGradeBook(GradeBook gradeBook) {
        for (int i = 0; i < gradeBook.length(); i++) {
            var groupPage = gradeBook.getPageForGroup(i);
            var group = groupPage.getGroup();

            var studentNames = group.getStudents();
            for (int studentIndex = 0; studentIndex < group.getTotalNumber(); studentIndex++) {
                System.out.print(studentIndex + ") " + studentNames[studentIndex] + ": ");

                for (int weekIndex = 0; weekIndex < gradeBook.getWeekNumber(); weekIndex++) {
                    System.out.print(groupPage.getMark(studentIndex, weekIndex) + " ");
                }
                System.out.println();
            }

            System.out.println();
        }
    }
}

package GradeBook;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Group {
    private final Map<String, Integer> students = new HashMap<>();
    private final String[] studentNames;
    private int studentNumber;

    public Group(String[] studentNames) {
        Arrays.sort(studentNames);
        this.studentNames = studentNames;

        for (int i = 0; i < studentNames.length; i++) {
            students.put(studentNames[i], i);
        }
        studentNumber = studentNames.length;
    }

    public Integer getStudentIndex(String name) {
        return students.get(name);
    }

    public String[] getStudents(){
        return studentNames;
    }


    public int getTotalNumber() {
        return studentNumber;
    }
}

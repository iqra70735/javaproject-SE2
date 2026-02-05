package learnify.models;

import java.io.Serializable;
import java.util.*;

public class Assignment implements Serializable {
    private static final long serialVersionUID = 1L;
    private String assignmentId;
    private String title;
    private String courseId;
    private String description;
    private String dueDate;
    private int totalMarks;
    private Map<String, String> submissions;

    public Assignment(String assignmentId, String title, String courseId, 
                      String description, String dueDate, int totalMarks) {
        this.assignmentId = assignmentId;
        this.title = title;
        this.courseId = courseId;
        this.description = description;
        this.dueDate = dueDate;
        this.totalMarks = totalMarks;
        this.submissions = new HashMap<>();
    }

    public void submitAssignment(String studentId, String submission) {
        submissions.put(studentId, submission);
    }

    public String getAssignmentId() { return assignmentId; }
    public String getTitle() { return title; }
    public String getCourseId() { return courseId; }
    public String getDescription() { return description; }
    public String getDueDate() { return dueDate; }
    public int getTotalMarks() { return totalMarks; }
    public Map<String, String> getSubmissions() { return submissions; }
    public String getSubmission(String studentId) { return submissions.get(studentId); }
}


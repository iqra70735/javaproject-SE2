package learnify.models;

import java.io.Serializable;
import java.util.*;

public class Course implements Serializable {
    private static final long serialVersionUID = 1L;
    private String courseId;
    private String courseName;
    private String description;
    private String teacherId;
    private List<String> enrolledStudents;
    private String startDate;
    private String endDate;
    private double credits;
    private String courseContent;

    public Course(String courseId, String courseName, String description, 
                  String teacherId, String startDate, String endDate, double credits) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.description = description;
        this.teacherId = teacherId;
        this.enrolledStudents = new ArrayList<>();
        this.startDate = startDate;
        this.endDate = endDate;
        this.credits = credits;
        this.courseContent = "";
    }

    public void enrollStudent(String studentId) {
        if (!enrolledStudents.contains(studentId)) {
            enrolledStudents.add(studentId);
        }
    }

    public void removeStudent(String studentId) { 
        enrolledStudents.remove(studentId); 
    }

    public String getCourseId() { return courseId; }
    public String getCourseName() { return courseName; }
    public String getDescription() { return description; }
    public String getTeacherId() { return teacherId; }
    public List<String> getEnrolledStudents() { return enrolledStudents; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public double getCredits() { return credits; }
    public String getCourseContent() { return courseContent; }

    public void setCourseName(String courseName) { this.courseName = courseName; }
    public void setDescription(String description) { this.description = description; }
    public void setCourseContent(String content) { this.courseContent = content; }

    public int getEnrollmentCount() { return enrolledStudents.size(); }
}

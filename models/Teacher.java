package learnify.models;

import java.io.Serializable;
import java.util.*;

public class Teacher extends User implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<String> assignedCourses;
    private List<String> publishedQuizzes;
    private List<String> publishedAssignments;
    private String department;
    private double rating;

    public Teacher(String userId, String username, String password, 
                   String email, String joinDate, String department) {
        super(userId, username, password, email, "TEACHER", joinDate);
        this.assignedCourses = new ArrayList<>();
        this.publishedQuizzes = new ArrayList<>();
        this.publishedAssignments = new ArrayList<>();
        this.department = department;
        this.rating = 0.0;
    }

    public void assignCourse(String courseId) {
        if (!assignedCourses.contains(courseId)) {
            assignedCourses.add(courseId);
        }
    }

    public void publishQuiz(String quizId) { publishedQuizzes.add(quizId); }
    public void publishAssignment(String assignmentId) { publishedAssignments.add(assignmentId); }

    public List<String> getAssignedCourses() { return assignedCourses; }
    public List<String> getPublishedQuizzes() { return publishedQuizzes; }
    public List<String> getPublishedAssignments() { return publishedAssignments; }
    public String getDepartment() { return department; }
    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    @Override
    public void displayInfo() {
        System.out.println("Teacher: " + username + " | Department: " + department + 
                          " | Courses: " + assignedCourses.size());
    }
}
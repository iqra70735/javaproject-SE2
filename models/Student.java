package learnify.models;

import java.io.Serializable;
import java.util.*;

public class Student extends User implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<String> enrolledCourses;
    private Map<String, Double> grades;
    private List<String> certificates;
    private double gpa;

    public Student(String userId, String username, String password, 
                   String email, String joinDate) {
        super(userId, username, password, email, "STUDENT", joinDate);
        this.enrolledCourses = new ArrayList<>();
        this.grades = new HashMap<>();
        this.certificates = new ArrayList<>();
        this.gpa = 0.0;
    }

    public void enrollCourse(String courseId) {
        if (!enrolledCourses.contains(courseId)) {
            enrolledCourses.add(courseId);
        }
    }

    public void dropCourse(String courseId) {
        enrolledCourses.remove(courseId);
    }

    public List<String> getEnrolledCourses() { return enrolledCourses; }
    public Map<String, Double> getGrades() { return grades; }
    public void setGrade(String courseId, double grade) { grades.put(courseId, grade); }
    public List<String> getCertificates() { return certificates; }
    public void addCertificate(String certificateId) { certificates.add(certificateId); }
    public double getGPA() { return gpa; }
    public void setGPA(double gpa) { this.gpa = gpa; }

    @Override
    public void displayInfo() {
        System.out.println("Student: " + username + " | Email: " + email + 
                          " | Courses: " + enrolledCourses.size());
    }
}
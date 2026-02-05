package learnify.interfaces;

public interface Gradable {
    void assignGrade(String studentId, String courseId, double grade);
    double calculateGPA(String studentId);
    void generateReport(String studentId);
}
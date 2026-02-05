package learnify.interfaces;

public interface Enrollable {
    void enrollCourse(String courseId);
    void dropCourse(String courseId);
    void viewEnrolledCourses();
}
package learnify.database;

import java.util.*;
import learnify.models.*;
import learnify.utilities.*;

public class DataManager {
    private static DataManager instance;
    private final List<User> users;
    private final List<Course> courses;
    private final List<Quiz> quizzes;
    private final List<Assignment> assignments;
    private final List<Certificate> certificates;

    private DataManager() {
        FileHandler.initializeDataDirectory();
        this.users = FileHandler.loadUsers();
        this.courses = FileHandler.loadCourses();
        this.quizzes = FileHandler.loadQuizzes();
        this.assignments = FileHandler.loadAssignments();
        this.certificates = FileHandler.loadCertificates();
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    /* =====================
          USER CRUD
       ===================== */

    public void addUser(User user) {
        users.add(user);
        saveAllData();
    }

    public User getUserByUsername(String username) {
    for (User user : users) {
        if (user.getUsername().equals(username)) {
            return user;
        }
    }
    return null;
}


    public User getUserById(String userId) {
        return users.stream()
            .filter(u -> u.getUserId().equals(userId))
            .findFirst()
            .orElse(null);
    }

    public List<User> getAllUsers() { 
        return new ArrayList<>(users); 
    }

    public void updateUser(User user) { 
        saveAllData(); 
    }

    public void deleteUser(String userId) {
        users.removeIf(u -> u.getUserId().equals(userId));
        saveAllData();
    }

    /* =====================
          COURSE CRUD
       ===================== */

    public void addCourse(Course course) {
        courses.add(course);
        saveAllData();
    }

    public Course getCourseById(String courseId) {
        return courses.stream()
            .filter(c -> c.getCourseId().equals(courseId))
            .findFirst()
            .orElse(null);
    }

    public List<Course> getAllCourses() { 
        return new ArrayList<>(courses); 
    }

    public void updateCourse(Course course) { 
        saveAllData(); 
    }

    public void deleteCourse(String courseId) {
        courses.removeIf(c -> c.getCourseId().equals(courseId));
        saveAllData();
    }

    /* =====================
          QUIZ CRUD
       ===================== */

    public void addQuiz(Quiz quiz) {
        quizzes.add(quiz);
        saveAllData();
    }

    public Quiz getQuizById(String quizId) {
        return quizzes.stream()
            .filter(q -> q.getQuizId().equals(quizId))
            .findFirst()
            .orElse(null);
    }

    public List<Quiz> getQuizzesByCourse(String courseId) {
        return quizzes.stream()
            .filter(q -> q.getCourseId().equals(courseId))
            .toList();
    }

    // ✅ FIX ADDED — NOW VIEW QUIZZES BUTTON WORKS
    public List<Quiz> getAllQuizzes() {
        return new ArrayList<>(quizzes);
    }

    public void updateQuiz(Quiz quiz) { 
        saveAllData(); 
    }

    public void deleteQuiz(String quizId) {
        quizzes.removeIf(q -> q.getQuizId().equals(quizId));
        saveAllData();
    }

    /* =====================
        ASSIGNMENT CRUD
       ===================== */

    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
        saveAllData();
    }

    public Assignment getAssignmentById(String assignmentId) {
        return assignments.stream()
            .filter(a -> a.getAssignmentId().equals(assignmentId))
            .findFirst()
            .orElse(null);
    }

    public List<Assignment> getAssignmentsByCourse(String courseId) {
        return assignments.stream()
            .filter(a -> a.getCourseId().equals(courseId))
            .toList();
    }

    public void updateAssignment(Assignment assignment) { 
        saveAllData(); 
    }

    public void deleteAssignment(String assignmentId) {
        assignments.removeIf(a -> a.getAssignmentId().equals(assignmentId));
        saveAllData();
    }

    /* =====================
        CERTIFICATE CRUD
       ===================== */

    public void addCertificate(Certificate certificate) {
        certificates.add(certificate);
        saveAllData();
    }

    public List<Certificate> getCertificatesByStudent(String studentId) {
        return certificates.stream()
            .filter(c -> c.getStudentId().equals(studentId))
            .toList();
    }

    /* =====================
           SAVE ALL
       ===================== */

    public void saveAllData() {
        FileHandler.saveUsers(users);
        FileHandler.saveCourses(courses);
        FileHandler.saveQuizzes(quizzes);
        FileHandler.saveAssignments(assignments);
        FileHandler.saveCertificates(certificates);
    }
}

package learnify.utilities;

import java.io.*;
import java.util.*;
import learnify.models.*;

public class FileHandler {
    
    public static void saveUsers(List<User> users) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(Constants.USERS_FILE))) {
            oos.writeObject(users);
        } catch (IOException e) {
        }
    }

    @SuppressWarnings("unchecked")
    public static List<User> loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(Constants.USERS_FILE))) {
            return (List<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public static void saveCourses(List<Course> courses) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(Constants.COURSES_FILE))) {
            oos.writeObject(courses);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Course> loadCourses() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(Constants.COURSES_FILE))) {
            return (List<Course>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public static void saveQuizzes(List<Quiz> quizzes) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(Constants.QUIZZES_FILE))) {
            oos.writeObject(quizzes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Quiz> loadQuizzes() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(Constants.QUIZZES_FILE))) {
            return (List<Quiz>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public static void saveAssignments(List<Assignment> assignments) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(Constants.ASSIGNMENTS_FILE))) {
            oos.writeObject(assignments);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Assignment> loadAssignments() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(Constants.ASSIGNMENTS_FILE))) {
            return (List<Assignment>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public static void saveCertificates(List<Certificate> certificates) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(Constants.CERTIFICATES_FILE))) {
            oos.writeObject(certificates);
        } catch (IOException e) {
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Certificate> loadCertificates() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(Constants.CERTIFICATES_FILE))) {
            return (List<Certificate>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public static boolean fileExists(String filePath) {
        return new File(filePath).exists();
    }

    public static void initializeDataDirectory() {
        File dir = new File(Constants.DATA_DIRECTORY);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }
}
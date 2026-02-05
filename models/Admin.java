package learnify.models;

import java.io.Serializable;
import java.util.*;

public class Admin extends User implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<String> managedUsers;
    private List<String> managedCourses;
    private String adminLevel;
    private Date lastLogin;

    public Admin(String userId, String username, String password, 
                 String email, String joinDate, String adminLevel) {
        super(userId, username, password, email, "ADMIN", joinDate);
        this.managedUsers = new ArrayList<>();
        this.managedCourses = new ArrayList<>();
        this.adminLevel = adminLevel;
        this.lastLogin = new Date();
    }

    public void addUser(String userId) { managedUsers.add(userId); }
    public void removeUser(String userId) { managedUsers.remove(userId); }
    public void addCourse(String courseId) { managedCourses.add(courseId); }
    public void removeCourse(String courseId) { managedCourses.remove(courseId); }

    public List<String> getManagedUsers() { return managedUsers; }
    public List<String> getManagedCourses() { return managedCourses; }
    public String getAdminLevel() { return adminLevel; }
    public Date getLastLogin() { return lastLogin; }
    public void updateLastLogin() { this.lastLogin = new Date(); }

    @Override
    public void displayInfo() {
        System.out.println("Admin: " + username + " | Level: " + adminLevel + 
                          " | Users: " + managedUsers.size());
    }
}

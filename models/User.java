package learnify.models;

import java.io.Serializable;

public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String userId;
    protected String username;
    protected String password;
    protected String email;
    protected String role;
    protected String joinDate;

    public User(String userId, String username, String password, 
                String email, String role, String joinDate) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.joinDate = joinDate;
    }

    public String getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public String getJoinDate() { return joinDate; }

    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }

    public abstract void displayInfo();
}

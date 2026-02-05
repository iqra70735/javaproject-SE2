package learnify.interfaces;

public interface Authenticatable {
    boolean authenticate(String username, String password);
    boolean registerUser(String username, String password, String email);
    void logout();
}
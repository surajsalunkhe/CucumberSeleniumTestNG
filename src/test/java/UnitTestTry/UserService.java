package UnitTestTry;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * UserService class for managing user operations.
 * This is a sample class to demonstrate the Unit Test Generator plugin.
 */
public class UserService {

    private List<User> users;
    private EmailService emailService;

    public UserService() {
        this.users = new ArrayList<>();
        this.emailService = new EmailService();
    }

    public UserService(EmailService emailService) {
        this.users = new ArrayList<>();
        this.emailService = emailService;
    }

    /**
     * Find a user by their ID.
     * 
     * @param id The user ID
     * @return Optional containing the user if found
     */
    public Optional<User> findUserById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    /**
     * Create a new user and send welcome email.
     * 
     * @param user The user to create
     * @return The created user with generated ID
     */
    public User createUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("User email is required");
        }

        // Check if email already exists
        boolean emailExists = users.stream()
                .anyMatch(u -> u.getEmail().equals(user.getEmail()));
        
        if (emailExists) {
            throw new IllegalStateException("Email already exists");
        }

        // Generate ID
        Long newId = users.isEmpty() ? 1L : users.get(users.size() - 1).getId() + 1;
        user.setId(newId);
        
        // Save user
        users.add(user);
        
        // Send welcome email
        emailService.sendWelcomeEmail(user.getEmail(), user.getName());
        
        return user;
    }

    /**
     * Update an existing user.
     * 
     * @param id The user ID
     * @param updatedUser The updated user data
     * @return The updated user
     */
    public User updateUser(Long id, User updatedUser) {
        if (id == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        
        Optional<User> existingUser = findUserById(id);
        
        if (!existingUser.isPresent()) {
            throw new IllegalStateException("User not found");
        }
        
        User user = existingUser.get();
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setAge(updatedUser.getAge());
        
        return user;
    }

    /**
     * Delete a user by ID.
     * 
     * @param id The user ID to delete
     * @return true if deleted, false otherwise
     */
    public boolean deleteUser(Long id) {
        if (id == null) {
            return false;
        }
        
        return users.removeIf(user -> user.getId().equals(id));
    }

    /**
     * Get all users.
     * 
     * @return List of all users
     */
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    /**
     * Find users by age range.
     * 
     * @param minAge Minimum age
     * @param maxAge Maximum age
     * @return List of users within age range
     */
    public List<User> findUsersByAgeRange(int minAge, int maxAge) {
        if (minAge < 0 || maxAge < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        
        if (minAge > maxAge) {
            throw new IllegalArgumentException("Min age cannot be greater than max age");
        }
        
        List<User> result = new ArrayList<>();
        for (User user : users) {
            if (user.getAge() >= minAge && user.getAge() <= maxAge) {
                result.add(user);
            }
        }
        
        return result;
    }

    /**
     * Count total number of users.
     * 
     * @return Total user count
     */
    public int getUserCount() {
        return users.size();
    }

    /**
     * Check if a user exists by email.
     * 
     * @param email The email to check
     * @return true if exists, false otherwise
     */
    public boolean userExistsByEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        
        return users.stream()
                .anyMatch(user -> user.getEmail().equalsIgnoreCase(email));
    }

    /**
     * Activate a user account.
     * 
     * @param id The user ID
     */
    public void activateUser(Long id) {
        Optional<User> user = findUserById(id);
        if (user.isPresent()) {
            user.get().setActive(true);
            emailService.sendActivationEmail(user.get().getEmail());
        }
    }

    /**
     * Deactivate a user account.
     * 
     * @param id The user ID
     */
    public void deactivateUser(Long id) {
        Optional<User> user = findUserById(id);
        if (user.isPresent()) {
            user.get().setActive(false);
        }
    }
}

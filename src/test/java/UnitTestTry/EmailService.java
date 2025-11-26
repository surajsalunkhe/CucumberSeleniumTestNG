package UnitTestTry;

/**
 * EmailService class for sending emails.
 * This is a dependency of UserService.
 */
public class EmailService {

    /**
     * Send a welcome email to a new user.
     * 
     * @param email The recipient email
     * @param name The user's name
     */
    public void sendWelcomeEmail(String email, String name) {
        System.out.println("Sending welcome email to " + email);
        // Implementation would send actual email
    }

    /**
     * Send an activation email.
     * 
     * @param email The recipient email
     */
    public void sendActivationEmail(String email) {
        System.out.println("Sending activation email to " + email);
        // Implementation would send actual email
    }
}

package UnitTestTry;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import static org.testng.Assert.*;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class UserTest {

    @InjectMocks
    private User user;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIsActive() {
        // Arrange

        // Act
        boolean result = user.isActive();

        // Assert
        assertNotNull(result, "Result should not be null");
        // TODO: Add more specific assertions
    }

    @Test
    public void testToString() {
        // Arrange

        // Act
        String result = user.toString();

        // Assert
        assertNotNull(result, "Result should not be null");
        // TODO: Add more specific assertions
    }

}

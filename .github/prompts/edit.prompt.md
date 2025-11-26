---
agent: edit
---

# Cucumber-Selenium-TestNG Framework - Code Generation & Editing Guidelines

## Mandatory Code Standards

### 1. File Header Convention
**ALWAYS include this comment at the top of every new file:**
```java
```

### 2. Package Structure
Follow existing package organization:
- `pages.{Module}` - Page Objects (e.g., `pages.Login`, `pages.Dashboard`)
- `stepDef` - Step Definitions
- `utils` - Utility classes
- `driverFactory` - WebDriver management
- `cucumber` - Test context and DI
- `objectManager` - Page Object factory
- `appHooks` - Cucumber lifecycle hooks
- `testRunner` - Test execution configuration
- `listeners` - TestNG listeners

---

## Page Object Model Pattern (MANDATORY)

### Template for New Page Classes

```java
package pages.{ModuleName};

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import utils.ElementUtil;
import utils.ObjectRepository;

public class {PageName}Page {
    private WebDriver driver;
    private ElementUtil elementutil;
    private static final Logger logger = LogManager.getLogger({PageName}Page.class);
    
    // Locators - NEVER hardcode, always use ObjectRepository
    private By elementLocator = ObjectRepository.getLocator("ELEMENT_KEY");
    private By buttonLocator = ObjectRepository.getLocator("BUTTON_KEY");
    
    // Constructor - MUST initialize ElementUtil and PageFactory
    public {PageName}Page(WebDriver driver) {
        this.driver = driver;
        this.elementutil = new ElementUtil(driver);
        PageFactory.initElements(driver, this);
    }
    
    // Action methods - Use ElementUtil wrapper methods
    public void clickButton() {
        logger.info("Clicking on button");
        elementutil.doClick(buttonLocator);
    }
    
    public void enterText(String text) {
        logger.info("Entering text: {}", text);
        elementutil.doSendKeys(elementLocator, text);
    }
    
    public String getElementText() {
        logger.info("Getting element text");
        return elementutil.getTextOfElement(elementLocator);
    }
    
    public boolean isElementDisplayed() {
        return elementutil.isElementDisplayed(elementLocator);
    }
}
```

### Key Rules for Page Objects:
1. ✅ **Use `private` for driver and elementutil fields**
2. ✅ **Use `ObjectRepository.getLocator("KEY")` for ALL locators**
3. ✅ **Never use `driver.findElement()` - always use `elementutil` methods**
4. ✅ **Initialize `PageFactory.initElements(driver, this)` in constructor**
5. ✅ **Add logger for debugging: `Logger logger = LogManager.getLogger(ClassName.class)`**
6. ✅ **Log important actions at INFO level**
7. ❌ **Never hardcode locators like `By.xpath("//button")`**

---

## Step Definition Pattern (MANDATORY)

### Template for New Step Definition Classes
```java
package stepDef;

import cucumber.TestContext;
import driverFactory.DriverManager;
import io.cucumber.java.en.*;
import pages.{ModuleName}.{PageName}Page;
import utils.PropertiesFileManager;
import org.testng.Assert;

public class {Feature}StepDef {
    
    private TestContext testContext;
    private {PageName}Page pageName;
    
    // Constructor - Picocontainer auto-injects TestContext
    public {Feature}StepDef(TestContext context) {
        this.testContext = context;
    }
    
    @Given("User launch the browser")
    public void user_launch_browser() {
        String browserName = DriverManager.returnBrowserName();
        testContext.initializeDriver(browserName);
        pageName = testContext.getPageObjectManager().get{PageName}Page();
    }
    
    @Given("User navigates to {string}")
    public void user_navigates_to(String urlKey) {
        String url = PropertiesFileManager.getPropertyValue(urlKey);
        pageName = testContext.getPageObjectManager().get{PageName}Page();
        pageName.navigateToSite(url);
    }
    
    @When("User clicks on {string}")
    public void user_clicks_on(String element) {
        pageName = testContext.getPageObjectManager().get{PageName}Page();
        pageName.clickButton();
    }
    
    @When("User enters {string} in field")
    public void user_enters_in_field(String text) {
        pageName = testContext.getPageObjectManager().get{PageName}Page();
        pageName.enterText(text);
    }
    
    @Then("User verifies {string} is displayed")
    public void user_verifies_displayed(String expectedText) {
        pageName = testContext.getPageObjectManager().get{PageName}Page();
        String actualText = pageName.getElementText();
        Assert.assertEquals(actualText, expectedText, "Text verification failed");
    }
    
    @Then("User closes the browser")
    public void user_closes_browser() {
        testContext.closeDriver();
    }
}
```

### Key Rules for Step Definitions:
1. ✅ **Constructor must inject `TestContext` - NO manual instantiation**
2. ✅ **Call `testContext.initializeDriver(browser)` in first `@Given` step**
3. ✅ **Get page objects via `testContext.getPageObjectManager().getXxxPage()`**
4. ✅ **Never create page objects with `new PageClass(driver)`**
5. ✅ **Use `PropertiesFileManager.getPropertyValue()` for all config values**
6. ✅ **Use TestNG `Assert` for validations**
7. ❌ **Never initialize driver in `@Before` hook**
8. ❌ **Never store driver as instance variable in step definition**

---

## PageObjectManager Registration (CRITICAL)

**MUST register every new page in `PageObjectManager.java`:**

```java
package objectManager;

import org.openqa.selenium.WebDriver;
import pages.Login.LoginPage;
import pages.Login.RegistrationPage;
import pages.{ModuleName}.{PageName}Page;  // Add import

public class PageObjectManager {
    private WebDriver driver;
    private LoginPage loginPage;
    private RegistrationPage registrationPage;
    private {PageName}Page pageNamePage;  // Add field
    
    public PageObjectManager(WebDriver driver) {
        this.driver = driver;
    }
    
    public LoginPage getLoginPage() {
        return (loginPage == null) ? loginPage = new LoginPage(driver) : loginPage;
    }
    
    public RegistrationPage getRegistrationPage() {
        return (registrationPage == null) ? registrationPage = new RegistrationPage(driver) : registrationPage;
    }
    
    // Add getter with lazy initialization
    public {PageName}Page get{PageName}Page() {
        return (pageNamePage == null) ? pageNamePage = new {PageName}Page(driver) : pageNamePage;
    }
}
```

### Pattern Explanation:
- **Lazy initialization**: Page created only on first access
- **Singleton per context**: Same instance reused within test scenario
- **Null check**: Prevents duplicate page instantiation
- **Naming**: Method name is `get{PageName}Page()`, field is `pageNamePage` (camelCase)

---

## Locator Management (NEVER HARDCODE)

### Adding Locators to Properties Files

**File: `src/test/resources/environment/QA/Locator.properties`**
```properties
# Login Page Locators
LOGIN_USERNAME=id:username
LOGIN_PASSWORD=id:password
LOGIN_BUTTON=xpath://button[@type='submit']
LOGOUT_BUTTON=css:.logout-btn
LOGIN_SUCCESS_MESSAGE=css:.alert-success
LOGIN_FAILED_MESSAGE=xpath://div[@class='error-message']

# Dashboard Page Locators
DASHBOARD_TITLE=css:h1.dashboard-title
USER_PROFILE_LINK=linktext:Profile
SETTINGS_ICON=class:settings-icon

# Common Elements
SUBMIT_BUTTON=xpath://button[text()='Submit']
CANCEL_BUTTON=partiallinktext:Cancel
ERROR_POPUP=css:#error-modal
```

**File: `src/test/resources/environment/UAT/Locator.properties`**
```properties
# Same keys, potentially different values for UAT environment
LOGIN_USERNAME=id:user-input-uat
LOGIN_PASSWORD=id:pass-input-uat
LOGIN_BUTTON=xpath://button[@id='submit-uat']
```

### Supported Locator Types:
- `id:elementId` → `By.id("elementId")`
- `name:elementName` → `By.name("elementName")`
- `xpath://path` → `By.xpath("//path")`
- `css:.selector` → `By.cssSelector(".selector")`
- `class:className` → `By.className("className")`
- `linktext:Link Text` → `By.linkText("Link Text")`
- `partiallinktext:Partial` → `By.partialLinkText("Partial")`

### Usage in Page Objects:
```java
// ✅ CORRECT
private By loginButton = ObjectRepository.getLocator("LOGIN_BUTTON");

// ❌ WRONG
private By loginButton = By.xpath("//button[@type='submit']");
```

---

## Feature File Structure

### Template for New Feature Files

**File: `src/test/resources/features/{ModuleName}/{FeatureName}.feature`**

```gherkin
@QA @UAT @Smoke
Feature: {Feature Name}
  As a {user role}
  I want to {action}
  So that {business value}

  Background:
    Given User launch the browser
    And User navigates to "ApplicationURL"

  @Positive @TC001
  Scenario: {Clear positive scenario description}
    When User enters "validUser" in username field
    And User enters "validPass" in password field
    And User clicks on "LOGIN_BUTTON"
    Then User verifies "Welcome, User" is displayed
    And User closes the browser

  @Negative @TC002
  Scenario: {Clear negative scenario description}
    When User enters "invalidUser" in username field
    And User enters "wrongPass" in password field
    And User clicks on "LOGIN_BUTTON"
    Then User verifies "Invalid credentials" is displayed
    And User closes the browser

  @DataDriven @TC003
  Scenario Outline: {Parameterized scenario}
    When User enters "<username>" in username field
    And User enters "<password>" in password field
    And User clicks on "LOGIN_BUTTON"
    Then User verifies "<message>" is displayed
    And User closes the browser
    
    Examples:
      | username  | password | message              |
      | user1     | pass1    | Welcome, user1       |
      | user2     | pass2    | Welcome, user2       |
      | invalid   | wrong    | Invalid credentials  |
```

### Tagging Strategy:
- **Environment**: `@QA`, `@UAT` - Controls which environment runs the test
- **Test Type**: `@Smoke`, `@Regression`, `@Sanity` - Test suite categorization
- **Test Nature**: `@Positive`, `@Negative`, `@DataDriven` - Test approach
- **Test ID**: `@TC001`, `@TC002` - Unique identifier
- **Jira Integration**: `@JIRA-123` - Link to Jira story/bug

---

## Configuration Files

### Environment Properties
**File: `src/test/resources/environment/QA/environment.properties`**
```properties
# Application URLs
MyURL=https://qa.application.com
TestLoginURL=https://qa.application.com/login
ApiBaseURL=https://api.qa.application.com

# Feature Flags
JiraUpdate=No
SEND_EMAIL_AFTER_EXECUTION=No
ATTACH_SCREENSHOT_TO_REPORT=No

# Timeouts (seconds)
IMPLICIT_WAIT=10
EXPLICIT_WAIT=15
PAGE_LOAD_TIMEOUT=30
```

### Application User Properties
**File: `src/test/resources/environment/QA/ApplicationUser.properties`**
```properties
# Test Accounts
Username=testuser@domain.com
Password=Test@123
AdminUsername=admin@domain.com
AdminPassword=Admin@123

# API Credentials
ApiUsername=api_user
ApiPassword=Api@Pass123
ApiKey=abc123xyz789
```

### Assertion Properties
**File: `src/test/resources/environment/QA/Assertion.properties`**
```properties
# Expected Messages
LOGIN_SUCCESS_MESSAGE=Login successful
LOGOUT_SUCCESS_MESSAGE=Logged out successfully
DASHBOARD_TITLE=Dashboard - My Application
ERROR_INVALID_CREDENTIALS=Invalid username or password
ERROR_ACCOUNT_LOCKED=Account has been locked

# Expected Values
DEFAULT_LANGUAGE=English
DEFAULT_CURRENCY=USD
```

---

## ElementUtil Usage (MANDATORY)

### Common Methods (Use Instead of Raw Selenium)

```java
// Click Operations
elementutil.doClick(locator);                          // Standard click with retry
elementutil.doActionsClick(locator);                   // Actions class click
elementutil.doClickByJS(locator);                      // JavaScript click

// Send Keys Operations
elementutil.doSendKeys(locator, "text");              // Standard input with retry
elementutil.doSendKeysByJS(locator, "text");          // JavaScript input

// Get Operations
elementutil.getTextOfElement(locator);                 // Get visible text
elementutil.doGetAttribute(locator, "value");          // Get attribute value
elementutil.isElementDisplayed(locator);               // Check visibility

// Wait Operations
elementutil.waitForElementToBePresent(locator);        // Wait for element in DOM
elementutil.waitForElementToBeVisible(locator, 15);    // Wait for visibility
elementutil.waitForElementToBeClickable(locator, 10);  // Wait for clickable state
elementutil.waitForElementToDisappear(locator, 10);    // Wait for disappearance

// List Operations
elementutil.getAllElements(locator);                   // Get List<WebElement>
elementutil.getElementsCount(locator);                 // Get count of elements
```

### Why Use ElementUtil:
- ✅ **Automatic retry** on `StaleElementReferenceException`
- ✅ **Fallback to JS** when standard methods fail
- ✅ **Built-in explicit waits** (no manual `WebDriverWait` needed)
- ✅ **Consistent error handling** across framework
- ✅ **Logging** for debugging

---

## Property Resolution Pattern

### Using PropertiesFileManager

```java
import utils.PropertiesFileManager;

// Reads from environment/{QA|UAT}/*.properties
String url = PropertiesFileManager.getPropertyValue("MyURL");
String username = PropertiesFileManager.getPropertyValue("Username");
String expectedMsg = PropertiesFileManager.getPropertyValue("LOGIN_SUCCESS_MESSAGE");

// Always check for null
if (url == null) {
    throw new RuntimeException("MyURL property not found in environment files");
}
```

### Property Resolution Order:
1. `environment.properties` (checked first)
2. `ApplicationUser.properties`
3. `Locator.properties`
4. `Assertion.properties`
5. Any other `*.properties` in `environment/{QA|UAT}/` folder

**Key Insight**: Properties can be in ANY file within the environment folder. The framework searches automatically.

---

## Test Data Patterns

### 1. Excel-Based Test Data

```java
import utils.ExcelReader;
import static utils.Constants.TEST_DATA_FILE_PATH;

// Step Definition
@Given("User has test data from Excel")
public void user_has_test_data() throws IOException {
    String env = DriverManager.returnEnvironment();
    String excelPath = TEST_DATA_FILE_PATH + env + "/TestData/LoginData.xlsx";
    List<Object[]> testData = ExcelReader.readExcelDataForLogin(excelPath);
}

@When("User enters credentials from Excel")
public void user_enters_credentials() {
    for (Object[] row : testData) {
        String username = (String) row[0];
        String password = (String) row[1];
        String expectedStatus = (String) row[2];
        String expectedMessage = (String) row[3];
        
        loginPage.enterUsernameAndPassword(username, password);
        loginPage.verifyTheMessageFromData(expectedStatus, expectedMessage);
    }
}
```

**Excel File Location:**
```
src/test/resources/environment/
├── QA/TestData/
│   ├── LoginData.xlsx
│   └── RegistrationData.xlsx
└── UAT/TestData/
    ├── LoginData.xlsx
    └── RegistrationData.xlsx
```

### 2. Scenario Outline (Inline Data)

```gherkin
Scenario Outline: Login with multiple users
  When User enters "<username>" and "<password>"
  Then User verifies "<status>" and message "<message>"
  
  Examples:
    | username | password | status | message           |
    | user1    | pass1    | Y      | Login successful  |
    | user2    | pass2    | Y      | Login successful  |
    | invalid  | wrong    | N      | Invalid credentials |
```

### 3. Properties-Based (Configuration)

```java
String url = PropertiesFileManager.getPropertyValue("TestLoginURL");
String username = PropertiesFileManager.getPropertyValue("Username");
String password = PropertiesFileManager.getPropertyValue("Password");
```

---

## Logging Standards

### Log4j2 Integration

```java
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyPage {
    private static final Logger logger = LogManager.getLogger(MyPage.class);
    
    public void performAction() {
        logger.info("Starting action: performAction");
        
        try {
            elementutil.doClick(actionButton);
            logger.debug("Button clicked successfully");
        } catch (Exception e) {
            logger.error("Failed to click button: {}", e.getMessage(), e);
            throw new RuntimeException("Action failed", e);
        }
        
        logger.info("Completed action: performAction");
    }
}
```

### Log Levels:
- `logger.info()` - Important actions (clicks, navigation, verifications)
- `logger.debug()` - Detailed flow information
- `logger.warn()` - Warnings but execution continues
- `logger.error()` - Errors with stack traces

---

## Assertion Patterns

### Using TestNG Assertions

```java
import org.testng.Assert;

// Equality assertions
Assert.assertEquals(actualValue, expectedValue, "Error message if failed");

// Boolean assertions
Assert.assertTrue(condition, "Error message if failed");
Assert.assertFalse(condition, "Error message if failed");

// Null checks
Assert.assertNotNull(object, "Object should not be null");

// Contains checks
Assert.assertTrue(actualText.contains(expectedText), "Text not found");
```

### Example in Step Definition:

```java
@Then("User verifies success message")
public void verify_success_message() {
    String actualMessage = loginPage.getLoginSuccessMessage();
    String expectedMessage = PropertiesFileManager.getPropertyValue("LOGIN_SUCCESS_MESSAGE");
    Assert.assertEquals(actualMessage, expectedMessage, "Success message mismatch");
}

@Then("User should be on dashboard")
public void verify_dashboard() {
    boolean isDashboardDisplayed = dashboardPage.isDashboardTitleDisplayed();
    Assert.assertTrue(isDashboardDisplayed, "Dashboard not displayed");
}
```

---

## Code Quality Checklist

Before submitting code, verify:

### Structure & Patterns:
- [ ] File header comment `// --Generated by Copilot` present
- [ ] Correct package structure followed
- [ ] Page objects follow mandatory template
- [ ] Step definitions use constructor injection
- [ ] New pages registered in `PageObjectManager`

### Configuration & Data:
- [ ] No hardcoded values (URLs, credentials, locators)
- [ ] All locators in `Locator.properties`
- [ ] All config in `environment.properties` or related files
- [ ] Test data in environment-specific folders

### Selenium & Framework:
- [ ] `ElementUtil` used for all Selenium operations
- [ ] `ObjectRepository.getLocator()` used for all locators
- [ ] `PropertiesFileManager.getPropertyValue()` for config
- [ ] Driver initialized in first `@Given` step
- [ ] Page objects retrieved via `PageObjectManager`

### Code Style:
- [ ] 4-space indentation (no tabs)
- [ ] Line length ≤ 120 characters
- [ ] Meaningful variable/method names
- [ ] Logger added and used appropriately
- [ ] Proper exception handling
- [ ] Comments for complex logic

---

## Formatting Standards

### Class Structure Order:
```java
// 1. Package declaration
package pages.Login;

// 2. Imports (grouped)
import org.openqa.selenium.*;      // Selenium
import org.testng.Assert;          // TestNG
import io.cucumber.java.en.*;      // Cucumber
import utils.*;                     // Framework utilities

// 3. Class declaration with JavaDoc
/**
 * Page Object for Login functionality
 */
public class LoginPage {
    
    // 4. Constants
    private static final int TIMEOUT = 10;
    
    // 5. Instance variables
    private WebDriver driver;
    private ElementUtil elementutil;
    private static final Logger logger = LogManager.getLogger(LoginPage.class);
    
    // 6. Locators
    private By loginButton = ObjectRepository.getLocator("LOGIN_BUTTON");
    
    // 7. Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.elementutil = new ElementUtil(driver);
        PageFactory.initElements(driver, this);
    }
    
    // 8. Public action methods
    public void clickLogin() {
        logger.info("Clicking login button");
        elementutil.doClick(loginButton);
    }
    
    // 9. Public getter methods
    public String getSuccessMessage() {
        return elementutil.getTextOfElement(successMessage);
    }
    
    // 10. Private helper methods
    private void waitForPageLoad() {
        // Helper logic
    }
}
```

### Naming Conventions:
- **Classes**: `PascalCase` (LoginPage, TestRunner, DriverManager)
- **Methods**: `camelCase` (clickLoginButton, getSuccessMessage, verifyDashboard)
- **Variables**: `camelCase` (username, loginButton, testContext)
- **Constants**: `UPPER_SNAKE_CASE` (LOGIN_BUTTON, SUCCESS_MESSAGE, TIMEOUT)
- **Packages**: `lowercase` (stepdef, utils, pages, driverFactory)

### Indentation:
- Use **4 spaces** (never tabs)
- Align opening/closing braces vertically
- Single blank line between methods
- Two blank lines between class sections

---

## Common Modifications

### 1. Adding New Page Object

**Step 1**: Create page class
```java
package pages.Dashboard;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import utils.ElementUtil;
import utils.ObjectRepository;

public class DashboardPage {
    private WebDriver driver;
    private ElementUtil elementutil;
    private By dashboardTitle = ObjectRepository.getLocator("DASHBOARD_TITLE");
    
    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.elementutil = new ElementUtil(driver);
        PageFactory.initElements(driver, this);
    }
    
    public boolean isDashboardTitleDisplayed() {
        return elementutil.isElementDisplayed(dashboardTitle);
    }
}
```

**Step 2**: Add locators to properties
```properties
# environment/QA/Locator.properties
DASHBOARD_TITLE=css:h1.dashboard-title
```

**Step 3**: Register in PageObjectManager
```java
private DashboardPage dashboardPage;

public DashboardPage getDashboardPage() {
    return (dashboardPage == null) ? dashboardPage = new DashboardPage(driver) : dashboardPage;
}
```

**Step 4**: Use in step definition
```java
@Then("User should see dashboard")
public void verify_dashboard() {
    DashboardPage dashboardPage = testContext.getPageObjectManager().getDashboardPage();
    Assert.assertTrue(dashboardPage.isDashboardTitleDisplayed());
}
```

### 2. Adding New ElementUtil Method

```java
// File: utils/ElementUtil.java
public void waitForTextToBePresent(By locator, String text, int timeout) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
}
```

### 3. Adding New Utility Class
```java
package utils;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;

public class JsonDataReader {
    
    public static <T> T readJsonData(String filePath, Class<T> classType) {
        try (FileReader reader = new FileReader(filePath)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, classType);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON file: " + filePath, e);
        }
    }
}
```

---

## Error Handling Best Practices

### In Page Objects:
```java
public void clickSubmit() {
    try {
        logger.info("Attempting to click submit button");
        elementutil.doClick(submitButton);
    } catch (TimeoutException e) {
        logger.error("Submit button not clickable within timeout");
        throw new RuntimeException("Submit button not found or not clickable", e);
    } catch (NoSuchElementException e) {
        logger.error("Submit button not found in DOM");
        throw new RuntimeException("Submit button locator incorrect", e);
    }
}
```

### In Step Definitions:
```java
@When("User performs critical action")
public void perform_critical_action() {
    try {
        myPage.clickButton();
    } catch (Exception e) {
        logger.error("Critical action failed: {}", e.getMessage());
        Assert.fail("Critical action failed: " + e.getMessage());
    }
}
```

---

## Testing Best Practices

### Scenario Independence:
```gherkin
# ❌ BAD - Scenarios depend on each other
Scenario: Create user account
  When User fills registration form
  
Scenario: Login with new account
  When User logs in  # Depends on previous scenario!

# ✅ GOOD - Each scenario is independent
Scenario: Create user account
  When User fills registration form
  Then User should see success message
  
Scenario: Login with valid credentials
  Given User has valid account
  When User enters valid credentials
  Then User should see dashboard
```

### Data Cleanup:
```java
// In ApplicationHook.java @After method
@After
public void tearDown(Scenario scenario) {
    if (scenario.isFailed()) {
        takeScreenshot(scenario);
    }
    // Clean up test data if needed
    // Logout if needed
    testContext.closeDriver();
}
```

---

## Real Examples from Current Project

### Existing LoginPage Pattern:
```java
public void enterUsernameAndPassword(String userName, String password) {
    logger.info("Entering username and password");
    elementutil.doSendKeys(loginUsername, userName);
    elementutil.doSendKeys(loginPassword, password);
    elementutil.doClick(loginButton);
}
```

### Existing Step Definition Pattern:
```java
@Given("User launch the browser")
public void user_launch_the_browser() {
    environmentName = DriverManager.returnEnvironment();
    String browserName = DriverManager.returnBrowserName();
    testContext.initializeDriver(browserName);
    loginPage = testContext.getPageObjectManager().getLoginPage();
}
```

### Existing PageObjectManager Pattern:
```java
public LoginPage getLoginPage() {
    return (loginPage == null) ? loginPage = new LoginPage(driver) : loginPage;
}
```

---

## Summary of Critical Rules

1. **ALWAYS** add `// --Generated by Copilot` header
2. **NEVER** hardcode locators - use `ObjectRepository.getLocator()`
3. **NEVER** use raw `driver.findElement()` - use `ElementUtil` methods
4. **ALWAYS** inject `TestContext` via constructor in step definitions
5. **ALWAYS** register new pages in `PageObjectManager`
6. **ALWAYS** initialize driver in first `@Given` step
7. **ALWAYS** use `PropertiesFileManager` for configuration values
8. **ALWAYS** add locators to environment-specific `Locator.properties`
9. **ALWAYS** use 4-space indentation
10. **ALWAYS** add logger and log important actions

---

**Follow these patterns consistently to maintain framework integrity and code quality.**

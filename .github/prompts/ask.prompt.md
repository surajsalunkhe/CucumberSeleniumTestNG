---
agent: ask
description: 'Explain framework related QA for Cucumber-Selenium-TestNG automation project.'
model: GPT-4o
---
# Cucumber-Selenium-TestNG Framework Context

## Project Overview
This is a **Cucumber BDD + Selenium WebDriver + TestNG** test automation framework implementing:
- Page Object Model (POM) design pattern
- Picocontainer dependency injection
- Parallel test execution capabilities
- Environment-based configuration (QA/UAT)
- ExtentReports integration with screenshot capture
- Jira XRay test result integration

## Core Architecture

### Driver Management
- **DriverManager** (`driverFactory/DriverManager.java`): ThreadLocal WebDriver instances for thread-safe parallel execution
- Supports Chrome, Firefox, Edge, Safari (all run headless by default)
- System properties: `-Dbrowser=edge` (default), `-Denv=QA` (default)
- WebDriverManager for automatic driver binary management

### Dependency Injection
- **TestContext** (`cucumber/TestContext.java`): Central DI container managing driver lifecycle
- **PageObjectManager** (`objectManager/PageObjectManager.java`): Lazy-initialized page object factory
- Picocontainer auto-wires dependencies via constructor injection in step definitions

### Element Interaction
- **ElementUtil** (`utils/ElementUtil.java`): Selenium wrapper with automatic retry logic
- Handles StaleElementReferenceException and TimeoutException
- Falls back to JavascriptExecutor when standard methods fail
- All methods use explicit waits (10-15 seconds)

### Lifecycle Hooks
- **ApplicationHook** (`appHooks/ApplicationHook.java`):
  - `@AfterStep`: Conditional screenshot capture
  - `@After`: Failure screenshots + driver cleanup
  - `@AfterSuite`: Optional Jira XRay integration

## Configuration Management

### Environment-Based Setup
- Properties resolve from `environment/{QA|UAT}/*.properties`
- **PropertiesFileManager**: Cascading search across multiple property files
- **ObjectRepository**: Centralized locator management from `Locator.properties`
- Locator format: `LOCATOR_NAME=type:value` (e.g., `LOGIN_BUTTON=xpath://button[@id='submit']`)

### Key Configuration Files
- `environment.properties`: URLs, feature flags
- `ApplicationUser.properties`: Credentials
- `Locator.properties`: Element locators
- `Assertion.properties`: Expected values
- `TestData/*.xlsx`: Excel-based test data

## Execution Patterns

### Test Flow
1. TestNG triggers `TestRunner.java` with `@DataProvider(parallel=true)`
2. Cucumber executes feature files with glue paths: `stepDef/`, `appHooks/`
3. Step definitions initialize driver via `testContext.initializeDriver(browser)` in first Given step
4. Page objects retrieved from `testContext.getPageObjectManager().getXxxPage()`
5. Teardown in `@After` hook captures screenshots and quits driver

### Parallel Execution
- Thread count configured in `pom.xml`: `<dataproviderthreadcount>1</dataproviderthreadcount>`
- Each scenario gets isolated ThreadLocal driver instance
- Never share driver references between scenarios

## Build Commands

```bash
# Default execution (QA environment, edge browser, headless)
mvn clean verify

# Specific environment and browser
mvn clean verify -Denv=UAT -Dbrowser=chrome

# Tagged scenarios
mvn clean test -Dbrowser=firefox "-Dcucumber.filter.tags=@Smoke"

# Rerun failed tests
mvn clean verify -Dtest=FailedTestcaseRunner
```

## Reporting

### Generated Reports
- **ExtentReports**: `target/ExtentReports/` (HTML with screenshots)
- **Cucumber JSON**: `target/json-report/cucumber.json`
- **Cucumber HTML**: `target/cucumber-reports.html`
- **Failed scenarios**: `target/failedrerun.txt`
- **Test output**: `test-output/SparkReport/TestExecutionResult.html`

### Configuration Flags
- `JiraUpdate=Yes`: POST results to Jira XRay endpoint
- `SEND_EMAIL_AFTER_EXECUTION=Yes`: Email reports after execution
- `ATTACH_SCREENSHOT_TO_REPORT=Yes`: Screenshot every step (slower, default: No)

## Development Guidelines

### Adding New Tests (5-Step Process)
1. Create feature file in `src/test/resources/features/`
2. Add locators to `environment/{env}/Locator.properties`
3. Create Page Object in `pages/` with ElementUtil wrapper
4. Register page in `PageObjectManager` with lazy-init getter
5. Write step definitions with constructor-injected TestContext

### Mandatory Patterns
- **Never hardcode locators**: Use `ObjectRepository.getLocator("KEY")`
- **Never use raw WebDriver**: Use `ElementUtil.doClick/doSendKeys` methods
- **Always initialize driver**: Call `testContext.initializeDriver(browser)` in first Given step
- **Register all pages**: Add getter to `PageObjectManager` or page won't be accessible
- **Use DI**: Constructor-inject `TestContext`, never manually instantiate

### Code Quality Standards
- Indent with 4 spaces (never tabs)
- Line length limit: 120 characters
- Use meaningful variable names
- Add comments for complex logic
- Follow existing naming conventions

## Common Pitfalls & Solutions

| Problem | Solution |
|---------|----------|
| NullPointerException on driver | Call `testContext.initializeDriver(browser)` in first Given step |
| Stale element exceptions | Use `ElementUtil` methods (built-in retry logic) |
| Locator not found | Add to `Locator.properties` with exact case-sensitive key |
| Property returns null | Verify `-Denv` matches folder name (case-sensitive) |
| Parallel test flakiness | Verify ThreadLocal isolation, no shared state |

## Project Structure Reference

```
src/test/
├── java/
│   ├── appHooks/           # Cucumber lifecycle hooks
│   ├── cucumber/           # TestContext DI container
│   ├── driverFactory/      # ThreadLocal WebDriver management
│   ├── listeners/          # TestNG listeners
│   ├── objectManager/      # Page object factory
│   ├── pages/              # Page Objects (POM)
│   ├── stepDef/            # Cucumber step definitions
│   ├── testRunner/         # TestNG + Cucumber runner
│   └── utils/              # ElementUtil, PropertiesFileManager, ObjectRepository
└── resources/
    ├── environment/        # QA/UAT configuration
    │   ├── QA/
    │   │   ├── environment.properties
    │   │   ├── Locator.properties
    │   │   ├── ApplicationUser.properties
    │   │   └── TestData/*.xlsx
    │   └── UAT/
    └── features/           # Cucumber .feature files
```

## Key Design Decisions

- **ThreadLocal drivers**: Enables parallel execution without race conditions
- **Picocontainer**: Automatic dependency injection for step definitions
- **Lazy-initialized PageObjectManager**: Pages created only when needed
- **Separate environment folders**: QA/UAT can have different configs without code changes
- **ElementUtil wrapper**: Centralizes retry logic and explicit waits
- **Headless by default**: Faster CI/CD execution (remove for local debugging)

## Code Examples

### Page Object Example
```java
public class LoginPage {
    private WebDriver driver;
    private ElementUtil elementUtil;
    private By usernameField = ObjectRepository.getLocator("LOGIN_USERNAME");
    
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.elementUtil = new ElementUtil(driver);
        PageFactory.initElements(driver, this);
    }
    
    public void enterUsername(String username) {
        elementUtil.doSendKeys(usernameField, username);
    }
}
```

### Step Definition Example
```java
public class LoginStepDef {
    private TestContext testContext;
    private LoginPage loginPage;
    
    public LoginStepDef(TestContext context) {
        this.testContext = context;
    }
    
    @Given("User launch the browser")
    public void user_launch_browser() {
        String browser = DriverManager.returnBrowserName();
        testContext.initializeDriver(browser);
        loginPage = testContext.getPageObjectManager().getLoginPage();
    }
}
```

## Additional Resources

- Framework instructions: `.github/copilot-instructions.md`
- Epic specifications: `epics/LoginModule_Spec.md`
- User stories: `userstory/*.md`
- Maven configuration: `pom.xml`
- TestNG configuration: `testng.xml`
- Logs: `logs/` directory
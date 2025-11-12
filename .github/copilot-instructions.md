# AI Agent Instructions for Cucumber-Selenium-TestNG Framework

## Architecture Overview

This is a **Cucumber BDD + Selenium WebDriver + TestNG** test automation framework using Page Object Model (POM) with dependency injection via Picocontainer. Tests run in parallel with environment-based configuration.

### Core Components
- **DriverManager** (`driverFactory/`): ThreadLocal WebDriver management supporting Chrome, Firefox, Edge, Safari with WebDriverManager auto-setup
- **TestContext** (`cucumber/`): Central DI container managing driver lifecycle and PageObjectManager
- **PageObjectManager** (`objectManager/`): Factory pattern for lazy-loading page objects
- **ApplicationHook** (`appHooks/`): Cucumber hooks for driver setup/teardown, screenshot capture on failure
- **ElementUtil** (`utils/`): Selenium wrapper with explicit waits, stale element retry logic, JS fallbacks

### Test Execution Flow
1. TestNG triggers `TestRunner.java` via `testng.xml`
2. `@Before` hook in ApplicationHook (currently empty - driver init happens in step defs)
3. Step definitions call `TestContext.initializeDriver(browser)` which sets up ThreadLocal driver
4. Tests execute via Cucumber feature files with glue to `stepDef/` and `appHooks/`
5. `@After` hook captures screenshots on failure, quits driver
6. `@AfterSuite` optionally updates Jira via REST API with cucumber.json results

## Critical Patterns

### Environment-Based Configuration
**All properties resolve dynamically** based on `-Denv` system property (defaults to "QA"):
```java
// Properties resolution searches: environment/QA/*.properties or environment/UAT/*.properties
String value = PropertiesFileManager.getPropertyValue("MyURL"); // Reads from environment/{QA|UAT}/environment.properties
```

**Multi-file property search**: `PropertiesFileManager` searches `environment.properties` first, then all `.properties` files in the environment folder if key not found.

### Locator Management
**Do NOT hardcode locators in Page Objects**. Use `ObjectRepository.getLocator()`:
```java
By loginButton = ObjectRepository.getLocator("LOGIN_BUTTON"); // Reads from environment/{env}/Locator.properties
// Format in Locator.properties: LOGIN_BUTTON=xpath://button[@id='submit']
```

Supported locator types: `id`, `name`, `xpath`, `css`, `class`, `linktext`, `partiallinktext`

### Page Object Pattern
All page classes follow this structure:
```java
public class LoginPage {
    WebDriver driver;
    ElementUtil elementutil;
    By loginButton = ObjectRepository.getLocator("LOGIN_BUTTON"); // Lazy load via ObjectRepository
    
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        elementutil = new ElementUtil(driver);
        PageFactory.initElements(driver, this);
    }
}
```

**Register new pages** in `PageObjectManager.getXxxPage()` with lazy initialization pattern.

### Step Definition DI Pattern
Inject `TestContext` via constructor (Picocontainer auto-wires):
```java
public class StepDefinition {
    TestContext testContext;
    LoginPage loginPage;
    
    public StepDefinition(TestContext context) {
        this.testContext = context;
    }
    
    @Given("User launch the browser")
    public void user_launch_the_browser() {
        String browserName = DriverManager.returnBrowserName(); // Reads -Dbrowser or defaults to "edge"
        testContext.initializeDriver(browserName);
        loginPage = testContext.getPageObjectManager().getLoginPage();
    }
}
```

### Parallel Execution
Configured via TestNG `@DataProvider(parallel = true)` in `TestRunner`:
- Thread count controlled by `pom.xml` → `<dataproviderthreadcount>1</dataproviderthreadcount>`
- Each scenario gets isolated ThreadLocal driver instance
- Increase thread count for faster execution but monitor resource usage

## Build & Test Commands

```bash
# Run all tests with default environment (QA) and browser (edge)
mvn clean verify

# Run specific environment and browser
mvn clean verify -Denv=UAT -Dbrowser=chrome

# Run specific tag with environment
mvn clean test -Denv=QA -Dbrowser=firefox -Dtest=TestRunner "-Dcucumber.filter.tags=@Smoke"

# Run failed tests (auto-generated rerun)
# After first run, FailedTestcaseRunner executes from target/failedrerun.txt
```

### Maven Lifecycle Hooks
- `verify` phase: Runs tests via surefire-plugin
- `post-integration-test` phase: Generates ExtentReports from `target/json-report/cucumber.json`

## Configuration Flags (environment.properties)

```properties
JiraUpdate=No              # Enable Jira XRay test result import via REST API
SEND_EMAIL_AFTER_EXECUTION=No  # Email reports (configure in SendTestAutomationReport)
ATTACH_SCREENSHOT_TO_REPORT=Yes  # AfterStep screenshot capture (use "No" for faster runs)
```

**Jira Integration**: Requires `JiraUserID`, `JiraPassword`, `JiraAPIURL` properties. Posts `target/json-report/cucumber.json` to `/import/execution/cucumber` endpoint.

## Adding New Tests

1. **Create feature file** in `src/test/resources/features/`:
   ```gherkin
   @QA @UAT
   Scenario: My test scenario
     Given User launch the browser
     When User performs action
   ```

2. **Add locators** to `environment/{QA|UAT}/Locator.properties`:
   ```properties
   MY_BUTTON=xpath://button[@id='myBtn']
   ```

3. **Create Page Object** in `pages/MyFeature/MyPage.java`:
   ```java
   By myButton = ObjectRepository.getLocator("MY_BUTTON");
   public void clickMyButton() { elementutil.doClick(myButton); }
   ```

4. **Register in PageObjectManager**:
   ```java
   public MyPage getMyPage() {
       return (myPage == null) ? myPage = new MyPage(driver) : myPage;
   }
   ```

5. **Write step definitions** in `stepDef/` (inject TestContext, get page from manager)

## Common Pitfalls

- **Driver not initialized**: Always call `testContext.initializeDriver()` in first Given step
- **Stale elements**: `ElementUtil.doClick/doSendKeys` has built-in retry logic - use it instead of raw WebDriver
- **Locator not found**: Ensure key exists in `Locator.properties` for target environment
- **Property not resolving**: Check environment folder matches `-Denv` value (case-sensitive)
- **Parallel test flakiness**: Verify ThreadLocal isolation - never share driver references between scenarios
- **Headless mode**: All browsers configured with `--headless` by default in `DriverManager`

## Reporting

- **ExtentReports**: Generated in `target/` after `post-integration-test` phase
- **Cucumber JSON**: `target/json-report/cucumber.json` (used for Jira integration)
- **HTML**: `target/cucumber-reports.html`
- **Logs**: Log4j2 configured via `log4j2.properties`

## Testing Data Approaches

1. **Inline Examples** (Scenario Outline in feature file)
2. **Excel-based**: `ExcelReader.readExcelDataForLogin()` reads from `environment/{env}/TestData/*.xlsx`
3. **Properties-based**: Test URLs, credentials in `ApplicationUser.properties`

When reading Excel: First call `ExcelReader`, then iterate rows in step definition calling page methods.

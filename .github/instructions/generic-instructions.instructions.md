---
applyTo: "**/*"
---
Provide general project context and coding guidelines that AI should follow when generating code, answering questions, or reviewing changes.

1. **Project Overview**
    - This is a Cucumber BDD + Selenium WebDriver + TestNG test automation framework.
    - Uses Page Object Model (POM) with dependency injection via Picocontainer.
    - Supports parallel test execution with environment-based configuration.
    - Follows Maven project structure with standard directory layout.

2. **Framework Architecture**
    - **DriverManager**: ThreadLocal WebDriver management supporting Chrome, Firefox, Edge, Safari.
    - **TestContext**: Central DI container managing driver lifecycle and PageObjectManager.
    - **PageObjectManager**: Factory pattern for lazy-loading page objects.
    - **ApplicationHook**: Cucumber hooks for driver setup/teardown and screenshot capture.
    - **ElementUtil**: Selenium wrapper with explicit waits and retry logic.

3. **Configuration Management**
    - Environment-based configuration using `-Denv` system property (QA/UAT).
    - Properties resolve dynamically from `environment/{env}/*.properties`.
    - Locators managed centrally in `Locator.properties` using `ObjectRepository.getLocator()`.
    - Test data stored in environment-specific folders.

4. **Best Practices**
    - Never hardcode values; use property files for configuration.
    - Follow existing patterns for Page Objects, Step Definitions, and utilities.
    - Maintain ThreadLocal isolation for parallel execution.
    - Use ElementUtil methods instead of raw WebDriver calls.
    - Implement proper exception handling and logging.
    - Keep test scenarios independent and self-contained.

5. **Code Quality Standards**
    - Follow consistent naming conventions across the framework.
    - Add meaningful comments and documentation.
    - Write reusable, maintainable code.
    - Ensure proper indentation and formatting (4 spaces).
    - Limit line length to 120 characters for readability.

6. **Testing Approach**
    - Use BDD principles with clear Given-When-Then structure.
    - Tag scenarios appropriately (@Smoke, @Regression, @QA, @UAT).
    - Support multiple data sources (inline, Excel, properties).
    - Generate comprehensive reports (ExtentReports, Cucumber JSON).

7. **Collaboration**
    - Follow existing code style and architectural patterns.
    - Use pull requests for all changes.
    - Update documentation when adding new features or patterns.
    - Maintain backward compatibility when modifying shared utilities.

---
applyTo: "**/*.xml"
---
Provide project context and coding guidelines that AI should follow when working with XML files.

1. **File Types**
    - **pom.xml**: Maven project configuration and dependency management.
    - **testng.xml**: TestNG suite configuration for test execution.
    - **Extent-config.xml**: ExtentReports configuration for test reporting.
    - **html-config.xml**: HTML report configuration.
    - **log4j2.xml** (if present): Logging configuration.

2. **POM Configuration**
    - Maintain proper dependency versions and avoid version conflicts.
    - Group dependencies logically (Selenium, Cucumber, TestNG, Reporting, Utils).
    - Use properties for version management (e.g., `<selenium.version>4.x.x</selenium.version>`).
    - Configure maven-surefire-plugin for TestNG execution.
    - Set proper encoding (UTF-8) and Java version compatibility.

3. **TestNG Configuration**
    - Define test suites with meaningful names.
    - Configure parallel execution settings (thread count, data provider threads).
    - Use parameter tags for environment and browser configuration.
    - Include listeners for logging and reporting.
    - Organize test classes logically by feature or module.

4. **Report Configuration**
    - Customize ExtentReports with project-specific branding.
    - Configure report location and naming conventions.
    - Set appropriate logging levels and detail.
    - Include relevant metadata (environment, browser, timestamp).

5. **Best Practices**
    - Maintain proper XML indentation (2 or 4 spaces consistently).
    - Use comments to explain complex configurations.
    - Validate XML syntax before committing changes.
    - Keep configuration files organized and easy to read.
    - Avoid hardcoding values; use properties where possible.

6. **Dependency Management**
    - Exclude transitive dependencies causing conflicts.
    - Use latest stable versions of testing frameworks.
    - Document reasons for specific version choices or exclusions.
    - Regularly update dependencies for security and features.

7. **Collaboration**
    - Review POM changes carefully as they affect the entire team.
    - Test configuration changes locally before committing.
    - Document any special configuration requirements in comments.
    - Use pull requests for all XML configuration changes.

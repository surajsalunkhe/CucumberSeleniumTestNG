---
applyTo: "**/*.properties"
---
Provide project context and coding guidelines that AI should follow when working with properties files.

1. **File Organization**
    - Properties files are located in `src/test/resources/environment/{env}/`.
    - Separate properties files for different concerns: environment, locators, users, assertions.
    - Environment-specific folders: QA and UAT.

2. **Naming Conventions**
    - Use UPPERCASE_WITH_UNDERSCORES for property keys.
    - Use descriptive names that clearly indicate the purpose.
    - Group related properties together with blank lines for separation.

3. **Property Categories**
    - **environment.properties**: URLs, browser settings, execution flags (JiraUpdate, SEND_EMAIL_AFTER_EXECUTION).
    - **Locator.properties**: UI element locators in format `KEY=type:value` (e.g., `LOGIN_BUTTON=xpath://button[@id='submit']`).
    - **ApplicationUser.properties**: User credentials and test account information.
    - **Assertion.properties**: Expected values for test validations.

4. **Locator Format**
    - Format: `LOCATOR_NAME=locatorType:locatorValue`
    - Supported types: id, name, xpath, css, class, linktext, partiallinktext
    - Example: `USERNAME_FIELD=id:username`, `SUBMIT_BTN=xpath://button[@type='submit']`

5. **Best Practices**
    - Never hardcode sensitive information; use property files.
    - Maintain consistency across QA and UAT environments.
    - Add comments using # to explain complex or non-obvious properties.
    - Use meaningful values that are easy to understand and maintain.
    - Keep properties alphabetically sorted within their category for easy lookup.

6. **Configuration Flags**
    - Use Yes/No or true/false for boolean flags.
    - Document the purpose of each flag with inline comments.
    - Examples: `JiraUpdate=No`, `ATTACH_SCREENSHOT_TO_REPORT=Yes`

7. **Collaboration**
    - Update both QA and UAT property files when adding new properties.
    - Use pull requests for changes to production environment properties.
    - Notify team when adding new required properties.

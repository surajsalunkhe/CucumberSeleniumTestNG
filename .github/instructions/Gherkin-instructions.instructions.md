---
applyTo: "**/*.feature"
---
Provide project context and coding guidelines that AI should follow when generating code, answering questions, or reviewing changes.

1. **Project Structure**
    - The project follows a standard Maven directory layout.
    - Feature files are located in `src/test/resources/features/`.

2. **Gherkin Syntax**
    - Use Gherkin syntax for writing feature files.
    - Each scenario should have a clear Given-When-Then structure.

3. **Step Definitions**
    - Step definitions are located in `src/test/java/com/example/steps/`.
    - Reuse existing step definitions where possible to avoid duplication.

4. **Best Practices**
    - Keep scenarios independent and self-contained.
    - Use meaningful names for feature files and scenarios.
    - Include tags for categorizing scenarios (e.g., @smoke, @regression).
    - Comments should be added with # symbol
    - Avoid using triple quotes for comments; use # for Gherkin compatibility.
    - Use comments at the beginning of feature files to describe their purpose.

5. **Collaboration**
    - Encourage team members to review and contribute to feature files.
    - Use pull requests for all changes to feature files.
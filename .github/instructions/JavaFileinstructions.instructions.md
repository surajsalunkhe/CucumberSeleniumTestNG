---
applyTo: "**/*.java"
---
Provide project context and coding guidelines that AI should follow when generating code, answering questions, or reviewing changes.

1. **Project Structure**
    - The project follows a standard Maven directory layout.
    - Source code is located in `src/test/java/`.
    - Use meaningful package names that reflect the module structure.

2. **Java Coding Standards**
    - Use Java 8 or higher features where appropriate (e.g., Streams, Lambdas).
    - Class names should be in PascalCase; method and variable names in camelCase.
    - Use descriptive names for classes, methods, and variables.
    - Limit line length to 120 characters for better readability.

3. **Code Organization**
    - Organize imports: group by standard library, third-party libraries, and project-specific imports.
    - Maintain consistent formatting: 4 spaces for indentation, braces on the same line.
    - Adhere to SOLID principles and design patterns (e.g., Page Object Model for testing).

4. **Best Practices**
    - Ensure proper exception handling; avoid empty catch blocks.
    - Use logging frameworks (e.g., SLF4J, Log4j) instead of System.out.println for logging.
    - Add Javadoc comments to all public classes and methods.
    - Write unit tests for all logic-heavy classes in the `src/test/java` directory.
    - Follow existing code style (indentation, brackets, naming) and use static code analysis tools where set up.

5. **Collaboration**
    - Follow existing code style and patterns in the repository.
    - Use pull requests for all changes to Java files.
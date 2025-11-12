---
applyTo: "**/*.xlsx"
---
Provide project context and coding guidelines that AI should follow when working with Excel test data files.

1. **File Location**
    - Excel files are located in `src/test/resources/environment/{env}/TestData/`.
    - Separate test data for QA and UAT environments.
    - Use descriptive file names indicating the feature or test scenario.

2. **Workbook Structure**
    - Each workbook can contain multiple sheets for different test scenarios.
    - Use the first sheet as the primary data source unless specified otherwise.
    - Keep sheet names short, descriptive, and without special characters.

3. **Data Format**
    - First row contains column headers (field names).
    - Use clear, descriptive header names matching page object fields.
    - Each subsequent row represents one test data set.
    - Use consistent data types within columns.

4. **Column Organization**
    - Group related fields together (e.g., user info, address fields).
    - Use standard naming conventions: camelCase or UPPER_CASE.
    - Include a "TestCase" or "Scenario" column to identify data sets.
    - Add "ExpectedResult" columns for validation data.

5. **Best Practices**
    - Keep data sets independent and self-contained.
    - Use realistic test data that represents actual use cases.
    - Avoid empty rows between data sets.
    - Mark invalid/negative test data clearly in a separate column.
    - Use data-driven approach for scenarios requiring multiple iterations.

6. **Data Management**
    - Update test data when application requirements change.
    - Maintain separate data for positive and negative scenarios.
    - Use meaningful values that make test results easy to understand.
    - Keep sensitive data masked or use test accounts only.

7. **Reading Excel Data**
    - Use `ExcelReader` utility class to read data.
    - Validate data format before test execution.
    - Handle missing or malformed data gracefully with appropriate error messages.

8. **Collaboration**
    - Document the purpose of each Excel file in feature file comments.
    - Coordinate with team when updating shared test data files.
    - Use version control for test data files to track changes.
    - Notify team members of structural changes to Excel files.

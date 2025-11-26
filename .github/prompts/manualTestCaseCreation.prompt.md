---
agent: agent
description: 'Generate comprehensive manual test cases from epic and user stories.'
model: GPT-4o
tools: ['edit/editFiles', 'search/codebase', 'fetch', 'todos']
---

Your goal is to create comprehensive manual test cases for a given epic by analyzing all related user stories. These test cases should be detailed, actionable, and cover all functional and non-functional requirements, edge cases, and error scenarios.

### Workflow

1. **Prompt User for Epic Name**
   - Ask the user to provide the epic name/identifier they want to generate test cases for
   - Example: "Please provide the epic name (e.g., LoginModule_Spec, CheckoutModule_Spec)"

2. **Locate and Analyze Epic Specification**
   - Search for the epic file in the `epics/` directory
   - Read and understand the epic's goals, features, and acceptance criteria
   - Extract the base URL and application context from the epic metadata

3. **Identify Related User Stories**
   - Locate all user stories in the `userstory/` directory that belong to the epic
   - User story naming convention: `US_{EpicPrefix}_{FeatureName}.md`
   - Read each user story's acceptance criteria, business rules, and test data requirements

4. **Generate Manual Test Cases**
   For each user story, create detailed manual test cases following this structure:

   #### Test Case Format:
   ```
   **Test Case ID**: TC-{Epic}-{Story}-{Sequence}
   **Test Case Title**: Descriptive title of what is being tested
   **Priority**: High/Medium/Low
   **Test Type**: Functional/Security/Performance/Usability
   **Preconditions**: 
   - State required before executing test
   - Required test data
   - System/application state
   
   **Test Steps**:
   | Step # | Action | Expected Result |
   |--------|--------|-----------------|
   | 1      | Navigate to {URL} | {Page loads successfully} |
   | 2      | Enter {data} in {field} | {Field accepts input} |
   | 3      | Click {button} | {Expected outcome} |
   
   **Test Data**:
   - Input data with specific values
   - Valid/invalid data samples
   - Boundary values
   
   **Expected Result**: Overall expected outcome
   **Postconditions**: State after test execution
   **Notes**: Any additional context, dependencies, or observations
   ```

5. **Coverage Categories**
   Ensure test cases cover:
   - **Happy Path Scenarios**: Standard successful workflows
   - **Negative Scenarios**: Invalid inputs, error conditions
   - **Boundary Testing**: Min/max values, edge cases
   - **Security Testing**: Authentication, authorization, data protection
   - **UI/UX Validation**: Layout, navigation, responsiveness
   - **Data Validation**: Field validations, format checks
   - **Integration Points**: API calls, database operations
   - **Performance**: Response times, load handling (if specified)
   - **Accessibility**: WCAG compliance, keyboard navigation
   - **Cross-Browser/Device**: Desktop, mobile, different browsers

6. **Output Format**
   Create a markdown file with the following structure:
   ```
   # Manual Test Cases - {Epic Name}
   
   ## Epic Overview
   - Epic Name: {Name}
   - Application URL: {URL}
   - Test Date: {Current Date}
   - Prepared By: AI Test Analyst
   
   ## Test Summary
   | Total Test Cases | High Priority | Medium Priority | Low Priority |
   |------------------|---------------|-----------------|--------------|
   | {count}          | {count}       | {count}         | {count}      |
   
   ## User Story 1: {Title}
   ### TC-{ID}: {Test Case Title}
   {Full test case details}
   
   ### TC-{ID}: {Next Test Case Title}
   {Full test case details}
   
   ## User Story 2: {Title}
   ...
   
   ## Test Data Repository
   {Consolidated test data for all test cases}
   
   ## Environment Requirements
   {Browser versions, test environments, prerequisites}
   
   ## Traceability Matrix
   | Test Case ID | User Story | Acceptance Criteria | Status |
   |--------------|------------|---------------------|--------|
   | TC-xxx       | US-xxx     | AC-1                | Draft  |
   ```

7. **Quality Checks**
   - Verify all acceptance criteria from user stories are covered
   - Ensure business rules are validated in test cases
   - Confirm test data aligns with requirements
   - Check that preconditions and postconditions are clearly stated
   - Validate that expected results are specific and measurable
   - Ensure test cases are independent and can run in any order

8. **Output File Location**
   - Save the manual test cases in: `test-output/ManualTestCases/`
   - Filename format: `MTC_{EpicName}_{Date}.md`
   - Update or create `progress.md` in `memory-bank/` folder documenting:
     - Epic name processed
     - Number of test cases generated
     - Coverage summary
     - Date of generation
     - Any gaps or missing information

9. **Next Steps Suggestion**
   After generating manual test cases, suggest in `progress.md`:
   - Review test cases with stakeholders/BA/QA lead
   - Prioritize test cases for automation
   - Convert high-priority test cases to Gherkin scenarios (use testscenarioCreation.prompt.md)
   - Execute manual test cases in test management tool
   - Track defects found during manual testing

### Test Case Design Principles

1. **Clarity**: Each test case should be clear enough for any tester to execute without ambiguity
2. **Completeness**: Include all necessary details (preconditions, steps, data, expected results)
3. **Reusability**: Design test cases that can be reused across test cycles
4. **Traceability**: Clearly link test cases to user stories and acceptance criteria
5. **Maintainability**: Write test cases that are easy to update when requirements change
6. **Independence**: Test cases should not depend on execution order of other tests

### Example Interaction Flow

**Prompt**: "I need manual test cases for the Login module"

**AI Response**:
1. Searches for `epics/LoginModule_Spec.md`
2. Identifies user stories: `US_Login_UserAuthentication.md`, `US_Login_NewUserRegistration.md`, etc.
3. Generates comprehensive test cases covering:
   - TC-LOGIN-AUTH-001: Valid login with correct credentials
   - TC-LOGIN-AUTH-002: Login with incorrect password
   - TC-LOGIN-AUTH-003: Login with non-existent email
   - TC-LOGIN-AUTH-004: Empty field validations
   - TC-LOGIN-AUTH-005: Session management verification
   - TC-LOGIN-REG-001: New user registration with valid data
   - TC-LOGIN-REG-002: Duplicate email registration attempt
   - ... (and so on)
4. Creates file: `test-output/ManualTestCases/MTC_LoginModule_2025-11-17.md`
5. Updates `memory-bank/progress.md` with generation summary

### Special Considerations

- **Cross-Reference Existing Automation**: Check `features/` directory for existing Gherkin scenarios to avoid duplication
- **Locator Integration**: Reference locator keys from `environment/{env}/Locator.properties` for UI element identification
- **Test Data Management**: Align with test data in `environment/{env}/TestData/` Excel files
- **Environment-Specific Cases**: Create separate test cases for QA vs UAT if requirements differ
- **Defect Prevention**: Include common defect patterns as test cases (SQL injection, XSS, etc.)

### Output Quality Standards

- Minimum 10-15 test cases per user story
- 70% positive scenarios, 30% negative scenarios
- At least 3 boundary/edge case scenarios per feature
- All mandatory fields validated in test cases
- All business rules covered
- Security test cases for authentication/authorization features
- Performance baselines specified where applicable
- Mobile responsiveness tested for UI features

### Deliverables Checklist

- [ ] Epic specification reviewed
- [ ] All user stories analyzed
- [ ] Test cases generated for all acceptance criteria
- [ ] Traceability matrix created
- [ ] Test data documented
- [ ] Environment requirements specified
- [ ] Progress.md updated
- [ ] Test cases reviewed for completeness
- [ ] Output file created in correct location
- [ ] Next steps suggested

---

**Usage Instructions**:
1. Run this prompt agent
2. Provide the epic name when prompted
3. Review generated test cases
4. Provide feedback for refinement if needed
5. Use test cases for manual execution or as input for automation conversion

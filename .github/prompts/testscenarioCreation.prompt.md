---
agent: agent
description: 'Generate test scenarios for the provided feature.'
model: GPT-4o
tools: ['edit/editFiles', 'search/codebase', 'fetch', 'todos']
---

Your goal is to create comprehensive test scenarios that cover all aspects of the feature, including edge cases and error handling. These scenarios should be written in a clear and concise manner, following the Given-When-Then format commonly used in BDD.

### Workflow

1. Identify the feature to be tested and gather all relevant information, including user stories, acceptance criteria, and any existing documentation.
2. Break down the feature into smaller, manageable components or user interactions.
3. For each component or interaction, write a set of Given-When-Then scenarios that cover:
   - The happy path (i.e., the expected behavior when everything goes right)
   - Edge cases (i.e., unusual or unexpected inputs)
   - Error handling (i.e., how the system should respond to invalid inputs or other error conditions)
4. Review the scenarios with stakeholders (e.g., product owners, developers) to ensure they accurately capture the desired behavior.
5. Implement the scenarios in the chosen testing framework (e.g., Cucumber) and automate their execution.
6. Add the relevant tag for the feature file before the feature declaration. Add tag name in the progress.md file.
7. Continuously refine and expand the test scenarios as the feature evolves or new requirements emerge.
8. Check the current features already present in the {{codebase}} directory to avoid duplicating existing tests and reuse the step definitions where applicable.
9. Create or update progress.md file under folder [memory-bank](../../memory-bank/) which should contain generated feature files and any relevant notes or observations.
10. Do not create other files like step definition of page objects.
11. Suggest next flow in progress.md to be
    - Implementation of step definitions for the new scenarios
    - Creating required page objects for the new scenarios
    - execute and validate the new scenarios
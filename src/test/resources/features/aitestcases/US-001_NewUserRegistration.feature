# Feature: New User Registration with Email Validation
# US-001: New User Registration with Email Validation
# Description: Allow new visitors to create accounts with unique email addresses
# Generated from: userstory/US_Login_NewUserRegistration.md
# Last updated: 2025-11-14
# 
# NOTE: This file follows Gherkin-instructions.instructions.md guidelines
# - Comments use # symbol only (no triple quotes)
# - Scenarios are independent and self-contained
# - Step definitions reuse existing patterns from src/test/java/stepDef/
# 
# Business Context:
# The AutomationExercise platform requires unique email registration to enable
# personalized shopping experiences, order tracking, and profile management.
# Registration form is displayed alongside login form on the authentication page.

Feature: New User Registration with Email Validation

  Background:
    Given User launch the browser
    And User navigates to URL "MyTestLoginURL"

  # Happy Path - Critical functionality for new user onboarding
  @QA @UAT @Smoke @Registration @myTest
  Scenario: Successful new user registration with valid credentials
    Given User is on the signup section of the login page
    When User enters name "John Doe" in signup form
    And User enters unique email "newuser001@test.com" in signup form
    And User enters valid password "Test@123" in signup form
    And User clicks on "SIGNUP_BUTTON"
    Then User should see success message "Account created successfully"
    And User account should be created in the system
    And User should be able to login with email "newuser001@test.com" and password "Test@123"
    And User closes the browser

  # Data-driven test covering multiple registration scenarios
  @QA @UAT @Regression @DataDriven @Registration
  Scenario Outline: User registration with various credential combinations
    Given User is on the signup section of the login page
    When User enters name "<Name>" in signup form
    And User enters email "<Email>" in signup form
    And User enters password "<Password>" in signup form
    And User clicks on "SIGNUP_BUTTON"
    Then Verify registration status "<IsRegistrationSuccessful>" and verify message "<Message>"
    And User closes the browser
    Examples:
      | Name          | Email                    | Password    | IsRegistrationSuccessful | Message                        |
      | Jane Smith    | uniqueuser1@test.com     | Password123 | Y                        | Account created successfully   |
      | Test User     | uniqueuser2@test.com     | SecurePass1 | Y                        | Account created successfully   |
      | John Doe      | existing@example.com     | Test@123    | N                        | Email Address already exist!   |
      | Invalid User  | notanemail               | Test@123    | N                        | Please enter a valid email address |

  # Negative test - Duplicate email validation
  @QA @UAT @Negative @Registration
  Scenario: Registration attempt with existing email address
    Given User is on the signup section of the login page
    And There is already a registered user with email "existing@example.com"
    When User enters name "New User" in signup form
    And User enters email "existing@example.com" in signup form
    And User enters password "Test@123" in signup form
    And User clicks on "SIGNUP_BUTTON"
    Then Registration should fail
    And User should see error message "Email Address already exist!"
    And User should remain on the login page
    And Signup form should retain entered name "New User"
    And Password field should be empty for security
    And No new account should be created
    And User closes the browser

  # Validation test - Password strength requirements
  @QA @UAT @Negative @Validation @Registration
  Scenario: Registration with invalid password format
    Given User is on the signup section of the login page
    When User enters name "Test User" in signup form
    And User enters unique email "testuser@example.com" in signup form
    And User enters invalid password "123" that does not meet requirements
    And User clicks on "SIGNUP_BUTTON"
    Then User should see error message "Password must be at least 6 characters"
    And Registration should not proceed
    And No account should be created for email "testuser@example.com"
    And User closes the browser

  # Validation test - Required field enforcement
  @QA @UAT @Negative @Validation @Registration
  Scenario: Registration with missing required fields
    Given User is on the signup section of the login page
    When User leaves name field empty in signup form
    And User leaves email field empty in signup form
    And User leaves password field empty in signup form
    And User clicks on "SIGNUP_BUTTON"
    Then User should see validation error "This field is required" for name field
    And User should see validation error "This field is required" for email field
    And User should see validation error "This field is required" for password field
    And Registration form should not submit
    And No account should be created
    And User closes the browser

  # Validation test - Email format validation
  @QA @UAT @Negative @Validation @Registration
  Scenario Outline: Registration with invalid email formats
    Given User is on the signup section of the login page
    When User enters name "Test User" in signup form
    And User enters invalid email "<InvalidEmail>" in signup form
    And User enters valid password "Test@123" in signup form
    And User clicks on "SIGNUP_BUTTON"
    Then User should see error message "Please enter a valid email address"
    And Registration should not proceed
    And User closes the browser
    Examples:
      | InvalidEmail              |
      | notanemail                |
      | test@                     |
      | @example.com              |
      | test..test@example.com    |

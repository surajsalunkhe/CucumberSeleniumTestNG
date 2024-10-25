Feature: User Login Functionality using Test Data sheet

Feature: User Login Functionality

  Background:
    Given User has test data from Excel

  @QA @DataDriven
  Scenario: User attempts to log in with different credentials
    And User navigates to the site "TestLoginURL"
    When User enters username and password, and clicks on submit button and verify output
    And User closes the browser

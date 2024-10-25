Feature: Verify the login Functionality

  @QA @UAT
  Scenario: Navigate to the site
    Given User launch the browser
    And User navigates to URL "MyURL"
    And User close the browser

  @QA @UAT
  Scenario Outline: Open User Provided site
    Given User navigates to the site "TestLoginURL"
    When User enters "<Username>" and "<Password>" and click on login
    Then Verify "<isLoginSuccessful>" and verify the "<Message>"
    And User close the browser
    Examples:
    |Username|Password    |isLoginSuccessful|Message                  |
    |student |Password123 |Y                |Logged In Successfully   |
    |Test    |Password123 |N                |Your username is invalid!|
    |student |Test@123    |N                |Your password is invalid!|

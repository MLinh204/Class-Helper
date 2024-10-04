Feature: User use case on Class Helper website

  Background:
    Given the browser is open
    And the user is on the Class Helper homepage

  Scenario: User accesses the homepage
    When the user looks at the page
    Then the user should see the needed UI elements

  Scenario: User click home button
    When the user clicks the Home navigation button
    Then user should be redirected to Home page

  Scenario: User clicks logo
    When user clicks logo
    Then user should be redirected to Home page

  Scenario: User clicks Student List button
    When user clicks Student List button
    Then user should be redirected to Student List page
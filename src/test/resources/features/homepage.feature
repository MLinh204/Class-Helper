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

  Scenario: User clicks Quiz button
    When user clicks Quiz button
    Then  user should be redirected to Quiz page

  Scenario: User clicks Play Game button
    When user clicks Play Game button
    Then user should be redirected to Game page

  Scenario: User clicks Functions button
    When user clicks Functions button
    Then dropdown list of functions should be displayed

  Scenario: User clicks Add Student button
    When user clicks Functions button
    And user clicks Add Student button
    Then user should be redirected to Add Student Page

  Scenario: User clicks Random Student button
    When user clicks Functions button
    And user clicks Random Student button
    Then user should be redirected to Random Student Page

  Scenario: User clicks White Board button
    When user clicks Functions button
    And user clicks White Board button
    Then user should be redirected to White Board Page

  Scenario: User clicks Clock button
    When user clicks Functions button
    And user clicks Clock button
    Then user should be redirected to Clock Page

  Scenario: User clicks Scroll to top button
    When user scrolls to footer
    And user clicks Scroll to top button
    Then user should move to the top
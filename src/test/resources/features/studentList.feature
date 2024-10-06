@StudentList
Feature: User use case on Student List page

  Background:
    Given the browser is opened
    And the user is on the Student List page

  Scenario: User accesses Student List
    When the user looks at the student list page
    Then the title should be Student List

  Scenario: User checks elements on Student List page
    When the user looks at the student list page
    Then the user should see the needed student list UI elements

  Scenario: User checks View button of each student
    When the user hovers on each student
    Then the View buttons should display

  Scenario: User selects View button of a student
    When user hovers on the student
    Then View button should be displayed
    And user clicks View button
    Then user should be redirected to student detail page

  Scenario: User selects Name on student
    When user clicks the name of the student
    Then user should be redirected to student detail page
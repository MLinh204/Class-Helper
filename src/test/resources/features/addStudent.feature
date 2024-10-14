@AddStudent
  Feature: User add new student

    Background:
      Given the browser is opened for Create Student
      And the user is on the Create Student page

    Scenario: Access create student page
      When user looks at the create student page
      Then the user should see the needed create student UI elements

    Scenario: Click Return To Student List button
      When user clicks Return To Student List button
      Then system redirects to Student List page

    Scenario: Add Student successfully
      When user fills in Name
      And user chooses Profile picture
      And user chooses power type
      And user clicks Save button
      Then new student should be created
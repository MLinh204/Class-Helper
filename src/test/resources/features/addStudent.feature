@AddStudent
  Feature: User add new student

    Background:
      Given the browser is opened for Create Student
      And the user is on the Create Student page

    Scenario: Access create student page
      When user looks at the create student page
      Then the user should see the needed create student UI elements
Feature: User use case on Class Helper website
  As a user
  I want to see the UI on home page as expected

  Background:
    Given The homepage is accessible

    Scenario: User access to the homepage
      Given : The user is on the homepage
      Then : The user should see the needed UI element

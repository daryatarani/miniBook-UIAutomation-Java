Feature: Elements

  Background: The user should be able to validate each element's functionality
    Given user login to mini Book Store website
    When the user clicks on Element button

  Scenario: The user should be able to validate Radio Button functionality
    And The user selects radio button from the list of elements
    And The user answers Do you like the site question as 'Yes'
    Then A proper message should be displayed based on the user's answer

   Scenario: The user should be able to validate Buttons functionality
    When The user selects buttons from the list of elements
    And The user clicks on double click on me button
    Then  A proper message based on the user selection should be displayed


Feature: Login Data Driven

  Scenario Outline: Login Data Driven
    Given User Launch browser
    And opens URL "https://demo.opencart.com/"
    When User navigate to MyAccount menu
    And click on Login
    And User enters Email as "<email>" and Password as "<password>"
    And Click on Login button
    Then User navigates to MyAccount Page

    Examples: 
      | email                     | password |
      | pavanoltraining@gmail.com | test@123 |
      | pavanol@gmail.com         | test123  |

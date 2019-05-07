Feature: Send greetings emails

  Scenario: Send 1 birthday greetings
    Given I have one employee with birthday
    And I am in the send greetings page
    When I confirm the send greetings option
    Then I Should see Emails sent: 1

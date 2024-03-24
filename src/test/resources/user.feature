Feature: Client management
  As a system administrator
  I want to be able to delete clients
  So that I can manage the client database effectively

  Scenario: Deleting a client
    Given a client exists with the following details
      | firstName | lastName | email           |
      | john   | white    | white@gmail.com  |
    When I delete the client
    Then the client should no longer exist in the system
Feature: Workload test
  Scenario: Call endpoint
    When called endpoint with "ADD" action
    Then returned status code is 200
  Scenario: Call endpoint delete
    When called endpoint with "DELETE" action
    Then returned status code is 200
  Scenario: Call endpoint invalid
    When endpoint called with invalid request
    Then returned status code is 500
  Scenario: Call without endpoint
    When endpoint without token
    Then returned status code is 403
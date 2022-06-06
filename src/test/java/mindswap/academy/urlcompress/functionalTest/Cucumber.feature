Feature: the version can be retrieved
  Background: Given the Application is available  at "http://localhost:8000"
  Scenario: client makes call to GET /version
    When the client calls version
    Then the client receives status code of 200
#    And the client receives server version "duarte"

  Scenario: List all available urls
    When I fetch urls at "/api/v1/url"
    Then I should find all urls
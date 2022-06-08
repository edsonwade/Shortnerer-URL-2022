Feature: the version can be retrieved

  Scenario: calling /api/v1/hello returns 200 with hello world
    Given I have a GET /api/v1/hello request
    When I perform the request
    Then I receive 200 status code
    And I receive a Hello world message

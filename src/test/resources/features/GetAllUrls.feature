Feature: the version can be retrieved

Scenario:  calling /api/v1/url returns 200
  Given I have a GET /api/v1/url request
  And I wait 5 seconds
  When I perform the request
  Then I receive 200 status code
#  Then I should receive <longUrl>
#
#  Examples:
#    |longUrl|
#    |https://www.facebook.com,https://www.google.com,https://www.instagram.com|




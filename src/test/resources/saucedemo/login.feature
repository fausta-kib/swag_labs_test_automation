Feature: Verify SwagLabs Log in Page

  Background:
    Given menu "is not" displayed

  Scenario Outline: Successful Login to SwagLabs with correct credentials
    Given a user is on the login page
    When a user inputs "<username>" username value
      And a user inputs "secret_sauce" password value
      And a user clicks on the Login button
    Then a user is redirected to the "inventory" page
    Examples:
      | username                |
      | standard_user           |
      | problem_user            |
      | performance_glitch_user |

  Scenario: Unsuccessful login attempt with locked out user credentials
    Given a user is on the login page
    When a user inputs "locked_out_user" username value
      And a user inputs "secret_sauce" password value
      And a user clicks on the Login button
    Then an error message "user has been locked out" should be displayed to a user
      And user should remain on the login page

  Scenario: Unsuccessful login attempt with non-existing user credentials
    Given a user is on the login page
    When a user inputs "non_existing_username" username value
      And a user inputs "non_existing_password" password value
      And a user clicks on the Login button
    Then an error message "Username and password do not match any user in this service" should be displayed to a user
      And user should remain on the login page

  Scenario Outline: Access to other application pages requires login
    Given a user is on the login page
    When the user attempts to access the "<page>" page via URL without logging in
    Then an error message "<error>" should appear to a user
      And user should remain on the login page

    Examples:
      | page              | error                                                                 |
      | inventory         | You can only access '/inventory.html' when you are logged in          |
      | cart              | You can only access '/cart.html' when you are logged in               |
      | checkout-step-one | You can only access '/checkout-step-one.html' when you are logged in  |
      | checkout-step-two | You can only access '/checkout-step-two.html' when you are logged in  |
      | checkout-complete | You can only access '/checkout-complete.html' when you are logged in  |

  Scenario: Empty credentials are not allowed
    Given a user is on the login page
    When a user leaves "username" field empty
      And a user leaves "password" field empty
      And a user clicks on the Login button
    Then an error message "Username is required" should be displayed to a user
      And user should remain on the login page

  Scenario: Empty username value isn't allowed
    Given a user is on the login page
    When a user leaves "username" field empty
      And a user inputs "secret_sauce" password value
      And a user clicks on the Login button
    Then an error message "Username is required" should be displayed to a user
      And user should remain on the login page

  Scenario: Empty password value isn't allowed
    Given a user is on the login page
    When a user inputs "standard_user" username value
      And a user leaves "password" field empty
      And a user clicks on the Login button
    Then an error message "Password is required" should be displayed to a user
      And user should remain on the login page
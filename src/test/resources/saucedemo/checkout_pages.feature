Feature: Verify SwagLabs Checkout Pages

  Background:
    Given a user successfully logs into the application with "standard_user" username and "secret_sauce" password
      And a user adds the "first" product to the cart
      And a user clicks on the shopping card icon
      And a user is redirected to the "shopping cart" page
      And a user clicks on the "Checkout" button

    Scenario: Empty "Your Information" form
      Given a user is redirected to the "checkout your information" page
      When a user clicks the "Continue" button
      Then an error message "First Name is required" will appear for a user

    Scenario: First name only in "Your Information" form
      Given a user is redirected to the "checkout your information" page
      When a user inputs "Pokis" first name value
        And a user clicks the "Continue" button
      Then an error message "Last Name is required" will appear for a user

    Scenario: First name and last name in "Your Information" form
      Given a user is redirected to the "checkout your information" page
      When a user inputs "Pokis" first name value
        And a user inputs "Pokovicius" last name value
        And a user clicks the "Continue" button
      Then an error message "Postal Code is required" will appear for a user

    Scenario: Cancel "Your Information" form
      Given a user is redirected to the "checkout your information" page
      When a user clicks the "Cancel" button
      Then a user is redirected to the "shopping cart" page

    Scenario: Canceling "Overview" page after successfully entering "Your Information" form
      Given a user is redirected to the "checkout your information" page
        And a user inputs "Pokis" first name value
        And a user inputs "Pokovicius" last name value
        And a user inputs "12345" zip code value
      When a user clicks the "Continue" button
      Then a user is redirected to the "checkout overview" page
      When a user clicks the "Cancel" button
      Given a user is redirected to the "inventory" page

    Scenario: Successful "Your Information" form input and order completion
      Given a user is redirected to the "checkout your information" page
        And a user inputs "Pokis" first name value
        And a user inputs "Pokovicius" last name value
        And a user inputs "12345" zip code value
      When a user clicks the "Continue" button
      Then a user is redirected to the "checkout overview" page
      When a user clicks the "Finish" button
      Then a user is redirected to the "checkout complete" page
      When a user clicks the "Back Home" button
      Then a user is redirected to the "inventory" page

    Scenario Outline: Verify menu functionality
      Given menu "is" displayed
        And a user clicks on the menu icon
      When a user clicks on the "<menu_selection>" option
      Then a user is redirected to the "<redirected_page>" page
      Examples:
        | menu_selection | redirected_page |
        | All items      | inventory       |
        | About          | about           |
        | Logout         | login           |

    Scenario: Verified "Reset App State" menu selection functionality
      Given menu "is" displayed
        And a shopping cart icon displays the number 1
        And a user clicks on the menu icon
      When a user clicks on the "Reset App State" option
      Then a user's shopping cart is empty
Feature: Verify SwagLabs Checkout Pages

  Background:
    Given a user successfully logs into the application with "standard_user" username and "secret_sauce" password
      And a user adds the "first" product to the cart
      And a user clicks on the shopping card icon
      And a user is redirected to the shopping card page
      And a user clicks on the "Checkout" button

    Scenario: "Your information" form is empty
      Given a user is redirected to the checkout one page
      When a user clicks the "Continue" button
      Then an error message "First Name is required" will appear for a user

    Scenario: User inputs only first name in the "Your information" form
      Given a user is redirected to the checkout one page
      When a user inputs "Pokis" first name value
        And a user clicks the "Continue" button
      Then an error message "Last Name is required" will appear for a user

    Scenario: User inputs only first name and last name in the "Your information" form
      Given a user is redirected to the checkout one page
      When a user inputs "Pokis" first name value
        And a user inputs "Pokovicius" last name value
        And a user clicks the "Continue" button
      Then an error message "Postal Code is required" will appear for a user

    Scenario: Your information form will be canceled
      Given a user is redirected to the checkout one page
      When a user clicks the "Cancel" button
      Then a user is redirected to the shopping card page

    Scenario: Your information form will be inputted successfully, but overview page will be canceled
      Given a user is redirected to the checkout one page
        And a user inputs "Pokis" first name value
        And a user inputs "Pokovicius" last name value
        And a user inputs "12345" zip code value
      When a user clicks the "Continue" button
      Then a user is redirected to the checkout overview page
      When a user clicks the "Cancel" button
      Given a user is redirected to the inventory page

    Scenario: Your information form will be inputted successfully
      Given a user is redirected to the checkout one page
        And a user inputs "Pokis" first name value
        And a user inputs "Pokovicius" last name value
        And a user inputs "12345" zip code value
      When a user clicks the "Continue" button
      Then a user is redirected to the checkout overview page
      When a user clicks the "Finnish" button
      Then a user is redirected to the checkout complete page
      When a user clicks the "Back Home" button
      Then a user is redirected to the inventory page
Feature: Verify SwagLabs Shopping Cart Page

  Background:
    Given a user successfully logs into the application with "standard_user" username and "secret_sauce" password
      And a user is redirected to the inventory page
      And a user's shopping cart is empty

  Scenario: Verify shopping cart is empty
    Given a user clicks on the shopping card icon
      And a user is redirected to the shopping card page
      And there are 0 products displayed in the cart
    When a user clicks on the "Checkout" button
    Then a user will be redirected to the checkout page

  Scenario: Verify continue shopping button redirect to the inventory page
    Given a user clicks on the shopping card icon
      And a user is redirected to the shopping card page
    When a user clicks on the "Continue Shopping" button
    Then a user is redirected to the inventory page

    Scenario: Verify product removal from the shopping cart
      Given a user adds the "first" product to the cart
        And a user adds the "second" product to the cart
        And a user adds the "third" product to the cart
        And a user clicks on the shopping card icon
        And a user is redirected to the shopping card page
        And a shopping cart icon displays the number 3
        And there are 3 products displayed in the cart
      When a user removes the second product from the cart
      Then there are 2 products displayed in the cart
        And a shopping cart icon displays the number 2

    Scenario: Verify checkout page redirection
    Given a user adds the "first" product to the cart
      And a user adds the "second" product to the cart
      And a user clicks on the shopping card icon
      And a user is redirected to the shopping card page
    When a user clicks on the "Checkout" button
    Then a user will be redirected to the checkout page

    Scenario: Verify product page redirection
      Given a user adds product by name "Sauce Labs Backpack"
        And a user clicks on the shopping card icon
        And a user is redirected to the shopping card page
      When a user clicks on the "Sauce Labs Backpack" product title
      Then a user will be redirected to the "Sauce Labs Backpack" product page
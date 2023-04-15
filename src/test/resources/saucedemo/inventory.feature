Feature: Test SwagLabs inventory page functionality

  Background:
    Given a user successfully logs into the application with "standard_user" username and "secret_sauce" password
      And a user is redirected to the inventory page

  Scenario: Sort products by Name (A to Z)
    Given the default product sort option is "Name (A to Z)"
      And products are currently sorted by "Name A to Z"
    When a user selects "Name (Z to A)" from the product sort options
    Then products will be sorted by "Name (Z to A)"

  Scenario: Sort products by Price (low to high)
    Given the default product sort option is "Name (A to Z)"
      And products are currently sorted by "Name A to Z"
    When a user selects "Price (low to high)" from the product sort options
    Then a user products will be sorted by "Price (low to high)"

  Scenario: Sort products by Price (high to low)
    Given the default product sort option is "Name (A to Z)"
      And products are currently sorted by "Name A to Z"
    When a user selects "Price (high to low)" from the product sort options
    Then a user products will be sorted by "Price (high to low)"

  Scenario: Adding items to the shopping cart
    Given a user's shopping cart is empty
    When a user adds the "first" product to the cart
    Then a shopping cart icon displays the number 1
    When a user adds the "second" product to the cart
    Then a shopping cart icon displays the number 2

  Scenario: Removing items to the shopping cart
    Given a user's shopping cart is empty
    When a user adds the "first" product to the cart
      And a user adds the "second" product to the cart
    Then a shopping cart icon displays the number 2
    When a user removes the first product from the cart
    Then a shopping cart icon displays the number 1

  Scenario: Verify product page redirection and back to the inventory page redirection
    Given a user adds product by name "Sauce Labs Backpack"
    When a user clicks on the "Sauce Labs Backpack" product title
    Then a user will be redirected to the "Sauce Labs Backpack" product page
    When a user clicks on the Back to products button
    Then a user is redirected to the inventory page

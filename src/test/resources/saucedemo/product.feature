Feature: Verify SwagLabs Product Page

  Background:
    Given a user successfully logs into the application with "standard_user" username and "secret_sauce" password
      And a user is redirected to the "inventory" page

  Scenario: Verify product page redirection and back to the inventory page redirection
    Given a user adds product by name "Sauce Labs Backpack"
    When a user clicks on the "Sauce Labs Backpack" product title
    Then a user will be redirected to the "Sauce Labs Backpack" product page
    When a user clicks on the "Back to products" button
    Then a user is redirected to the "inventory" page

  Scenario: Verified product remove functionality
    Given a user adds product by name "Sauce Labs Backpack"
    When a user clicks on the "Sauce Labs Backpack" product title
    Then a user will be redirected to the "Sauce Labs Backpack" product page
      And a shopping cart icon displays the number 1
    When a user clicks on the "Remove" button
    Then a user's shopping cart is empty

  Scenario: Verified add to cart functionality
    Given a user's shopping cart is empty
    When a user clicks on the "Sauce Labs Backpack" product title
    Then a user will be redirected to the "Sauce Labs Backpack" product page
    When a user clicks on the "Add to cart" button
    Then a shopping cart icon displays the number 1

  Scenario: Verified shopping cart redirection
    Given a user adds product by name "Sauce Labs Backpack"
    When a user clicks on the "Sauce Labs Backpack" product title
    Then a user will be redirected to the "Sauce Labs Backpack" product page
    And a shopping cart icon displays the number 1
    When a user clicks on the "Shopping card" button
    Then a user is redirected to the "shopping cart" page
    And there are 1 products displayed in the cart

  Scenario Outline: Verify menu functionality
    Given the menu "is" displayed
      And a user clicks on the "menu" button
    When a user clicks on the "<menu_selection>" option
    Then a user is redirected to the "<redirected_page>" page
    Examples:
      | menu_selection | redirected_page |
      | All items      | inventory       |
      | About          | about           |
      | Logout         | login           |

  Scenario: Verified "Reset App State" menu selection functionality
    Given the menu "is" displayed
      And a user adds product by name "Sauce Labs Backpack"
      And a shopping cart icon displays the number 1
      And a user clicks on the "menu" button
    When a user clicks on the "Reset App State" option
    Then a user's shopping cart is empty
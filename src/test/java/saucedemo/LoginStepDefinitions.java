package saucedemo;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static saucedemo.WebDriverProvider.getDriver;

public class LoginStepDefinitions {

    @Given("a user is on the login page")
    public void aUserIsInLogInPage() {
        getDriver().get(PageConstants.LOG_IN_PAGE);
        assertTrue(getDriver().getTitle().contains("Swag Labs"));
        List<WebElement> elements = getDriver().findElements(By.xpath(LoginConstants.LOGIN_LOGO));
        Assertions.assertFalse(elements.isEmpty());
    }

    @When("a user inputs {string} username value")
    public void aUserInputsUsernameUsernameValue(String UserNameValue) {
        WebElement username = getDriver().findElement(By.xpath(LoginConstants.INPUT_ID_USER_NAME));
        username.sendKeys(UserNameValue);
    }

    @And("a user inputs {string} password value")
    public void aUserInputsPasswordValue(String PasswordValue) {
        WebElement password = getDriver().findElement(By.xpath(LoginConstants.INPUT_TYPE_PASSWORD));
        password.sendKeys(PasswordValue);
    }

    @And("a user clicks on the Login button")
    public void aUserClicksOnTheLoginButton() {
        WebElement login = getDriver().findElement(By.xpath(LoginConstants.LOGIN_BUTTON));
        login.click();
    }

    @When("a user leaves \"username\" field empty")
    public void aUserLeftsUsernameValueEmpty() {
        WebElement username = getDriver().findElement(By.xpath(LoginConstants.INPUT_ID_USER_NAME));
        assertTrue(username.getText().isEmpty());
    }

    @And("a user leaves \"password\" field empty")
    public void aUserLeftsPasswordValueEmpty() {
        WebElement username = getDriver().findElement(By.xpath(LoginConstants.INPUT_TYPE_PASSWORD));
        assertTrue(username.getText().isEmpty());
    }

    @When("the user attempts to access the {string} page via URL without logging in")
    public void aUserGoesToTheInventoryPageByUrl(String urlUsedWent) {
        switch (urlUsedWent) {
            case "inventory":
                getDriver().get(PageConstants.INVENTORY_PAGE);
                break;
            case "cart":
                getDriver().get(PageConstants.CART_PAGE);
                break;
            case "checkout-step-one":
                getDriver().get(PageConstants.CHECKOUT_STEP_ONE_PAGE);
                break;
            case "checkout-step-two":
                getDriver().get(PageConstants.CHECKOUT_STEP_TWO_PAGE);
                break;
            default:
                getDriver().get(PageConstants.CHECKOUT_COMPLETE_PAGE);
                break;
        }
        assertTrue(getDriver().getTitle().contains("Swag Labs"));
    }

    @Then("an error message {string} should be displayed to a user")
    public void anErrorWillBeDisplayedForAUser(String errorValue) {

        WebElement error = getDriver().findElement(By.xpath(LoginConstants.ERROR_MESSAGE_CONTAINER));
        String getErrorText = error.getText();
        String getErrorMessage;

        switch (errorValue) {
            case "user has been locked out":
                getErrorMessage = "Epic sadface: Sorry, this user has been locked out.";
                break;
            case "Username and password do not match any user in this service":
                getErrorMessage = "Epic sadface: Username and password do not match any user in this service";
                break;
            case "Username is required":
                getErrorMessage = "Epic sadface: Username is required";
                break;
            case "You can only access '/inventory.html' when you are logged in":
                getErrorMessage = "Epic sadface: You can only access '/inventory.html' when you are logged in.";
                break;
            default:
                getErrorMessage = "Epic sadface: Password is required";
                break;
        }

        boolean equalTexts = getErrorText.equals(getErrorMessage);
        if (!equalTexts) {
            throw new RuntimeException("Expected error text is not as the actual error text");
        }
    }

    @Then("an error message {string} should appear to a user")
    public void anUrlErrorWillBeDisplayedForAUser(String errorUrlValue) {

        WebElement error = getDriver().findElement(By.xpath(LoginConstants.ERROR_MESSAGE_CONTAINER));
        String getErrorText = error.getText();
        String getUrlErrorMessage = null;

        switch (errorUrlValue) {
            case "You can only access '/inventory.html' when you are logged in":
                getUrlErrorMessage = "Epic sadface: You can only access '/inventory.html' when you are logged in.";
                break;
            case "You can only access '/cart.html' when you are logged in":
                getUrlErrorMessage = "Epic sadface: You can only access '/cart.html' when you are logged in.";
                break;
            case "You can only access '/checkout-step-one.html' when you are logged in":
                getUrlErrorMessage = "Epic sadface: You can only access '/checkout-step-one.html' when you are logged in.";
                break;
            case "You can only access '/checkout-step-two.html' when you are logged in":
                getUrlErrorMessage = "Epic sadface: You can only access '/checkout-step-two.html' when you are logged in.";
                break;
            case "You can only access '/checkout-complete.html' when you are logged in":
                getUrlErrorMessage = "Epic sadface: You can only access '/checkout-complete.html' when you are logged in.";
                break;
        }

        boolean equalTexts = getErrorText.equals(getUrlErrorMessage);
        if (!equalTexts) {
            throw new RuntimeException("Expected error text is not as the actual error text");
        }
    }

    @And("user should remain on the login page")
    public void aUserWillStayOnTheLogInPage() {
        assertTrue(getDriver().getCurrentUrl().contains(PageConstants.LOG_IN_PAGE));
        assertTrue(getDriver().getTitle().contains("Swag Labs"));
        List<WebElement> elements = getDriver().findElements(By.xpath(LoginConstants.LOGIN_LOGO));
        Assertions.assertFalse(elements.isEmpty());
    }
}
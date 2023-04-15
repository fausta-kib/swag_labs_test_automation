package saucedemo;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static saucedemo.WebDriverProvider.getDriver;

public class CheckoutPagesStepDefinitions {

    @Given("a user is redirected to the checkout your information page")
    public void userIsRedirectedToCheckoutYourInformationPage() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofMillis(5));
        wait.until(ExpectedConditions.urlToBe(PageConstants.CHECKOUT_STEP_ONE_PAGE));
        Assertions.assertFalse(getDriver().findElements(By.xpath(InventoryConstants.APP_LOGO)).isEmpty(), "Logo not found");
    }

    @Given("a user is redirected to the checkout complete page")
    public void userIsRedirectedToCheckOutCompletePage() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofMillis(5));
        wait.until(ExpectedConditions.urlToBe(PageConstants.CHECKOUT_COMPLETE_PAGE));
        Assertions.assertFalse(getDriver().findElements(By.xpath(InventoryConstants.APP_LOGO)).isEmpty(), "Logo not found");
    }

    @And("a user inputs {string} zip code value")
    public void userInputsZipCodeValue(String zipCodeValue) {
        WebElement zipCode = getDriver().findElement(By.xpath(CheckoutConstants.ZIP_CODE));
        zipCode.sendKeys(zipCodeValue);
    }

    @When("a user clicks the {string} button")
    public void userClicksButton(String buttonType) {
        WebElement button = null;
        switch (buttonType) {
            case "Continue":
                button = getDriver().findElement(By.xpath(CheckoutConstants.CONTINUE_BUTTON));
                break;
            case "Cancel":
                button = getDriver().findElement(By.xpath(CheckoutConstants.CANCEL_BUTTON));
                break;
            case "Finish":
                button = getDriver().findElement(By.xpath(CheckoutConstants.FINISH_BUTTON));
                break;
            case "Back Home":
                button = getDriver().findElement(By.xpath(CheckoutConstants.BACK_BUTTON));
                break;
            default:
                Assertions.fail("Button '" + buttonType + "' wasn't found on the cart page");
        }
        button.click();
    }

    @When("a user inputs {string} first name value")
    public void userInputsFirstNameValue(String firstNameValue) {
        WebElement firstName = getDriver().findElement(By.xpath(CheckoutConstants.NAME));
        firstName.sendKeys(firstNameValue);
    }

    @When("a user inputs {string} last name value")
    public void userInputsLastNameValue(String lastNameValue) {
        WebElement lastName = getDriver().findElement(By.xpath(CheckoutConstants.LASTNAME));
        lastName.sendKeys(lastNameValue);
    }

    @Then("an error message {string} will appear for a user")
    public void errorMessageWillAppear(String errorType) {

        WebElement error = getDriver().findElement(By.xpath(CheckoutConstants.ERROR_MESSAGE));
        String getErrorText = error.getText();
        String getErrorMessage;

        switch (errorType) {
            case "Last Name is required":
                getErrorMessage = "Error: Last Name is required";
                break;
            case "Postal Code is required":
                getErrorMessage = "Error: Postal Code is required";
                break;
            default:
                getErrorMessage = "Error: First Name is required";
                break;
        }
        boolean equalTexts = getErrorText.equals(getErrorMessage);
        if (!equalTexts) {
            throw new RuntimeException("Expected error message is not the same as actual error message");
        }
    }

    @Then("a user is redirected to the checkout overview page")
    public void userIsRedirectedToOverviewPage() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofMillis(5));
        wait.until(ExpectedConditions.urlToBe(PageConstants.CHECKOUT_STEP_TWO_PAGE));
        Assertions.assertFalse(getDriver().findElements(By.xpath(InventoryConstants.APP_LOGO)).isEmpty(), "Logo not found");
    }
}

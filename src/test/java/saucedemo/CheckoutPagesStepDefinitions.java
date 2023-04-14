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

    @Given("a user is redirected to the checkout one page")
    public void aUserIsRedirectedToTheCheckoutOnePage() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofMillis(5));
        wait.until(ExpectedConditions.urlToBe(PageConstants.CHECKOUT_STEP_ONE_PAGE));
        Assertions.assertFalse(getDriver().findElements(By.xpath(InventoryConstants.APP_LOGO)).isEmpty(), "Logo not found");
    }

    @When("a user clicks the {string} button")
    public void aUserClicksTheButton(String buttonType) {
        if (buttonType.equals("Continue")) {
            WebElement continueShoppingButton = getDriver().findElement(By.xpath("//input[@id='continue']"));
            continueShoppingButton.click();
        } else if (buttonType.equals("Cancel")) {
            WebElement CheckoutButton = getDriver().findElement(By.xpath("//button[@id='cancel']"));
            CheckoutButton.click();
        } else if (buttonType.equals("Finnish")) {
            WebElement CheckoutButton = getDriver().findElement(By.xpath("//button[@id='finish']"));
            CheckoutButton.click();
        } else if (buttonType.equals("Back Home")) {
            WebElement CheckoutButton = getDriver().findElement(By.xpath("//button[@id='back-to-products']"));
            CheckoutButton.click();
        } else {
            Assertions.fail("Button '" + buttonType + "' wasn't found on the cart page");
        }
    }

    @Then("an error message {string} will appear for a user")
    public void anErrorMessageWillAppearForAUser(String errorType) {

        WebElement error = getDriver().findElement(By.xpath("//div[contains(@class, 'error-message-container error')]/h3"));
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
            throw new RuntimeException("Expected error text is not as the actual error text");
        }
    }

    @When("a user inputs {string} first name value")
    public void aUserInputsFirstNameValue(String firstNameValue) {
        WebElement firstName = getDriver().findElement(By.xpath("//input[@id='first-name']"));
        firstName.sendKeys(firstNameValue);
    }

    @When("a user inputs {string} last name value")
    public void aUserInputsLastNameValue(String lastNameValue) {
        WebElement lastName = getDriver().findElement(By.xpath("//input[@id='last-name']"));
        lastName.sendKeys(lastNameValue);
    }

    @And("a user inputs {string} zip code value")
    public void aUserInputsZipCodeValue(String zipCodeValue) {
        WebElement zipCode = getDriver().findElement(By.xpath("//input[@id='postal-code']"));
        zipCode.sendKeys(zipCodeValue);
    }

    @Then("a user is redirected to the checkout overview page")
    public void aUserIsRedirectedToTheOverviewPage() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofMillis(5));
        wait.until(ExpectedConditions.urlToBe(PageConstants.CHECKOUT_STEP_TWO_PAGE));
        Assertions.assertFalse(getDriver().findElements(By.xpath(InventoryConstants.APP_LOGO)).isEmpty(), "Logo not found");
    }

    @Given("a user is redirected to the checkout complete page")
    public void aUserIsRedirectedToTheCheckoutCompletePage() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofMillis(5));
        wait.until(ExpectedConditions.urlToBe(PageConstants.CHECKOUT_COMPLETE_PAGE));
        Assertions.assertFalse(getDriver().findElements(By.xpath(InventoryConstants.APP_LOGO)).isEmpty(), "Logo not found");

    }
}

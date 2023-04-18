package saucedemo;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static saucedemo.WebDriverProvider.getDriver;

public class CheckoutPagesStepDefinitions {

    @And("a user inputs {string} zip code value")
    public void userInputsZipCodeValue(String zipCodeValue) {
        getDriver().findElement(By.xpath(CheckoutConstants.ZIP_CODE)).sendKeys(zipCodeValue);
    }

    @When("a user inputs {string} first name value")
    public void userInputsFirstNameValue(String firstNameValue) {
        getDriver().findElement(By.xpath(CheckoutConstants.NAME)).sendKeys(firstNameValue);
    }

    @When("a user inputs {string} last name value")
    public void userInputsLastNameValue(String lastNameValue) {
        getDriver().findElement(By.xpath(CheckoutConstants.LASTNAME)).sendKeys(lastNameValue);
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
}
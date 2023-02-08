package saucedemo;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginStepDefinitions {

    private WebDriver driver;

    @Before
    public void setUpDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-fullscreen");
        driver = new ChromeDriver(options);
    }

    @Given("a user is on the login page")
    public void aUserIsInLogInPage() {
        driver.get(PageConstants.LOG_IN_PAGE);
        assertTrue(driver.getTitle().contains("Swag Labs"));
        List<WebElement> elements = driver.findElements(By.xpath(LoginConstants.LOGIN_LOGO));
        Assertions.assertFalse(elements.isEmpty());
    }

    @When("a user inputs {string} username value")
    public void aUserInputsUsernameUsernameValue(String UserNameValue) {
        WebElement username = driver.findElement(By.xpath(LoginConstants.INPUT_ID_USER_NAME));
        username.sendKeys(UserNameValue);
    }

    @And("a user inputs {string} password value")
    public void aUserInputsPasswordValue(String PasswordValue) {
        WebElement password = driver.findElement(By.xpath(LoginConstants.INPUT_TYPE_PASSWORD));
        password.sendKeys(PasswordValue);
    }

    @And("a user clicks on the Login button")
    public void aUserClicksOnTheLoginButton() {
        WebElement login = driver.findElement(By.xpath(LoginConstants.LOGIN_BUTTON));
        login.click();
    }

    @When("a user leaves \"username\" field empty")
    public void aUserLeftsUsernameValueEmpty() {
        WebElement username = driver.findElement(By.xpath(LoginConstants.INPUT_ID_USER_NAME));
        Assertions.assertTrue(username.getText().isEmpty());
    }

    @And("a user leaves \"password\" field empty")
    public void aUserLeftsPasswordValueEmpty() {
        WebElement username = driver.findElement(By.xpath(LoginConstants.INPUT_TYPE_PASSWORD));
        Assertions.assertTrue(username.getText().isEmpty());
    }

    @When("the user attempts to access the {string} page via URL without logging in")
    public void aUserGoesToTheInventoryPageByUrl(String urlUsedWent) {
        switch (urlUsedWent) {
            case "inventory":
                driver.get(PageConstants.INVENTORY_PAGE);
                break;
            case "cart":
                driver.get(PageConstants.CART_PAGE);
                break;
            case "checkout-step-one":
                driver.get(PageConstants.CHECKOUT_STEP_ONE_PAGE);
                break;
            case "checkout-step-two":
                driver.get(PageConstants.CHECKOUT_STEP_TWO_PAGE);
                break;
            default:
                driver.get(PageConstants.CHECKOUT_COMPLETE_PAGE);
                break;
        }
        assertTrue(driver.getTitle().contains("Swag Labs"));
    }

    @Then("a user should be redirected to the inventory page")
    public void aUserWillBeRedirectedToTheXPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(5));
        wait.until(ExpectedConditions.urlToBe(PageConstants.INVENTORY_PAGE));

        List<WebElement> elements = driver.findElements(By.xpath(LoginConstants.APP_LOGO));
        Assertions.assertFalse(elements.isEmpty());
    }

    @Then("an error message {string} should be displayed to a user")
    public void anErrorWillBeDisplayedForAUser(String errorValue) {

        WebElement error = driver.findElement(By.xpath(LoginConstants.ERROR_MESSAGE_CONTAINER));
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

        WebElement error = driver.findElement(By.xpath(LoginConstants.ERROR_MESSAGE_CONTAINER));
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
        assertTrue(driver.getCurrentUrl().contains(PageConstants.LOG_IN_PAGE));
        assertTrue(driver.getTitle().contains("Swag Labs"));
        List<WebElement> elements = driver.findElements(By.xpath(LoginConstants.LOGIN_LOGO));
        Assertions.assertFalse(elements.isEmpty());
    }

    @After()
    public void closeBrowser() {
        driver.quit();
    }

    @After
    public void includeScreenshot(Scenario scenario) throws IOException {
        if (scenario.isFailed()) {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            byte[] fileContent = FileUtils.readFileToByteArray(screenshot);
            scenario.attach(fileContent, "image/png", "image1");
        }
    }
}


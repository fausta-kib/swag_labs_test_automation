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
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class InventoryStepDefinitions {
    private WebDriver driver;

    @Before
    public void setUpDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-fullscreen");
        driver = new ChromeDriver(options);
    }

    @Given("a user successfully logs into the application with {string} username and {string} password")
    public void aUserSuccessfullyIsLoggedToApplicationWithUsernameAndPassword(String UserNameValue, String PasswordValue) {
        driver.get(PageConstants.LOG_IN_PAGE);
        WebElement username = driver.findElement(By.xpath(LoginConstants.INPUT_ID_USER_NAME));
        username.sendKeys(UserNameValue);
        WebElement password = driver.findElement(By.xpath(LoginConstants.INPUT_TYPE_PASSWORD));
        password.sendKeys(PasswordValue);
        WebElement login = driver.findElement(By.xpath(LoginConstants.LOGIN_BUTTON));
        login.click();
    }

    @And("a user is redirected to the inventory page")
    public void aUserWillBeRedirectedToTheXPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(5));
        wait.until(ExpectedConditions.urlToBe(PageConstants.INVENTORY_PAGE));
        List<WebElement> elements = driver.findElements(By.xpath(InventoryConstants.APP_LOGO));
        Assertions.assertFalse(elements.isEmpty());
    }

    @And("a user's shopping cart is empty")
    public void andShoppingCardIsEmpty() {
        List<WebElement> card = driver.findElements(By.xpath(InventoryConstants.SHOPPING_CART));
        Assertions.assertTrue(card.isEmpty());
    }

    @Given("the default product sort option is {string}")
    public void sortedByValueIs(String sortedBySelection) {
        WebElement sorting = driver.findElement(By.xpath(InventoryConstants.ACTIVE_SORT_VALUE));
        String getSortText = sorting.getText();
        boolean equalTexts = getSortText.equals(sortedBySelection);
        if (!equalTexts) {
            throw new RuntimeException("Expected error text is not as the actual error text");
        }
    }

    @And("products are currently sorted by \"Name A to Z\"")
    public void productsByDefaultWillBeSortedBy() {
        List<WebElement> actualWebElements = driver.findElements(By.xpath(InventoryConstants.INVENTORY_NAME));
        List<String> actualNames = new ArrayList<>();
        for (WebElement webElement : actualWebElements) {
            String name = webElement.getText();
            actualNames.add(name);
        }
        List<String> expectedSortedList = new ArrayList<>(actualNames);
        Collections.sort(expectedSortedList);
        boolean result = expectedSortedList.equals(actualNames);
        if (!result) {
            throw new RuntimeException("List is not sorted");
        }
    }

    @Then("products will be sorted by {string}")
    public void productsWillBeSortedBy(String productsSortedByValue) {
        List<WebElement> actualWebElements = driver.findElements(By.xpath(InventoryConstants.INVENTORY_NAME));
        List<String> actualNames = new ArrayList<>();
        for (WebElement webElement : actualWebElements) {
            String name = webElement.getText();
            actualNames.add(name);
        }
        List<String> expectedSortedList = new ArrayList<>(actualNames);

        if (productsSortedByValue.equals("Name (Z to A)")) {
            Comparator<String> reverseComparator = Comparator.reverseOrder();
            expectedSortedList.sort(reverseComparator);
        } else {
            Collections.sort(expectedSortedList);
        }

        boolean result = expectedSortedList.equals(actualNames);
        if (!result) {
            throw new RuntimeException("List is not sorted");
        }
    }

    @When("a user selects {string} from the product sort options")
    public void aUserChangesSortedValueTo(String sortedByValueChanged) {
        WebElement productsSortedBy = driver.findElement(By.xpath(InventoryConstants.PRODUCTS_ARE_SORTED_BY));
        productsSortedBy.click();

        switch (sortedByValueChanged) {
            case "Price (low to high)": {
                WebElement changeSortingValue = driver.findElement(By.xpath(InventoryConstants.LOW_TO_HIGH));
                changeSortingValue.click();
                break;
            }
            case "Price (high to low)": {
                WebElement changeSortingValue = driver.findElement(By.xpath(InventoryConstants.HIGH_TO_LOW));
                changeSortingValue.click();
                break;
            }
            default: {
                WebElement changeSortingValue = driver.findElement(By.xpath(InventoryConstants.NAME_Z_TO_A));
                changeSortingValue.click();
                break;
            }
        }
        WebElement sorting = driver.findElement(By.xpath(InventoryConstants.ACTIVE_SORT_VALUE));
        String getSortText = sorting.getText();
        boolean equalTexts = getSortText.equals(sortedByValueChanged);
        if (!equalTexts) {
            throw new RuntimeException("Expected error text is not as the actual error text");
        }
    }

    @Then("a user products will be sorted by {string}")
    public void aUserProductsWillBeSortedBy(String productsSortedByPrice) {
        List<WebElement> actualWebElements = driver.findElements(By.xpath(InventoryConstants.INVENTORY_PRICE));
        List<BigDecimal> actualPrices = new ArrayList<>();
        for (WebElement webElement : actualWebElements) {
            String priceDisplay = webElement.getText();
            String priceNumber = priceDisplay.substring(1);
            BigDecimal price = new BigDecimal(priceNumber);
            actualPrices.add(price);
        }
        List<BigDecimal> expectedSortedList = new ArrayList<>(actualPrices);
        if (productsSortedByPrice.equals("Price (low to high)")) {
            Collections.sort(expectedSortedList);
        } else if (productsSortedByPrice.equals("Price (high to low)")) {
            Collections.sort(expectedSortedList);
            Collections.reverse(expectedSortedList);
        } else {
            Collections.sort(expectedSortedList);
        }
        boolean result = expectedSortedList.equals(actualPrices);
        if (!result) {
            throw new RuntimeException("List is not sorted");
        }
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

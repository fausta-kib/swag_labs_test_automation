package saucedemo;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
    public void aUserSuccessfullyIsLoggedToApplicationWithUsernameAndPassword(String userNameValue, String passwordValue) {
        driver.get(PageConstants.LOG_IN_PAGE);
        driver.findElement(By.xpath(LoginConstants.INPUT_ID_USER_NAME)).sendKeys(userNameValue);
        driver.findElement(By.xpath(LoginConstants.INPUT_TYPE_PASSWORD)).sendKeys(passwordValue);
        driver.findElement(By.xpath(LoginConstants.LOGIN_BUTTON)).click();
    }

    @And("a user is redirected to the inventory page")
    public void aUserWillBeRedirectedToTheXPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(5));
        wait.until(ExpectedConditions.urlToBe(PageConstants.INVENTORY_PAGE));
        Assertions.assertFalse(driver.findElements(By.xpath(InventoryConstants.APP_LOGO)).isEmpty());
    }

    @And("a user's shopping cart is empty")
    public void andShoppingCardIsEmpty() {
        Assertions.assertTrue(driver.findElements(By.xpath(InventoryConstants.SHOPPING_CART)).isEmpty());
    }

    @And("the default product sort option is {string}")
    public void sortedByValueIs(String sortedBySelection) {
        Assertions.assertEquals(driver.findElement(By.xpath(InventoryConstants.ACTIVE_SORT_VALUE)).getText(), sortedBySelection);
    }

    @And("products are currently sorted by \"Name A to Z\"")
    public void productsByDefaultWillBeSortedBy() {
        List<WebElement> actualWebElements = driver.findElements(By.xpath(InventoryConstants.INVENTORY_NAME));
        List<String> actualNames = new ArrayList<>();
        for (WebElement webElement : actualWebElements) {
            actualNames.add(webElement.getText());
        }
        List<String> expectedSortedList = new ArrayList<>(actualNames);
        Collections.sort(expectedSortedList);
        Assertions.assertEquals(expectedSortedList, actualNames);
    }

    @When("a user selects {string} from the product sort options")
    public void userSelectsSortOption(String sortOption) {
        WebElement productsSortedBy = driver.findElement(By.xpath(InventoryConstants.PRODUCTS_ARE_SORTED_BY));
        productsSortedBy.click();

        switch (sortOption) {
            case "Price (low to high)":
                driver.findElement(By.xpath(InventoryConstants.LOW_TO_HIGH)).click();
                break;
            case "Price (high to low)":
                driver.findElement(By.xpath(InventoryConstants.HIGH_TO_LOW)).click();
                break;
            default:
                driver.findElement(By.xpath(InventoryConstants.NAME_Z_TO_A)).click();
                break;
        }

        WebElement sorting = driver.findElement(By.xpath(InventoryConstants.ACTIVE_SORT_VALUE));
        String actualSortText = sorting.getText();
        Assertions.assertEquals(sortOption, actualSortText, "Expected sort option text is not equal to the actual sort option text");
    }

    @And("a user adds the {string} product to the cart")
    public void userAddsProductToCart(String productNumber) {
        List<WebElement> addToCartButtons = driver.findElements(By.xpath(InventoryConstants.ADD_TO_CARD_BUTTON));

        if (productNumber.equals("first")) {
            addToCartButtons.get(0).click();
        } else if (productNumber.equals("second")) {
            addToCartButtons.get(1).click();
        }
    }

    @And("a user removes the first product from the cart")
    public void userRemovesFirstProductFromCart() {
        List<WebElement> removeButtons = driver.findElements(By.xpath(InventoryConstants.REMOVE_BUTTON));
        removeButtons.get(0).click();
    }

    @Then("products will be sorted by {string}")
    public void productsWillBeSortedByName(String expectedSortOption) {
        List<WebElement> inventoryNameElements = driver.findElements(By.xpath(InventoryConstants.INVENTORY_NAME));
        List<String> actualNames = new ArrayList<>();

        for (WebElement element : inventoryNameElements) {
            actualNames.add(element.getText());
        }

        List<String> expectedSortedList = new ArrayList<>(actualNames);

        if (expectedSortOption.equals("Name (Z to A)")) {
            expectedSortedList.sort(Comparator.reverseOrder());
        } else {
            Collections.sort(expectedSortedList);
        }

        Assertions.assertEquals(expectedSortedList, actualNames, "Inventory names are not sorted as expected");
    }

    @And("a user products will be sorted by {string}")
    public void userProductsWillBeSortedByPrice(String expectedSortOption) {
        List<WebElement> inventoryPriceElements = driver.findElements(By.xpath(InventoryConstants.INVENTORY_PRICE));
        List<BigDecimal> actualPrices = new ArrayList<>();

        for (WebElement element : inventoryPriceElements) {
            String priceDisplay = element.getText();
            String priceNumber = priceDisplay.substring(1);
            actualPrices.add(new BigDecimal(priceNumber));
        }

        List<BigDecimal> expectedSortedList = new ArrayList<>(actualPrices);

        if (expectedSortOption.equals("Price (low to high)")) {
            Collections.sort(expectedSortedList);
        } else if (expectedSortOption.equals("Price (high to low)")) {
            Collections.sort(expectedSortedList, Collections.reverseOrder());
        } else {
            Collections.sort(expectedSortedList);
        }

        Assertions.assertEquals(expectedSortedList, actualPrices, "Inventory prices are not sorted as expected");
    }

    @And("a shopping cart icon should display the number {int}")
    public void verifyCartNumberCount(int expectedCount) {
        WebElement cartIcon = driver.findElement(By.xpath(InventoryConstants.SHOPPING_CART));
        int actualCount = Integer.parseInt(cartIcon.getText().trim());

        Assertions.assertEquals(expectedCount, actualCount, "Cart number count is not as expected.");
    }

    @After
    public void tearDown(Scenario scenario) throws IOException {
        if (scenario.isFailed()) {
            // Take screenshot on failure
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "failure-screenshot");
        }

        // Close the browser
        driver.quit();
    }
}

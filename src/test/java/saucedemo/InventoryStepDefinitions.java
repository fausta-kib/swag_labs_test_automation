package saucedemo;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static saucedemo.WebDriverProvider.getDriver;

public class InventoryStepDefinitions {

    @Given("a user successfully logs into the application with {string} username and {string} password")
    public void aUserSuccessfullyIsLoggedToApplicationWithUsernameAndPassword(String userNameValue, String passwordValue) {
        getDriver().get(PageConstants.LOG_IN_PAGE);
        getDriver().findElement(By.xpath(LoginConstants.INPUT_ID_USER_NAME)).sendKeys(userNameValue);
        getDriver().findElement(By.xpath(LoginConstants.INPUT_TYPE_PASSWORD)).sendKeys(passwordValue);
        getDriver().findElement(By.xpath(LoginConstants.LOGIN_BUTTON)).click();
    }

    @And("a user's shopping cart is empty")
    public void andShoppingCardIsEmpty() {
        Assertions.assertTrue(getDriver().findElements(By.xpath(InventoryConstants.SHOPPING_CART)).isEmpty());
    }

    @And("the default product sort option is {string}")
    public void sortedByValueIs(String sortedBySelection) {
        Assertions.assertEquals(getDriver().findElement(By.xpath(InventoryConstants.ACTIVE_SORT_VALUE)).getText(), sortedBySelection);
    }

    @And("products are currently sorted by \"Name A to Z\"")
    public void productsByDefaultWillBeSortedBy() {
        List<WebElement> actualWebElements = getDriver().findElements(By.xpath(InventoryConstants.INVENTORY_NAME));
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
        WebElement productsSortedBy = getDriver().findElement(By.xpath(InventoryConstants.PRODUCTS_ARE_SORTED_BY));
        productsSortedBy.click();

        switch (sortOption) {
            case "Price (low to high)":
                getDriver().findElement(By.xpath(InventoryConstants.LOW_TO_HIGH)).click();
                break;
            case "Price (high to low)":
                getDriver().findElement(By.xpath(InventoryConstants.HIGH_TO_LOW)).click();
                break;
            default:
                getDriver().findElement(By.xpath(InventoryConstants.NAME_Z_TO_A)).click();
                break;
        }

        WebElement sorting = getDriver().findElement(By.xpath(InventoryConstants.ACTIVE_SORT_VALUE));
        String actualSortText = sorting.getText();
        Assertions.assertEquals(sortOption, actualSortText, "Expected sort option text is not equal to the actual sort option text");
    }

    @And("a user adds the {string} product to the cart")
    public void userAddsProductToCart(String productNumber) {
        List<WebElement> addToCartButtons = getDriver().findElements(By.xpath(InventoryConstants.ADD_TO_CARD_BUTTON));

        if (productNumber.equals("first")) {
            addToCartButtons.get(0).click();
        } else if (productNumber.equals("second")) {
            addToCartButtons.get(1).click();
        } else if (productNumber.equals("third")) {
            addToCartButtons.get(2).click();
        } else {
            Assertions.fail("Product wasn't added successfully to the cart");
        }
    }

    @And("a user removes the first product from the cart")
    public void userRemovesFirstProductFromCart() {
        List<WebElement> removeButtons = getDriver().findElements(By.xpath(InventoryConstants.REMOVE_BUTTON));
        removeButtons.get(0).click();
    }

    @When("a user clicks on the Back to products button")
    public void aUserClicksOnTheBackToProductsButton() {
        WebElement backToProductsButton = getDriver().findElement(By.xpath("//button[@id='back-to-products']"));
        backToProductsButton.click();
    }

    @Then("products will be sorted by {string}")
    public void productsWillBeSortedByName(String expectedSortOption) {
        List<WebElement> inventoryNameElements = getDriver().findElements(By.xpath(InventoryConstants.INVENTORY_NAME));
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
        List<WebElement> inventoryPriceElements = getDriver().findElements(By.xpath(InventoryConstants.INVENTORY_PRICE));
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

    @And("a shopping cart icon displays the number {int}")
    public void verifyCartNumberCount(int expectedCount) {
        WebElement cartIcon = getDriver().findElement(By.xpath(InventoryConstants.SHOPPING_CART));
        int actualCount = Integer.parseInt(cartIcon.getText().trim());
        Assertions.assertEquals(expectedCount, actualCount, "Cart number count is not as expected.");
    }
}
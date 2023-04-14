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
import java.util.List;

import static saucedemo.WebDriverProvider.getDriver;

public class CartStepDefinitions {

    @Given("a user clicks on the shopping card icon")
    public void clickShoppingCartIcon() {
        WebElement cartButton = getDriver().findElement(By.xpath(CartConstants.SHOPPING_CART));
        cartButton.click();
    }

    @And("a user is redirected to the shopping card page")
    public void verifyCartPageIsDisplayed() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofMillis(5));
        wait.until(ExpectedConditions.urlToBe(PageConstants.CART_PAGE));
        Assertions.assertFalse(getDriver().findElements(By.xpath(InventoryConstants.APP_LOGO)).isEmpty());
    }

    @And("there are {int} products displayed in the cart")
    public void verifyNumberOfProductsDisplayed(int numberOfItems) {
        List<WebElement> actualCardItemsCount = getDriver().findElements(By.xpath(CartConstants.CARD_ITEM));
        int countedItems = actualCardItemsCount.size();
        Assertions.assertEquals(numberOfItems, countedItems, "Expected amount of products didn't matched counted amount");
    }

    @Given("a user adds product by name {string}")
    public void addProductByName(String productName) {

        List<WebElement> products = getDriver().findElements(By.xpath(CartConstants.ITEM_NAME));
        WebElement removeButton = null;
        WebElement selectedProduct = null;

        for (WebElement webElement : products) {
            if (webElement.getText().equals(productName)) {
                selectedProduct = webElement;
                removeButton = getDriver().findElement(By.xpath(CartConstants.ITEM_REMOVE_BUTTON));
            }
        }
        if (selectedProduct != null) {
            removeButton.click();
        } else {
            Assertions.fail("Product '" + productName + "' wasn't found");
        }
    }

    @When("a user clicks on the {string} button")
    public void clickButton(String buttonType) {
        if (buttonType.equals("Continue Shopping")) {
            WebElement continueShoppingButton = getDriver().findElement(By.xpath(CartConstants.CONTINUE_BUTTON));
            continueShoppingButton.click();
        } else if (buttonType.equals("Checkout")) {
            WebElement CheckoutButton = getDriver().findElement(By.xpath(CartConstants.CHECKOUT_BUTTON));
            CheckoutButton.click();
        } else {
            Assertions.fail("Button '" + buttonType + "' wasn't found on the cart page");
        }
    }

    @When("a user removes the second product from the cart")
    public void removeSecondProductFromCart() {
        List<WebElement> removeProductButtons = getDriver().findElements(By.xpath(CartConstants.REMOVE_BUTTON));
        if (removeProductButtons.size() > 1) {
            removeProductButtons.get(1).click();
        } else {
            Assertions.fail("There is no second product to remove from the cart");
        }
    }

    @When("a user clicks on the {string} product title")
    public void clickProductTitle(String productName) {
        List<WebElement> products = getDriver().findElements(By.xpath(CartConstants.ITEM_NAME));
        WebElement selectedProduct = null;
        for (WebElement webElement : products) {
            if (webElement.getText().equals(productName)) {
                selectedProduct = webElement;
            }
        }
        if (selectedProduct != null) {
            selectedProduct.click();
        } else {
            Assertions.fail("Product wasn't found");
        }
    }

    @Then("a user will be redirected to the checkout page")
    public void userRedirectedToCheckoutPage() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofMillis(5));
        wait.until(ExpectedConditions.urlToBe(PageConstants.CHECKOUT_STEP_ONE_PAGE));
        Assertions.assertFalse(getDriver().findElements(By.xpath(InventoryConstants.APP_LOGO)).isEmpty(), "Logo not found");
    }

    @Then("a user will be redirected to the {string} product page")
    public void userRedirectedToProductPage(String expectedTitle) {
        String currentUrl = getDriver().getCurrentUrl();
        Assertions.assertTrue(currentUrl.contains(PageConstants.PRODUCT_PAGE), "Expected URL does not match current URL");

        WebElement productTitle = getDriver().findElement(By.xpath(CartConstants.ITEM_TITLE));
        String actualTitle = productTitle.getText().trim();
        Assertions.assertEquals(actualTitle, expectedTitle, "Expected title didn't match with actual title");
    }
}


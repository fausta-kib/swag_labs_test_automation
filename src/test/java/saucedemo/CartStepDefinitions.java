package saucedemo;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static saucedemo.WebDriverProvider.getDriver;

public class CartStepDefinitions {

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

    @Then("a user will be redirected to the {string} product page")
    public void userRedirectedToProductPage(String expectedTitle) {
        String currentUrl = getDriver().getCurrentUrl();
        Assertions.assertTrue(currentUrl.contains(GenericConstants.PRODUCT_PAGE), "Expected URL does not match current URL");

        WebElement productTitle = getDriver().findElement(By.xpath(CartConstants.ITEM_TITLE));
        String actualTitle = productTitle.getText().trim();
        Assertions.assertEquals(actualTitle, expectedTitle, "Expected title didn't match with actual title");
    }
}
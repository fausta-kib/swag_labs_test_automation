package saucedemo;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

import java.util.List;

import static saucedemo.WebDriverProvider.getDriver;

public class GenericStepDefinitions {

    @And("the menu {string} displayed")
    public void menuIsDisplayed(String menuStatus) {
        List<WebElement> burgerMenu = getDriver().findElements(By.xpath(GenericConstants.MENU));
        if (menuStatus.equals("is")) {
            Assertions.assertFalse(burgerMenu.isEmpty());
        } else {
            Assertions.assertTrue(burgerMenu.isEmpty());
        }
    }

    @And("the footer {string} displayed")
    public void footerIsDisplayed(String footerStatus) {
        List<WebElement> burgerMenu = getDriver().findElements(By.xpath(GenericConstants.FOOTER));
        if (footerStatus.equals("is")) {
            Assertions.assertFalse(burgerMenu.isEmpty());
        } else {
            Assertions.assertTrue(burgerMenu.isEmpty());
        }
    }

    @When("a user clicks on the {string} option")
    public void aUserClicksOnTheOption(String menuSelection) {
        WebElement button = null;
        switch (menuSelection) {
            case "All items":
                button = getDriver().findElement(By.xpath(GenericConstants.ALL_ITEMS));
                break;
            case "About":
                button = getDriver().findElement(By.xpath(GenericConstants.ABOUT));
                break;
            case "Logout":
                button = getDriver().findElement(By.xpath(GenericConstants.LOGOUT));
                break;
            case "Reset App State":
                button = getDriver().findElement(By.xpath(GenericConstants.RESET));
                break;
            case "X":
                button = getDriver().findElement(By.xpath(GenericConstants.X));
                break;
            default:
                Assertions.fail("Button '" + menuSelection + "' wasn't found on the cart page");
        }
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(button)).click();
    }

    @Then("a user is redirected to the {string} page")
    public void userRedirectedToCheckoutPage(String pageApp) {
        switch (pageApp) {
            case "login":
                getDriver().get(GenericConstants.LOG_IN_PAGE);
                break;
            case "inventory":
                getDriver().get(GenericConstants.INVENTORY_PAGE);
                break;
            case "product":
                getDriver().get(GenericConstants.PRODUCT_PAGE);
                break;
            case "shopping cart":
                getDriver().get(GenericConstants.CART_PAGE);
                break;
            case "checkout your information":
                getDriver().get(GenericConstants.CHECKOUT_STEP_ONE_PAGE);
                break;
            case "checkout overview":
                getDriver().get(GenericConstants.CHECKOUT_STEP_TWO_PAGE);
                break;
            case "checkout complete":
                getDriver().get(GenericConstants.CHECKOUT_COMPLETE_PAGE);
                break;
            case "about":
                getDriver().get(GenericConstants.ABOUT_PAGE);
                break;
            default:
                Assertions.fail("Page '" + pageApp + "' wasn't found");
                break;
        }
    }

    @When("a user clicks on the {string} button")
    public void userClicksButton(String pagesButtons) {
        WebElement button = null;
        switch (pagesButtons) {
            case "menu":
                button = getDriver().findElement(By.xpath(GenericConstants.MENU));
                break;
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
            case "Continue Shopping":
                button = getDriver().findElement(By.xpath(CartConstants.CONTINUE_BUTTON));
                break;
            case "Checkout":
                button = getDriver().findElement(By.xpath(CartConstants.CHECKOUT_BUTTON));
                break;
            case "Remove":
                button = getDriver().findElement(By.xpath(GenericConstants.REMOVE));
                break;
            case "Add to cart":
                button = getDriver().findElement(By.xpath(GenericConstants.ADD_TO_CART));
                break;
            case "Back to products":
                button = getDriver().findElement(By.xpath(GenericConstants.BACK_TO_PRODUCTS));
                break;
            case "Shopping card":
                button = getDriver().findElement(By.xpath(CartConstants.SHOPPING_CART));
                break;
            case "Twitter":
                button = getDriver().findElement(By.xpath(GenericConstants.TWITTER_BUTTON));
                break;
            case "Facebook":
                button = getDriver().findElement(By.xpath(GenericConstants.FACEBOOK_BUTTON));
                break;
            case "LinkedIn":
                button = getDriver().findElement(By.xpath(GenericConstants.LINKEDIN_BUTTON));
                break;
            default:
                Assertions.fail("Button '" + pagesButtons + "' wasn't found on the cart page");
        }
        button.click();
    }

    @Then("a new {string} browser tab will be opened")
    public void verifyNewTabOpenedWithExpectedUrl(String socialMediaSelection) {
        WebElement button;
        String expectedUrl;
        switch (socialMediaSelection) {
            case "Twitter":
                expectedUrl = GenericConstants.TWITTER;
                break;
            case "Facebook":
                expectedUrl = GenericConstants.FACEBOOK;
                break;
            case "LinkedIn":
                expectedUrl = GenericConstants.LINKEDIN;
                break;
            default:
                throw new IllegalArgumentException("Invalid social media selection: " + socialMediaSelection);
        }

        String currentWindowHandle = getDriver().getWindowHandle();
        Set<String> windowHandles = getDriver().getWindowHandles();
        windowHandles.remove(currentWindowHandle);
        String newTabHandle = windowHandles.iterator().next();
        getDriver().switchTo().window(newTabHandle);

        String currentUrl = getDriver().getCurrentUrl();
        Assertions.assertTrue(currentUrl.contains(expectedUrl), "The URL of the new tab does not match the expected URL.");
    }
}


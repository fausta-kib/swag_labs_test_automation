package saucedemo;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static saucedemo.WebDriverProvider.getDriver;

public class GenericStepDefinitions {

    @And("menu {string} displayed")
    public void menuIsDisplayed(String displayedStatus) {
        List<WebElement> burgerMenu = getDriver().findElements(By.xpath(PageConstants.MENU));
        if (displayedStatus.equals("is")) {
            Assertions.assertFalse(burgerMenu.isEmpty());
        } else {
            Assertions.assertTrue(burgerMenu.isEmpty());
        }
    }

    @And("a user clicks on the menu icon")
    public void aUserClicksOnTheMenuIcon() {
        WebElement continueShoppingButton = getDriver().findElement(By.xpath(PageConstants.MENU));
        continueShoppingButton.click();
    }

    @When("a user clicks on the {string} option")
    public void aUserClicksOnTheOption(String menuSelection) {
        WebElement button = null;
        switch (menuSelection) {
            case "All items":
                button = getDriver().findElement(By.xpath(PageConstants.ALL_ITEMS));
                break;
            case "About":
                button = getDriver().findElement(By.xpath(PageConstants.ABOUT));
                break;
            case "Logout":
                button = getDriver().findElement(By.xpath(PageConstants.LOGOUT));
                break;
            case "Reset App State":
                button = getDriver().findElement(By.xpath(PageConstants.RESET));
                break;
            case "X":
                button = getDriver().findElement(By.xpath(PageConstants.X));
                break;
            default:
                Assertions.fail("Button '" + menuSelection + "' wasn't found on the cart page");
        }
        button.click();
    }

    @Then("a user is redirected to the {string} page")
    public void userRedirectedToCheckoutPage(String pageApp) {
        switch (pageApp) {
            case "login":
                getDriver().get(PageConstants.LOG_IN_PAGE);
                break;
            case "inventory":
                getDriver().get(PageConstants.INVENTORY_PAGE);
                break;
            case "product":
                getDriver().get(PageConstants.PRODUCT_PAGE);
                break;
            case "shopping cart":
                getDriver().get(PageConstants.CART_PAGE);
                break;
            case "checkout your information":
                getDriver().get(PageConstants.CHECKOUT_STEP_ONE_PAGE);
                break;
            case "checkout overview":
                getDriver().get(PageConstants.CHECKOUT_STEP_TWO_PAGE);
                break;
            case "checkout complete":
                getDriver().get(PageConstants.CHECKOUT_COMPLETE_PAGE);
                break;
            case "about":
                getDriver().get(PageConstants.ABOUT_PAGE);
                break;
            default:
                Assertions.fail("Page '" + pageApp + "' wasn't found");
                break;
        }
    }
}

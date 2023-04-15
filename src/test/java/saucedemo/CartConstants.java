package saucedemo;

public class CartConstants {
    public static final String SHOPPING_CART = "//div[@id='shopping_cart_container']";
    public static final String CONTINUE_BUTTON = "//button[@id='continue-shopping']";
    public static final String CHECKOUT_BUTTON = "//button[@id='checkout']";
    public static final String CARD_ITEM = "//div[@class='cart_item']";
    public static final String REMOVE_BUTTON = "//button[@class='btn btn_secondary btn_small cart_button']";
    public static final String ITEM_NAME = "//div[@class='inventory_item_name']";
    public static final String ITEM_REMOVE_BUTTON = "//div[@class='inventory_item_name' and contains(text(), 'Sauce Labs Backpack')]//ancestor::div[2]//div[@class='pricebar']//button[@class='btn btn_primary btn_small btn_inventory']";
    public static final String ITEM_TITLE = "//div[@class='inventory_details_name large_size']";
}

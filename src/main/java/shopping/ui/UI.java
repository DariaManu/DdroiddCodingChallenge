package shopping.ui;

import shopping.model.Item;
import shopping.repo.RepoException;
import shopping.service.Service;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UI {
    private Service service;
    private Scanner scanner = new Scanner(System.in);

    private static String DISPLAY_ITEMS_FORMAT = "%s - $%s";
    private static String DISPLAY_SHOPPING_CART_FORMAT = "%s x %d";

    public UI(Service service) {
        this.service = service;
    }

    /**
     * display available options in the menu
     */
    private void showMenu() {
        System.out.println("Type \"items\" to view all items in the catalog");
        System.out.println("Type \"exit\" to exit application");
        System.out.println("Type \"add\" to add an item to your shopping cart");
        System.out.println("Type \"checkout\" to proceed to checkout");
        System.out.println();
    }

    /**
     * display all the items in the catalog
     */
    private void showItems() {
        List<Item> items = service.getAllItems();
        for(Item item: items) {
            System.out.println(String.format(DISPLAY_ITEMS_FORMAT, item.getItemName(), item.getItemPrice().toString()));
        }
        System.out.println();
    }

    /**
     * read an item name from the user and add the item to the shopping cart
     */
    private void addItemToShoppingCart() {
        System.out.println("---Special offers---");
        System.out.println("10% off keyboards  |  50% off one desk lamp if you buy 2 monitors  |  $10 off shipping when at least 2 items bought");
        System.out.print("\tType the name of the item: ");
        String itemName = scanner.nextLine();
        try {
            service.addItemToShoppingCart(itemName);
        } catch (RepoException exception) {
            System.out.println(exception.getMessage());
        }
        showShoppingCart();
    }

    /**
     * display all the items already added in the shopping cart
     */
    private void showShoppingCart() {
        Map<Item, Integer> shoppingCart = service.getShoppingCart();
        for(Item item: shoppingCart.keySet()) {
            Integer numberOfItems = shoppingCart.get(item);
            System.out.println(String.format(DISPLAY_SHOPPING_CART_FORMAT, item.getItemName(), numberOfItems));
        }
        System.out.println();
    }

    /**
     * display the shopping cart and the invoice details for the order
     */
    private void checkoutWithSpecialOffersAndVAT() {
        System.out.println("---Shopping Cart---");
        showShoppingCart();
        Map<String, Double> totalPrices = service.checkoutWithSpecialOffersAndVAT();
        System.out.println("---Invoice---");
        System.out.println("Subtotal: " + totalPrices.get("subtotal"));
        System.out.println("Shipping: " + totalPrices.get("shipping"));
        System.out.println("VAT: " + totalPrices.get("vat"));
        System.out.println();
        System.out.println("---Discounts---");
        if (totalPrices.containsKey("discount keyboard")) {
            System.out.println("10% off keyboards: -$" + totalPrices.get("discount keyboard"));
        }
        if (totalPrices.containsKey("discount desk lamp")) {
            System.out.println("50% off one desk lamp: -$" + totalPrices.get("discount desk lamp"));
        }
        if (totalPrices.containsKey("discount shipping")) {
            System.out.println("$10 off shipping: -$" + totalPrices.get("discount shipping"));
        }
        System.out.println();
        System.out.println("Total: " + totalPrices.get("total"));
        System.out.println();
    }

    /**
     * main method for reading commands from the user
     */
    public void run() {
        String command = "";
        boolean canContinue = true;
        showMenu();
        while(canContinue) {
            System.out.print("Your command:");
            command = scanner.nextLine();
            switch (command) {
                case "items":
                    showItems();
                    break;
                case "add":
                    addItemToShoppingCart();
                    break;
                case "checkout":
                    checkoutWithSpecialOffersAndVAT();
                    break;
                case "exit":
                    canContinue = false;
                    break;
                default:
                    System.out.println("Invalid command!");
                    break;
            }
        }
    }
}

package shopping.ui;

import shopping.model.Item;
import shopping.service.RepoException;
import shopping.service.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UI {
    private Service service;
    private Scanner scanner = new Scanner(System.in);

    private static String DISPLAY_ITEMS_FORMAT = "%s - $ %s";
    private static String DISPLAY_SHOPPING_CART_FORMAT = "%s x %d";

    public UI(Service service) {
        this.service = service;
    }

    private void showMenu() {
        System.out.println("Type \"items\" to view all items in the catalog");
        System.out.println("Type \"exit\" to exit application");
        System.out.println("Type \"add\" to add an item to your shopping cart");
        System.out.println("Type \"checkout\" to proceed to checkout");
        System.out.println();
    }

    private void showItems() {
        List<Item> items = service.getAllItems();
        for(Item item: items) {
            System.out.println(String.format(DISPLAY_ITEMS_FORMAT, item.getItemName(), item.getItemPrice().toString()));
        }
        System.out.println();
    }

    private void addItemToShoppingCart() {
        System.out.print("\tType the name of the item: ");
        String itemName = scanner.nextLine();
        try {
            service.addItemToShoppingCart(itemName);
        } catch (RepoException exception) {
            System.out.println(exception.getMessage());
        }
        showShoppingCart();
    }

    private void showShoppingCart() {
        HashMap<Item, Integer> shoppingCart = service.getShoppingCart();
        for(Item item: shoppingCart.keySet()) {
            Integer numberOfItems = shoppingCart.get(item);
            System.out.println(String.format(DISPLAY_SHOPPING_CART_FORMAT, item.getItemName(), numberOfItems));
        }
        System.out.println();
    }

    private void checkout() {
        System.out.println("Shopping Cart");
        showShoppingCart();
        Map<String, Double> totalPrices = service.checkout();
        System.out.println("Invoice");
        System.out.println("Subtotal: " + totalPrices.get("subtotal"));
        System.out.println("Shipping: " + totalPrices.get("shipping"));
        System.out.println("Total: " + totalPrices.get("total"));
        System.out.println();
    }

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
                    checkout();
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

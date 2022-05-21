package shopping.ui;

import shopping.model.Item;
import shopping.service.Service;

import java.util.List;
import java.util.Scanner;

public class UI {
    private Service service;
    private Scanner scanner = new Scanner(System.in);

    private static String DISPLAY_ITEMS_FORMAT = "%s - $ %s";

    public UI(Service service) {
        this.service = service;
    }

    private void showMenu() {
        System.out.println("Type \"items\" to view all items in the catalog");
        System.out.println("Type \"exit\" to exit application");
        System.out.println();
    }

    private void showItems() {
        List<Item> items = service.getAllItems();
        for(Item item: items) {
            System.out.println(String.format(DISPLAY_ITEMS_FORMAT, item.getItemName(), item.getItemPrice().toString()));
        }
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
                case "items": {
                    showItems();
                    break;
                }
                case "exit":
                    canContinue = false;
                    break;
                default: {
                    System.out.println("Invalid command!");
                    break;
                }
            }
        }
    }
}

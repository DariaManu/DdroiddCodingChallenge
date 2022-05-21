package shopping.service;

import shopping.model.Item;
import shopping.model.ShippingRate;
import shopping.repo.InMemoryRepo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Service {
    private InMemoryRepo repo;

    private HashMap<Item, Integer> shoppingCart;

    private static double SHIPPING_RATE_PER_KG = 0.1;

    public Service(InMemoryRepo repo) {
        this.repo = repo;
        shoppingCart = new HashMap<>();
    }

    public List<Item> getAllItems() {
        return repo.getAllItems();
    }

    public void addItemToShoppingCart(String itemName) throws RepoException{
        Item item = repo.getItemByItemName(itemName);
        if (shoppingCart.containsKey(item))
            shoppingCart.put(item, shoppingCart.get(item) + 1);
        else
            shoppingCart.put(item, 1);
    }

    public HashMap<Item, Integer> getShoppingCart() {
        return shoppingCart;
    }

    public Map<String, Double> checkout() {
        Map<String, Double> totalPrices = new HashMap<>();
        Double subtotal = computeSubtotal();
        Double shipping = computeShipping();
        Double total = subtotal + shipping;
        totalPrices.put("subtotal", subtotal);
        totalPrices.put("shipping", shipping);
        totalPrices.put("total", total);
        clearShoppingCart();
        return totalPrices;
    }

    private void clearShoppingCart() {
        shoppingCart.clear();
    }

    private Double computeSubtotal() {
        Double currentSubTotal = 0.0d;
        for (Item item: shoppingCart.keySet()) {
            int numberOfItems = shoppingCart.get(item);
            currentSubTotal += item.getItemPrice() * numberOfItems;
        }
        return (double) (Math.round(currentSubTotal*100.0)/100.0);
    }

    private Double computeShipping() {
        Double currentShipping = 0.0d;
        for (Item item: shoppingCart.keySet()) {
            int numberOfItems = shoppingCart.get(item);
            ShippingRate shippingRate = repo.getShippingRateForItem(item);
            currentShipping += Math.round(((item.getWeight() * shippingRate.getRate()) / SHIPPING_RATE_PER_KG) * numberOfItems);
        }
        return currentShipping;
    }
}

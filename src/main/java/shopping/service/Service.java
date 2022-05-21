package shopping.service;

import shopping.model.Item;
import shopping.model.ShippingRate;
import shopping.repo.InMemoryRepo;

import java.util.*;

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

    public Map<String, Double> checkoutWithSpecialOffers() {
        Map<String, Double> totalPrices = new HashMap<>();
        Double subtotal = computeSubtotal();
        totalPrices.put("subtotal", subtotal);
        Double shipping = computeShipping();
        totalPrices.put("shipping", shipping);
        Double discountKeyboards = applySpecialOfferForKeyboards();
        if (discountKeyboards != 0.0)
            totalPrices.put("discount keyboard", discountKeyboards);
        Double discountDeskLamp = applySpecialOfferFor2Monitors();
        if (discountDeskLamp != 0.0)
            totalPrices.put("discount desk lamp", discountDeskLamp);
        Double discountShipping = applySpecialOfferFor2ItemsOrMore();
        if (discountShipping != 0.0)
            totalPrices.put("discount shipping", discountShipping);
        Double total = subtotal + shipping - discountKeyboards - discountDeskLamp - discountShipping;
        total = roundDoubleValueTo2Decimals(total);
        totalPrices.put("total", total);
        return totalPrices;
    }

    public Double applySpecialOfferForKeyboards() {
        Double discount = 0.0;
        Optional<Item> keyboardItemOptional = shoppingCart.keySet().stream().filter(item -> item.getItemName().equals("Keyboard")).findFirst();
        if(keyboardItemOptional.isPresent()) {
            Item keyboardItem = keyboardItemOptional.get();
            int numberOfKeyboards = shoppingCart.get(keyboardItem);
            for (int i = 0; i < numberOfKeyboards; i++) {
                Double reducedValue = 0.1 * keyboardItem.getItemPrice();
                discount += roundDoubleValueTo2Decimals(reducedValue);
            }
        }
        return discount;
    }

    public Double applySpecialOfferFor2Monitors() {
        Double discount = 0.0;
        Optional<Item> monitorItemOptional = shoppingCart.keySet().stream().filter(item -> item.getItemName().equals("Monitor")).findFirst();
        if (monitorItemOptional.isPresent()) {
            Item monitorItem = monitorItemOptional.get();
            if (shoppingCart.get(monitorItem) >= 2) {
                Optional<Item> deskLampOptional = shoppingCart.keySet().stream().filter(item -> item.getItemName().equals("Desk Lamp")).findFirst();
                if (deskLampOptional.isPresent()) {
                    Double halfPriceForLamp = deskLampOptional.get().getItemPrice() / 2;
                    discount += roundDoubleValueTo2Decimals(halfPriceForLamp);
                }
            }
        }
        return discount;
    }

    public Double applySpecialOfferFor2ItemsOrMore() {
        Double discount = 0.0;
        int numberOfItemsOrdered = shoppingCart.values().stream().reduce(0, Integer::sum);
        if (numberOfItemsOrdered >= 2)
            discount = 10.0;
        return discount;
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

    private Double roundDoubleValueTo2Decimals(Double value) {
        return (double) (Math.floor(value * 100.0) / 100.0);
    }
}

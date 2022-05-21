package shopping.service;

import shopping.model.Item;
import shopping.model.ShippingRate;
import shopping.repo.InMemoryRepo;

import java.util.*;

public class Service {
    private InMemoryRepo repo;

    private HashMap<Item, Integer> shoppingCart;

    private static double SHIPPING_RATE_PER_KG = 0.1;
    private static double KEYBOARD_DISCOUNT = 0.1;
    private static double DESK_LAMP_DISCOUNT = 0.5;
    private static double SHIPPING_DISCOUNT = 10.0;
    private static double VAT = 0.19;

    public Service(InMemoryRepo repo) {
        this.repo = repo;
        shoppingCart = new HashMap<>();
    }

    /**
     *
     * @return list containing all the items in the catalog
     */
    public List<Item> getAllItems() {
        return repo.getAllItems();
    }

    /**
     * add an Item to the shopping cart, or increase its quantity if it already exists
     * @param itemName - name of the item that is searched in the catalog
     * @throws RepoException if there is no item in the catalog with the given name
     */
    public void addItemToShoppingCart(String itemName) throws RepoException{
        Item item = repo.getItemByItemName(itemName);
        if (shoppingCart.containsKey(item))
            shoppingCart.put(item, shoppingCart.get(item) + 1);
        else
            shoppingCart.put(item, 1);
    }

    /**
     *
     * @return the contents of the user's shopping cart (items and their quantities)
     */
    public HashMap<Item, Integer> getShoppingCart() {
        return shoppingCart;
    }

    /**
     *
     * @return
     */
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

    /**
     * compute the values required to get the total price of the shopping cart
     * @return a map containing the details of the invoice for the customer's order (subtotal, shipping, vat, discounts, total)
     */
    public Map<String, Double> checkoutWithSpecialOffersAndVAT() {
        Map<String, Double> totalPrices = new HashMap<>();
        Double subtotal = computeSubtotal();
        totalPrices.put("subtotal", subtotal);
        Double vat = applyVAT(subtotal);
        totalPrices.put("vat", vat);
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
        Double total = subtotal + vat + shipping - discountKeyboards - discountDeskLamp - discountShipping;
        total = roundDoubleValueTo2Decimals(total);
        totalPrices.put("total", total);
        clearShoppingCart();
        return totalPrices;
    }

    /**
     * apply a KEYBOARD_DISCOUNT to the price of every keyboard in the shopping cart
     * @return the discount obtained
     */
    public Double applySpecialOfferForKeyboards() {
        Double discount = 0.0;
        Optional<Item> keyboardItemOptional = shoppingCart.keySet().stream().filter(item -> item.getItemName().equals("Keyboard")).findFirst();
        if(keyboardItemOptional.isPresent()) {
            Item keyboardItem = keyboardItemOptional.get();
            int numberOfKeyboards = shoppingCart.get(keyboardItem);
            for (int i = 0; i < numberOfKeyboards; i++) {
                Double reducedValue = KEYBOARD_DISCOUNT * keyboardItem.getItemPrice();
                discount += roundDoubleValueTo2Decimals(reducedValue);
            }
        }
        return discount;
    }

    /**
     * apply a DESK_LAMP_DISCOUNT to the price of a lamp desk if there are at least 2 monitors in the shopping cart
     * @return the discount obtained
     */
    public Double applySpecialOfferFor2Monitors() {
        Double discount = 0.0;
        Optional<Item> monitorItemOptional = shoppingCart.keySet().stream().filter(item -> item.getItemName().equals("Monitor")).findFirst();
        if (monitorItemOptional.isPresent()) {
            Item monitorItem = monitorItemOptional.get();
            if (shoppingCart.get(monitorItem) >= 2) {
                Optional<Item> deskLampOptional = shoppingCart.keySet().stream().filter(item -> item.getItemName().equals("Desk Lamp")).findFirst();
                if (deskLampOptional.isPresent()) {
                    Double reducedPriceForLamp = deskLampOptional.get().getItemPrice() * DESK_LAMP_DISCOUNT;
                    discount += roundDoubleValueTo2Decimals(reducedPriceForLamp);
                }
            }
        }
        return discount;
    }

    /**
     * apply a SHIPPING_DISCOUNT if there are at least 2 items in the shopping cart
     * @return the discount obtained
     */
    public Double applySpecialOfferFor2ItemsOrMore() {
        Double discount = 0.0;
        int numberOfItemsOrdered = shoppingCart.values().stream().reduce(0, Integer::sum);
        if (numberOfItemsOrdered >= 2)
            discount = SHIPPING_DISCOUNT;
        return discount;
    }

    /**
     * apply a VAT to the subtotal of the shopping cart
     * @param subtotal - the subtotal of the shopping cart before any discounts are applied
     * @return the value of the vat
     */
    public Double applyVAT(Double subtotal) {
        Double vat = VAT * subtotal;
        return roundDoubleValueTo2Decimals(vat);
    }

    /**
     * remove all items in the shopping cart
     */
    private void clearShoppingCart() {
        shoppingCart.clear();
    }

    /**
     * compute the subtotal of the shopping cart
     * @return the subtotal value of the shopping cart
     */
    private Double computeSubtotal() {
        Double currentSubTotal = 0.0d;
        for (Item item: shoppingCart.keySet()) {
            int numberOfItems = shoppingCart.get(item);
            currentSubTotal += item.getItemPrice() * numberOfItems;
        }
        return (double) (Math.round(currentSubTotal*100.0)/100.0);
    }

    /**
     * compute the shipping for the items in the shopping cart based on their respective shipping rates
     * @return the shipping value for the items
     */
    private Double computeShipping() {
        Double currentShipping = 0.0d;
        for (Item item: shoppingCart.keySet()) {
            int numberOfItems = shoppingCart.get(item);
            ShippingRate shippingRate = repo.getShippingRateForItem(item);
            currentShipping += Math.round(((item.getWeight() * shippingRate.getRate()) / SHIPPING_RATE_PER_KG) * numberOfItems);
        }
        return currentShipping;
    }

    /**
     * transform a decimal number so that is has only 2 values after the decimal point
     * @param value - a decimal number
     * @return transformed value
     */
    private Double roundDoubleValueTo2Decimals(Double value) {
        return (double) (Math.floor(value * 100.0) / 100.0);
    }
}

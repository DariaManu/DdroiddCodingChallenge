package shopping.repo;

import shopping.model.Item;
import shopping.model.ShippingCountry;
import shopping.model.ShippingRate;
import shopping.service.RepoException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryRepo {
    private Map<Item, ShippingRate> items;

    public InMemoryRepo() {
        items = new HashMap<>();
        loadItems();
    }

    /**
     * add the catalog of items and their respective shipping rates to items map
     */
    private void loadItems() {
        items.put(new Item("Mouse", 10.99d, ShippingCountry.RO, 0.2d), new ShippingRate(ShippingCountry.RO, 1));
        items.put(new Item("Keyboard", 40.99d, ShippingCountry.UK, 0.7d), new ShippingRate(ShippingCountry.UK, 2));
        items.put(new Item("Monitor", 164.99d, ShippingCountry.US, 1.9d), new ShippingRate(ShippingCountry.US, 3));
        items.put(new Item("Webcam", 84.99d, ShippingCountry.RO, 0.2d), new ShippingRate(ShippingCountry.RO, 1));
        items.put(new Item("Headphones", 59.99d, ShippingCountry.US, 0.6d), new ShippingRate(ShippingCountry.US, 3));
        items.put(new Item("Desk Lamp", 89.99d, ShippingCountry.UK, 1.3d), new ShippingRate(ShippingCountry.UK, 2));
    }

    /**
     *
     * @return list containing all the items in the catalog
     */
    public List<Item> getAllItems() {
        List<Item> availableItems = new ArrayList<>();
        for (Item item: items.keySet())
            availableItems.add(item);
        return availableItems;
    }

    /**
     *
     * @param itemName - name of the item that is searched in the catalog
     * @return the item in the catalog with name equal to itemName
     * @throws RepoException if there is no item in the catalog with the given name
     */
    public Item getItemByItemName(String itemName) throws RepoException{
        for (Item item: items.keySet()) {
            if (item.getItemName().equals(itemName))
                return item;
        }
        throw new RepoException("Item doesn't exist");
    }

    /**
     *
     * @param item - the item whose shipping rate is requested
     * @return the shipping rate corresponding to the given item
     */
    public ShippingRate getShippingRateForItem(Item item) {
        return items.get(item);
    }
}

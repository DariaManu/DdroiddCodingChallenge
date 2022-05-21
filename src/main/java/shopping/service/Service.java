package shopping.service;

import shopping.model.Item;
import shopping.repo.InMemoryRepo;

import java.util.HashMap;
import java.util.List;

public class Service {
    private InMemoryRepo repo;
    private HashMap<Item, Integer> shoppingCart;

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
}

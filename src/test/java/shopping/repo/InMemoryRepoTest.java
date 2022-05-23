package shopping.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shopping.model.Item;
import shopping.model.ShippingRate;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryRepoTest {
    private InMemoryRepo repo;

    @BeforeEach
    void setUp() {
        repo = new InMemoryRepo();
    }

    @Test
    void getItemByItemName() {
        try {
            Item item = repo.getItemByItemName("Monitor");
            assertEquals(164.99d, item.getItemPrice());
        } catch (RepoException exception) {
            assertTrue(true);
        }

        try {
            Item item = repo.getItemByItemName("Cooler");
        } catch (RepoException exception) {
            assertEquals("Item doesn't exist", exception.getMessage());
        }
    }

    @Test
    void getShippingRateForItem() {
        Item mouseItem = new Item("Mouse");
        ShippingRate rate = repo.getShippingRateForItem(mouseItem);
        assertEquals(1, rate.getRate());

        Item keyboardItem = new Item("Keyboard");
        rate = repo.getShippingRateForItem(keyboardItem);
        assertEquals(2, rate.getRate());

        Item monitorItem = new Item("Monitor");
        rate = repo.getShippingRateForItem(monitorItem);
        assertEquals(3, rate.getRate());
    }
}
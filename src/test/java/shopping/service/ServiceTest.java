package shopping.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.repo.InMemoryRepo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {
    private InMemoryRepo repo;
    private Service service;

    @BeforeEach
    void setUp() {
        repo = new InMemoryRepo();
        service = new Service(repo);
    }

    @Test
    @DisplayName("checkout() test")
    void checkout() {
        Map<String, Double> totalPrices = new HashMap<>();

        totalPrices = service.checkout();
        assertEquals(totalPrices.get("subtotal"), 0.0);
        assertEquals(totalPrices.get("shipping"), 0.0);
        assertEquals(totalPrices.get("total"), 0.0);

        try {
            service.addItemToShoppingCart("Monitor");
            totalPrices = service.checkout();
            assertEquals(totalPrices.get("subtotal"), 164.99);
            assertEquals(totalPrices.get("shipping"), 57.00);
            assertEquals(totalPrices.get("total"), 221.99);
        } catch (RepoException exception) {
            assertTrue(true);
        }

        try {
            service.addItemToShoppingCart("Mouse");
            service.addItemToShoppingCart("Monitor");
            service.addItemToShoppingCart("Monitor");
            totalPrices = service.checkout();
            assertEquals(totalPrices.get("subtotal"), 340.97);
            assertEquals(totalPrices.get("shipping"), 116.0);
            assertEquals(totalPrices.get("total"), 456.97);
        } catch (RepoException exception) {
            assertTrue(true);
        }

        try {
            service.addItemToShoppingCart("Mouse");
            service.addItemToShoppingCart("Keyboard");
            service.addItemToShoppingCart("Monitor");
            service.addItemToShoppingCart("Webcam");
            service.addItemToShoppingCart("Headphones");
            service.addItemToShoppingCart("Desk Lamp");
            totalPrices = service.checkout();
            assertEquals(totalPrices.get("subtotal"), 451.94);
            assertEquals(totalPrices.get("shipping"), 119.0);
            assertEquals(totalPrices.get("total"), 570.94);
        } catch (RepoException exception) {
            assertTrue(true);
        }

        try {
            service.addItemToShoppingCart("Webcam");
            service.addItemToShoppingCart("Headphones");
            service.addItemToShoppingCart("Headphones");
            service.addItemToShoppingCart("Desk Lamp");
            totalPrices = service.checkout();
            assertEquals(totalPrices.get("subtotal"), 294.96);
            assertEquals(totalPrices.get("shipping"), 64.0);
            assertEquals(totalPrices.get("total"), 358.96);
        } catch (RepoException exception) {
            assertTrue(true);
        }
    }
}
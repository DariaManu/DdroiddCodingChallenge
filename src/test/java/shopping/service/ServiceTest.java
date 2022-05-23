package shopping.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.repo.InMemoryRepo;
import shopping.repo.RepoException;

import java.util.HashMap;
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
    @DisplayName("checkoutWithSpecialOffersAndVAT() test")
    void checkoutWithSpecialOffersAndVAT() {
        Map<String, Double> totalPrices = new HashMap<>();

        totalPrices = service.checkoutWithSpecialOffersAndVAT();
        assertEquals(totalPrices.get("subtotal"), 0.0);
        assertEquals(totalPrices.get("shipping"), 0.0);
        assertEquals(totalPrices.get("total"), 0.0);

        try {
            service.addItemToShoppingCart("Monitor");
            totalPrices = service.checkoutWithSpecialOffersAndVAT();
            assertEquals(totalPrices.get("subtotal"), 164.99);
            assertEquals(totalPrices.get("shipping"), 57.00);
            assertEquals(totalPrices.get("vat"), 31.34);
            assertEquals(totalPrices.get("total"), 253.33);
        } catch (RepoException exception) {
            assertTrue(true);
        }

        try {
            service.addItemToShoppingCart("Mouse");
            service.addItemToShoppingCart("Monitor");
            service.addItemToShoppingCart("Monitor");
            totalPrices = service.checkoutWithSpecialOffersAndVAT();
            assertEquals(totalPrices.get("subtotal"), 340.97);
            assertEquals(totalPrices.get("shipping"), 116.0);
            assertEquals(totalPrices.get("vat"), 64.76);
            assertEquals(totalPrices.get("discount shipping"), 10.0);
            assertEquals(totalPrices.get("total"), 511.73);
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
            totalPrices = service.checkoutWithSpecialOffersAndVAT();
            assertEquals(totalPrices.get("subtotal"), 451.94);
            assertEquals(totalPrices.get("shipping"), 119.0);
            assertEquals(totalPrices.get("vat"), 85.82);
            assertEquals(totalPrices.get("discount keyboard"), 4.09);
            assertEquals(totalPrices.get("discount shipping"), 10.0);
            assertEquals(totalPrices.get("total"), 642.66);
        } catch (RepoException exception) {
            assertTrue(true);
        }

        try {
            service.addItemToShoppingCart("Webcam");
            service.addItemToShoppingCart("Headphones");
            service.addItemToShoppingCart("Headphones");
            service.addItemToShoppingCart("Desk Lamp");
            totalPrices = service.checkoutWithSpecialOffersAndVAT();
            assertEquals(totalPrices.get("subtotal"), 294.96);
            assertEquals(totalPrices.get("shipping"), 64.0);
            assertEquals(totalPrices.get("vat"), 56.01);
            assertEquals(totalPrices.get("discount shipping"), 10.0);
            assertEquals(totalPrices.get("total"), 404.97);
        } catch (RepoException exception) {
            assertTrue(true);
        }

        try {
            service.addItemToShoppingCart("Webcam");
            service.addItemToShoppingCart("Monitor");
            service.addItemToShoppingCart("Monitor");
            service.addItemToShoppingCart("Desk Lamp");
            totalPrices = service.checkoutWithSpecialOffersAndVAT();
            assertEquals(totalPrices.get("subtotal"), 504.96);
            assertEquals(totalPrices.get("shipping"), 142.0);
            assertEquals(totalPrices.get("vat"), 95.91);
            assertEquals(totalPrices.get("discount shipping"), 10.0);
            assertEquals(totalPrices.get("discount desk lamp"), 44.99);
            assertEquals(totalPrices.get("total"), 687.88);
        } catch (RepoException exception) {
            assertTrue(true);
        }
    }

    @Test
    void applySpecialOfferForKeyboards() {
        Double discount = 0.0;
        try {
            service.addItemToShoppingCart("Keyboard");
            discount = service.applySpecialOfferForKeyboards();
            assertEquals(discount, 4.09);
            service.addItemToShoppingCart("Keyboard");
            discount = service.applySpecialOfferForKeyboards();
            assertEquals(discount, 8.18);
            service.addItemToShoppingCart("Keyboard");
            discount = service.applySpecialOfferForKeyboards();
            assertEquals(discount, 12.27);
        } catch (RepoException exception) {
            assertTrue(true);
        }
    }

    @Test
    void applySpecialOfferFor2Monitors() {
        Double discount = 0.0;
        try {
            service.addItemToShoppingCart("Keyboard");
            service.addItemToShoppingCart("Monitor");
            service.addItemToShoppingCart("Monitor");
            service.addItemToShoppingCart("Desk Lamp");
            discount = service.applySpecialOfferFor2Monitors();
            assertEquals(discount, 44.99);
        } catch (RepoException exception) {
            assertTrue(true);
        }
    }

    @Test
    void applySpecialOfferFor2ItemsOrMore() {
        Double discount = 0.0;
        try {
            service.addItemToShoppingCart("Keyboard");
            discount = service.applySpecialOfferFor2ItemsOrMore();
            assertEquals(discount, 0.0);
            service.addItemToShoppingCart("Monitor");
            discount = service.applySpecialOfferFor2ItemsOrMore();
            assertEquals(discount, 10.0);
            service.addItemToShoppingCart("Desk Lamp");
            discount = service.applySpecialOfferFor2ItemsOrMore();
            assertEquals(discount, 10.0);
        } catch (RepoException exception) {
            assertTrue(true);
        }
    }

    @Test
    void applyVAT() {
        Double vat = 0.0;
        try {
            service.addItemToShoppingCart("Keyboard");
            service.addItemToShoppingCart("Monitor");
            service.addItemToShoppingCart("Monitor");
            vat = service.applyVAT();
            assertEquals(vat, 70.45);
        } catch (RepoException exception) {
            assertTrue(true);
        }
    }
}
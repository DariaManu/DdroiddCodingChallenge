package shopping.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
    private Item mouseItem;
    private Item otherMouseItem;
    private Item keyboardItem;

    @BeforeEach
    void setUp() {
        mouseItem = new Item("Mouse", 10.99d, ShippingCountry.RO, 0.2d);
        otherMouseItem = new Item("Mouse", 10.99d, ShippingCountry.RO, 0.2d);
        keyboardItem = new Item("Keyboard", 40.99d, ShippingCountry.UK, 0.7d);
    }

    @Test
    @DisplayName("equals() test")
    void testEquals() {
        assertTrue(mouseItem.equals(otherMouseItem));
        assertFalse(mouseItem.equals(keyboardItem));
    }

    @Test
    @DisplayName("hashCode() test")
    void testHashCode() {
        assertTrue(mouseItem.hashCode()== otherMouseItem.hashCode());
        assertFalse(mouseItem.hashCode() == keyboardItem.hashCode());
    }
}
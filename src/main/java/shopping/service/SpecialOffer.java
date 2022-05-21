package shopping.service;

import shopping.model.Item;
import shopping.model.ShippingRate;

import java.util.List;
import java.util.Map;

public interface SpecialOffer {
    public Double applySpecialOffer(Map<Item, Integer> shoppingCart, List<ShippingRate> shippingRates);
}

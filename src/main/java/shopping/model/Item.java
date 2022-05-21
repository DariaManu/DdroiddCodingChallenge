package shopping.model;

import java.util.Objects;

public class Item {
    private String itemName;
    private Double itemPrice;
    private ShippingCountry shippedFrom;
    private Double weight;

    public Item(String itemName, Double itemPrice, ShippingCountry shippedFrom, Double weight) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.shippedFrom = shippedFrom;
        this.weight = weight;
    }

    public Item(String itemName) {
        this.itemName = itemName;
        this.itemPrice = 0.0d;
        this.shippedFrom = null;
        this.weight = 0.0d;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public ShippingCountry getShippedFrom() {
        return shippedFrom;
    }

    public void setShippedFrom(ShippingCountry shippedFrom) {
        this.shippedFrom = shippedFrom;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return getItemName().equals(item.getItemName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getItemName());
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemName='" + itemName + '\'' +
                ", itemPrice=" + itemPrice +
                ", shippedFrom=" + shippedFrom +
                ", weight=" + weight +
                '}';
    }
}

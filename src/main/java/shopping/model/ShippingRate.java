package shopping.model;

import java.util.Objects;

public class ShippingRate {
    private ShippingCountry country;
    private Integer rate;

    public ShippingRate(ShippingCountry country, Integer rate) {
        this.country = country;
        this.rate = rate;
    }

    public ShippingCountry getCountry() {
        return country;
    }

    public void setCountry(ShippingCountry country) {
        this.country = country;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShippingRate)) return false;
        ShippingRate that = (ShippingRate) o;
        return getCountry() == that.getCountry();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCountry());
    }

    @Override
    public String toString() {
        return "ShippingRate{" +
                "country=" + country +
                ", rate=" + rate +
                '}';
    }
}

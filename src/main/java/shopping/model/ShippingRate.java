package shopping.model;

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
    public String toString() {
        return "ShippingRate{" +
                "country=" + country +
                ", rate=" + rate +
                '}';
    }
}

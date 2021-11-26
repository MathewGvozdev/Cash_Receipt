package main.com.mathewgv.app.entity;

public class DiscountCard {

    private final String number;
    private final int discount;

    public DiscountCard(String number, int discount) {
        this.number = number;
        this.discount = discount;
    }

    public String getNumber() {
        return number;
    }

    public int getDiscount() {
        return discount;
    }
}

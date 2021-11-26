package main.com.mathewgv.app.entity;

import main.com.mathewgv.app.property.Promotional;

public class Product implements Promotional {

    private static int idGenerator = 1;
    private final int id;
    private final String name;
    private final double price;
    private final boolean isPromotional;

    public Product(String name, double price, boolean isPromotional) {
        this.id = idGenerator++;
        this.name = name;
        this.price = price;
        this.isPromotional = isPromotional;
    }

    @Override
    public double sale() {
        double temp = this.price * (100 - Promotional.salePercentage) / 100;
        return Math.round(temp * 1e2) / 1e2;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public boolean isPromotional() {
        return isPromotional;
    }
}

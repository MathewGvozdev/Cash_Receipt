package main.com.mathewgv.app.entity;

import main.com.mathewgv.app.service.CheckFormatter;
import main.com.mathewgv.app.service.DataHandlerService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Check {

    private final List<Product> productList = DataHandlerService.getProductsToCheckList();
    private final Set<Product> productSet = new LinkedHashSet<>(productList);
    private final DiscountCard discountCard = DataHandlerService.getDiscountCardToCheck();
    private final List<String> checkLines = new ArrayList<>();

    public void printToConsole() {
        if (checkLines.isEmpty()) {
            initCheckLines();
        }
        for (String line : checkLines) {
            System.out.println(line);
        }
    }

    public void printToFile() {
        if (checkLines.isEmpty()) {
            initCheckLines();
        }
        try {
            Path checkFile = DataHandlerService.getCheckDirectory().resolve(Path.of(CheckFormatter.formatFileName()));
            if (!Files.exists(checkFile)) {
                Files.createFile(checkFile.toAbsolutePath());
            }
            Files.write(checkFile.toAbsolutePath(), checkLines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initCheckLines() {
        CheckFormatter.addHeadLines(checkLines);
        addProductLines();
        addResultLines();
    }

    private void addProductLines() {
        for (Product product : productSet) {
            int quantity = countProductQuantity(product);
            String name = product.getName();
            double price = product.getPrice();
            double priceOnPromotion = getPriceOnPromotion(product, quantity);
            double totalProductPrice;
            if (price == priceOnPromotion) {
                totalProductPrice = price * quantity;
                checkLines.add(CheckFormatter.toLineFormat(quantity, name, price, totalProductPrice));
            } else {
                totalProductPrice = priceOnPromotion * quantity;
                checkLines.add(CheckFormatter.toLineFormat(quantity, name, price));
                checkLines.add(CheckFormatter.toPromotionLineFormat(priceOnPromotion, totalProductPrice));
            }
        }
        checkLines.add(CheckFormatter.DASH_LINE);
    }

    private void addResultLines() {
        if (discountCard == null) {
            String total = CheckFormatter.toTotalLineFormat(countTotalPrice());
            checkLines.add(total);
        } else {
            String subtotal = CheckFormatter.toSubtotalLineFormat(countTotalPrice());
            double discount = countDiscount(countTotalPrice());
            String discountStr = CheckFormatter.toDiscountLineFormat(discountCard, discount);
            double totalWithDiscount = countTotalPriceWithDiscount(countTotalPrice(), discount);
            String totalWithDiscountStr = CheckFormatter.toTotalLineFormat(totalWithDiscount);
            Collections.addAll(checkLines, subtotal, discountStr, totalWithDiscountStr);
        }
        CheckFormatter.getThank(checkLines);
    }

    private double countTotalPrice() {
        double totalSum = 0;
        for (Product product : productSet) {
            int quantity = countProductQuantity(product);
            double price = product.getPrice();
            double priceOnPromotion = getPriceOnPromotion(product, quantity);
            double totalProductPrice;
            if (price == priceOnPromotion) {
                totalProductPrice = price * quantity;
                totalSum += totalProductPrice;
            } else {
                totalProductPrice = priceOnPromotion * quantity;
                totalSum += totalProductPrice;
            }
        }
        return totalSum;
    }

    private int countProductQuantity(Product product) {
        int quantity = 0;
        for (Product prod : productList) {
            if (prod.equals(product)) {
                quantity++;
            }
        }
        return quantity;
    }

    private double getPriceOnPromotion(Product product, int quantity) {
        double priceOnPromotion = product.getPrice();
        if (product.isPromotional() && quantity >= 5) {
            priceOnPromotion = product.sale();
        }
        return priceOnPromotion;
    }

    private double countDiscount(double price) {
        return discountCard.getDiscount() * price / 100;
    }

    private double countTotalPriceWithDiscount(double totalPrice, double discount) {
        return totalPrice - discount;
    }
}

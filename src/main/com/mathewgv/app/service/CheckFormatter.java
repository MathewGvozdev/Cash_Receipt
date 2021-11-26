package main.com.mathewgv.app.service;

import main.com.mathewgv.app.entity.DiscountCard;
import main.com.mathewgv.app.property.Promotional;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public class CheckFormatter {

    private static final String RECEIPT = "CASH RECEIPT";
    private static final String ADDRESS = "18 Molodezhnaya Novopolotsk";
    private static final String SHOP = "Shop \"DIONIS - 5\"";
    private static final String CASHIER = "Cashier N4";
    private static final String QUANTITY = "QTY";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String PRICE = "PRICE";
    private static final String SUBTOTAL = "SUBTOTAL";
    private static final String DISCOUNT = "DISCOUNT";
    private static final String TOTAL = "TOTAL";
    private static final String THANK = "THANKS FOR PURCHASING!";

    public static final String DASH_LINE = "#--------------------------------------#";
    public static final String STAR_LINE = "****************************************";
    private static final String STAR = "*";
    private static final String HASH = "#";

    private static final LocalDateTime dateTime = LocalDateTime.now();

    public static void addHeadLines(List<String> lines) {
        String line1 = String.format("%2$s%25s%2$14s", RECEIPT, STAR);
        String line2 = String.format("%2$s%28s%2$11s", SHOP, STAR);
        String line3 = String.format("%2$s%33s%2$6s", ADDRESS, STAR);
        String line4 = String.format("%3$s%10s%28s%3$s", CASHIER, formatDate(), HASH);
        String line5 = String.format("%2$s%38s%2$s", formatTime(), HASH);
        String line6 = String.format("%-5s%-18s%-10s%-7s", QUANTITY, DESCRIPTION, PRICE, TOTAL);

        Collections.addAll(lines, STAR_LINE, line1, line2, line3, line4, line5, DASH_LINE, line6);
    }

    public static String toLineFormat(int quantity, String name, double price, double totalPrice) {
        String priceStr = toPriceFormat(price);
        String totalPriceStr = toPriceFormat(totalPrice);
        return String.format("%-5s%-17s%-10s%-8s", quantity, name, priceStr, totalPriceStr);
    }

    public static String toLineFormat(int quantity, String name, double price) {
        String priceStr = toPriceFormat(price);
        return String.format("%-5s%-17s%-8s", quantity, name, priceStr);
    }

    public static String toPromotionLineFormat(double priceOnPromotion, double totalPrice) {
        String priceOnPromotionStr = toPriceFormat(priceOnPromotion);
        String totalPriceStr = toPriceFormat(totalPrice);
        return String.format("%18%%d  %-10s%-8s", Promotional.salePercentage, priceOnPromotionStr, totalPriceStr);
    }

    public static String toSubtotalLineFormat(double subtotalPrice) {
        return String.format("%-13s%27s", SUBTOTAL, toPriceFormat(subtotalPrice));
    }

    public static String toDiscountLineFormat(DiscountCard card, double discount) {
        return String.format("%s %s%%%28s", DISCOUNT, card.getDiscount(), toPriceFormat(discount));
    }

    public static String toTotalLineFormat(double totalPrice) {
        return String.format("%5s%35s", TOTAL, toPriceFormat(totalPrice));
    }

    public static String toPriceFormat(double price) {
        return new DecimalFormat("=$0.00").format(price);
    }

    public static void getThank(List<String> checkLines) {
        Collections.addAll(checkLines, DASH_LINE, getThankLine(), STAR_LINE);
    }

    private static String getThankLine() {
        return String.format("%2$s%31s%2$8s", THANK, HASH);
    }

    public static String formatFileName() {
        return String.format("check %s %s.txt", formatDate(), formatFileTime());
    }

    private static String formatFileTime() {
        return dateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH_mm_ss"));
    }

    private static String formatDate() {
        return dateTime.toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    private static String formatTime() {
        return dateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}

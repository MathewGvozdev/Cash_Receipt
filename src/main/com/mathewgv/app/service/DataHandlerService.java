package main.com.mathewgv.app.service;

import main.com.mathewgv.app.entity.DiscountCard;
import main.com.mathewgv.app.exceptions.IncorrectIdException;
import main.com.mathewgv.app.entity.Product;
import main.com.mathewgv.app.exceptions.InvalidCardNumberException;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DataHandlerService {

    private static final List<Product> productDataList = new ArrayList<>();
    private static final List<DiscountCard> discountCardDataList = new ArrayList<>();

    private static final List<Product> productsToCheckList = new ArrayList<>();
    private static DiscountCard discountCardToCheck;

    private static final Path productsInfo = Path.of("src", "main", "resources", "products.txt");
    private static final Path discountCardsInfo = Path.of("src", "main", "resources", "discount_cards.txt");
    private static final Path checkDirectory = Path.of("src", "main", "checks");

    public static void process(String[] arguments) {
        initProductDataList();
        initDiscountCardList();
        processArguments(arguments);
    }

    private static void initProductDataList() {
        try (BufferedReader productsReader = Files.newBufferedReader(productsInfo.toAbsolutePath())) {
            while (productsReader.ready()) {
                String productLineInfo = productsReader.readLine();
                String[] productArgs = productLineInfo.split(" ");
                String name = initProductName(productArgs);
                double price = initProductPrice(productArgs);
                boolean isPromotional = initProductPromotional(productArgs);
                productDataList.add(new Product(name, price, isPromotional));
            }
        } catch (IOException e) {
            System.err.println("Product data hasn't been initialized!");
            e.printStackTrace();
        }
    }

    private static void initDiscountCardList() {
        try (BufferedReader discountCardReader = Files.newBufferedReader(discountCardsInfo.toAbsolutePath())) {
            while (discountCardReader.ready()) {
                String discountCardLineInfo = discountCardReader.readLine();
                String[] discountCardArgs = discountCardLineInfo.split(" ");
                String number = initCardNumber(discountCardArgs);
                int discount = initCardDiscount(discountCardArgs);
                discountCardDataList.add(new DiscountCard(number, discount));
            }
        } catch (IOException e) {
            System.err.println("Discount card data hasn't been initialized!");
            e.printStackTrace();
        }
    }

    private static void processArguments(String[] arguments) {
        for (String argument : arguments) {
            String[] idAndQuantity = argument.split("-");
            try {
                int id = getId(idAndQuantity);
                int quantity = getQuantity(idAndQuantity);
                for (int i = 0; i < quantity; i++) {
                    addProductById(id);
                }
            } catch (NumberFormatException e) {
                String cardNumber = idAndQuantity[1];
                initDiscountCard(cardNumber);
            }
        }
    }

    private static void addProductById(int id) {
        Product seekProduct = null;
        for (Product product : productDataList) {
            if (id == product.getId()) {
                seekProduct = product;
            }
        }
        if (seekProduct == null) {
            try {
                throw new IncorrectIdException();
            } catch (IncorrectIdException e) {
                e.getExceptionMessage();
            }
        }
        productsToCheckList.add(seekProduct);
    }

    private static void initDiscountCard(String number) {
        DiscountCard seekCard = null;
        for (DiscountCard discountCard : discountCardDataList) {
            if (number.equals(discountCard.getNumber())) {
                seekCard = new DiscountCard(number, discountCard.getDiscount());
            }
        }
        discountCardToCheck = seekCard;
        try {
            if (discountCardToCheck == null)
                throw new InvalidCardNumberException();
        } catch (InvalidCardNumberException e) {
            e.getExceptionMessage();
        }
    }

    private static String initProductName(String[] productArgs) {
        return productArgs[0];
    }

    private static double initProductPrice(String[] productArgs) {
        return Double.parseDouble(productArgs[1]);
    }

    private static boolean initProductPromotional(String[] productArgs) {
        return productArgs[2].equals("true");
    }

    private static String initCardNumber(String[] discountCardArgs) {
        return discountCardArgs[0];
    }

    private static int initCardDiscount(String[] discountCardArg) {
        return Integer.parseInt(discountCardArg[1]);
    }

    private static int getId(String[] idAndQuantity) {
        return Integer.parseInt(idAndQuantity[0]);
    }

    private static int getQuantity(String[] idAndQuantity) {
        return Integer.parseInt(idAndQuantity[1]);
    }

    public static List<Product> getProductsToCheckList() {
        return productsToCheckList;
    }

    public static DiscountCard getDiscountCardToCheck() {
        return discountCardToCheck;
    }

    public static Path getCheckDirectory() {
        return checkDirectory;
    }
}

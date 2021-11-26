package main.com.mathewgv.app.exceptions;

public class InvalidCardNumberException extends Exception {

    public void getExceptionMessage() {
        System.err.println("Invalid discount card number!");
    }
}

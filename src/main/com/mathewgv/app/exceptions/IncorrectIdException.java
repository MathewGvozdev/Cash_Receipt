package main.com.mathewgv.app.exceptions;

public class IncorrectIdException extends Exception {

    public void getExceptionMessage() {
        System.err.println("Incorrect product id!");
        this.printStackTrace();
        System.exit(0);
    }
}

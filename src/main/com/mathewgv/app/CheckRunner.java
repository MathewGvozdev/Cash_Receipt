package main.com.mathewgv.app;

import main.com.mathewgv.app.entity.Check;
import main.com.mathewgv.app.service.DataHandlerService;

public class CheckRunner {

    public static void main(String[] args) {

        DataHandlerService.process(args);

        Check check = new Check();
        check.printToConsole();
        check.printToFile();
    }
}

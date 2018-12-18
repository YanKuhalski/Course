package by.bsuir.server;

import by.bsuir.server.base.BaseWorker;

import java.util.Scanner;

public class ConsoleListner implements Runnable {
    private Scanner scanner;

    {
        scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        while (true) {
            String nextLine = scanner.nextLine();
            switch (nextLine) {
                case "-saveDB":
                    BaseWorker.getInstance().saveAllData();
                    break;
                default:
                    System.out.println("Unknoun Operation");
                    break;
            }
        }

    }
}

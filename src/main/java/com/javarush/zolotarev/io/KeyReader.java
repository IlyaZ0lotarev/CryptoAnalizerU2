package com.javarush.zolotarev.io;

import java.util.Scanner;

public class KeyReader {
    private Scanner scanner;

    public KeyReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public Integer readKey(String text) {
        while (true) {
            System.out.println(text);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Ключ должен быть целым числом");
            }
        }
    }
}

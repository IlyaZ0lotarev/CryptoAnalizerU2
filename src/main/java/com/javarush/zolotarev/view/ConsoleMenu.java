package com.javarush.zolotarev.view;

import com.javarush.zolotarev.cipher.CaesarCipher;
import com.javarush.zolotarev.cipher.CipherCommands;
import com.javarush.zolotarev.cipher.CipherStrategy;
import com.javarush.zolotarev.handler.BruteForceHandler;
import com.javarush.zolotarev.handler.OperationHandler;
import com.javarush.zolotarev.io.FileProcessor;
import com.javarush.zolotarev.io.KeyReader;
import com.javarush.zolotarev.io.PathReader;
import java.util.Scanner;

public class ConsoleMenu {
    private final Scanner scanner;
    private final OperationHandler operationHandler;
    private final BruteForceHandler bruteForceHandler;

    public ConsoleMenu() {
        this.scanner = new Scanner(System.in);
        CipherStrategy cipher = new CaesarCipher();
        FileProcessor processor = new FileProcessor();
        PathReader pathReader = new PathReader(scanner);
        KeyReader keyReader = new KeyReader(scanner);
        this.operationHandler = new OperationHandler(pathReader, keyReader, cipher, processor);
        this.bruteForceHandler = new BruteForceHandler(pathReader);
    }

    public void run() {
        System.out.println("---- Caesar Cipher ---- \n");
        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> operationHandler.handleOperation(CipherCommands.ENCODE);
                case "2" -> operationHandler.handleOperation(CipherCommands.DECODE);
                case "3" -> bruteForceHandler.handleBruteForce();
                case "4" -> {
                    System.out.println("--Завершение работы--");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Неверный выбор. Введите: 1, 2 или 3. \n");
            }
        }
    }

    private void printMenu() {
        System.out.println("Выберите действие: \n");
        System.out.println("1. Зашифровать файл");
        System.out.println("2. Расшифровать файл");
        System.out.println("3. Расшифровка Brute Force (перебор ключей)");
        System.out.println("4. Выход");
    }
}

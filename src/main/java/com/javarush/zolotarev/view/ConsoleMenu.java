package com.javarush.zolotarev.view;

import com.javarush.zolotarev.BruteForce.BruteForce;
import com.javarush.zolotarev.cipher.CaesarCipher;
import com.javarush.zolotarev.cipher.CipherCommands;
import com.javarush.zolotarev.cipher.CipherStrategy;
import com.javarush.zolotarev.io.FileProcessor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.InvalidPathException;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {
    private final Scanner scanner;
    private final CipherStrategy cipher;
    private final FileProcessor processor;

    public ConsoleMenu() {
        this.scanner = new Scanner(System.in);
        this.cipher = new CaesarCipher();
        this.processor = new FileProcessor();
    }

    public void run() {
        System.out.println("---- Caesar Cipher ---- \n");
        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> handleOperation(CipherCommands.ENCODE);
                case "2" -> handleOperation(CipherCommands.DECODE);
                case "3" -> handleBruteForce();
                case "4" -> {
                    System.out.println("--Завершение работы--");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Неверный выбор. Введите: 1, 2 или 3. \n");
            }
        }
    }

    private void handleBruteForce() {
        System.out.println("Режим: Brute Force (перебор ключей)");
        Path inputPath = askPath("Введите путь к зашифрованному файлу: ", true);
        if (inputPath == null) {
            return;
        }

        try {
            String cipherText = Files.readString(inputPath, StandardCharsets.UTF_8);
            System.out.println("Перебор ключей");
            BruteForce cracker = new BruteForce();

            List<BruteForce.DecryptAttempt> attempts = cracker.keySearch(cipherText);
            BruteForce.DecryptAttempt bestKey = attempts.get(0);
            System.out.println("Готово. Ключ: " + bestKey.getKey());

            saveResult(bestKey.getText(), inputPath);

        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }


    private void saveResult(String text, Path originalPath) throws IOException {
        Path out = originalPath.resolveSibling(originalPath.getFileName().toString() + "_decrypted.txt");
        Files.writeString(out, text, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        System.out.println("Сохранено: " + out);
    }

    private void handleOperation(CipherCommands command) {
        String action = (command == CipherCommands.ENCODE) ? "шифрования" : "расшифрования";
        System.out.println("выбран режим: " + action);

        Path inputPath = askPath("введите путь к файлу: ", true);
        if (inputPath == null) {
            return;
        }

        Path outputPath = askPath("введите путь для сохранения результата: ", false);
        if (outputPath == null) {
            return;
        }
        if (Files.exists(outputPath) && Files.isDirectory(outputPath)) {
            String defaultName = "result.txt";
            outputPath = outputPath.resolve(defaultName);
        }

        Integer key = askKey("Введите ключ сдвига: ");
        if (key == null) {
            return;
        }

        System.out.println("Обработка...");
        processor.process(inputPath, outputPath, command, key, cipher);
        System.out.println("Успешно! Результат сохранён в: " + outputPath + "\n");
    }

    private Path askPath(String text, boolean checkExists) {
        while (true) {
            System.out.println(text);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Путь не может быть пустым");
                continue;
            }
            try {
                Path path = Path.of(input);
                if (checkExists) {
                    if (!Files.exists(path)) {
                        System.out.println("Файл не найден: " + path);
                    } else if (!Files.isRegularFile(path)) {
                        System.out.println("путь не является файлом.");
                    } else {
                        return path;
                    }
                } else {
                    return path;
                }
            } catch (InvalidPathException e) {
                System.out.println("Некорректный путь" + e.getMessage());
            }
        }
    }

    private Integer askKey(String text) {
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

    private void printMenu() {
        System.out.println("Выберите действие: \n");
        System.out.println("1. Зашифровать файл");
        System.out.println("2. Расшифровать файл");
        System.out.println("3. Расшифровка Brute Force (перебор ключей)");
        System.out.println("4. Выход");
    }
}

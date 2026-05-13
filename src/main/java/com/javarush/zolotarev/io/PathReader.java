package com.javarush.zolotarev.io;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Scanner;

public class PathReader {
    private Scanner scanner;

    public PathReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public Path readPath(String text, boolean checkExists) {
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
}

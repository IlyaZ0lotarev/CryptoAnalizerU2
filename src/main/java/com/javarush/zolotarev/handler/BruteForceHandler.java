package com.javarush.zolotarev.handler;

import com.javarush.zolotarev.cipher.BruteForce;
import com.javarush.zolotarev.io.PathReader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class BruteForceHandler {
    private final PathReader pathReader;

    public BruteForceHandler(PathReader pathReader) {
        this.pathReader = pathReader;
    }

    public void handleBruteForce() {
        System.out.println("Режим: Brute Force (перебор ключей)");
        Path inputPath = pathReader.readPath("Введите путь к зашифрованному файлу: ", true);
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
}

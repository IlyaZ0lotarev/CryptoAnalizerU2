package com.javarush.zolotarev.io;

import com.javarush.zolotarev.cipher.CipherCommands;
import com.javarush.zolotarev.cipher.CipherStrategy;
import com.javarush.zolotarev.exception.CipherException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileProcessor {
    public void process(Path inputPath, Path outputPath, CipherCommands command, int key, CipherStrategy cipher) {
        validateInput(inputPath);

        Path parentDir = outputPath.getParent();
        if (parentDir != null && !Files.exists(parentDir)) {
            try {
                Files.createDirectories(parentDir);
            } catch (IOException e) {
                throw new CipherException("Не удалось создать директорию: " + parentDir);
            }
        }

        try (BufferedReader reader = Files.newBufferedReader(inputPath, StandardCharsets.UTF_8);
             BufferedWriter writer = Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String processedLine = cipher.process(line, key, command);
                writer.write(processedLine);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new CipherException("Ошибка чтения/записи файлов: " + e.getMessage(), e);
        }
    }

    private void validateInput(Path inputPath) {
        if (!Files.exists(inputPath)) {
            throw new CipherException("файл не найден: " + inputPath);
        }
        if (!Files.isRegularFile(inputPath)) {
            throw new CipherException("некорректный путь к файлу: " + inputPath);
        }
    }
}
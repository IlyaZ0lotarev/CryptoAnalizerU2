package com.javarush.zolotarev.handler;

import com.javarush.zolotarev.cipher.CipherCommands;
import com.javarush.zolotarev.cipher.CipherStrategy;
import com.javarush.zolotarev.io.FileProcessor;
import com.javarush.zolotarev.io.KeyReader;
import com.javarush.zolotarev.io.PathReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class OperationHandler {
    private final PathReader pathReader;
    private final KeyReader keyReader;
    private final CipherStrategy cipher;
    private final FileProcessor processor;

    public OperationHandler(PathReader pathReader, KeyReader keyReader, CipherStrategy cipher, FileProcessor processor) {
        this.pathReader = pathReader;
        this.keyReader = keyReader;
        this.cipher = cipher;
        this.processor = processor;
    }

    public void handleOperation(CipherCommands command) {
        String action = (command == CipherCommands.ENCODE) ? "шифрования" : "расшифрования";
        System.out.println("выбран режим: " + action);
        Path inputPath = pathReader.readPath("введите путь к файлу: ", true);
        if (inputPath == null) {
            return;
        }
        Path outputPath = pathReader.readPath("введите путь для сохранения результата: ", false);
        if (outputPath == null) {
            return;
        }
        if (Files.exists(outputPath) && Files.isDirectory(outputPath)) {
            String defaultName = "result.txt";
            outputPath = outputPath.resolve(defaultName);
        }
        Integer key = keyReader.readKey("Введите ключ сдвига: ");
        if (key == null) {
            return;
        }
        System.out.println("Обработка...");
        processor.process(inputPath, outputPath, command, key, cipher);
        System.out.println("Успешно! Результат сохранён в: " + outputPath + "\n");
    }
}

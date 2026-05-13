package com.javarush.zolotarev.cipher;

public class CaesarCipher implements CipherStrategy {

    private static final String ALPHABET_UPPER = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    private static final String ALPHABET_LOWER = "абвгдежзийклмнопрстуфхцчшщъыьэюя";
    public static final int ALPHABET_SIZE = ALPHABET_UPPER.length();

    @Override
    public String process(String text, int key, CipherCommands command) {

        int shift = normalizeShift(key, command);

        char[] chars = text.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            char symbol = chars[i];

            if (symbol >= 'А' && symbol <= 'Я') {
                chars[i] = shiftChar(symbol, ALPHABET_UPPER, shift);
            } else if (symbol >= 'а' && symbol <= 'я') {
                chars[i] = shiftChar(symbol, ALPHABET_LOWER, shift);
            }
        }
        return new String(chars);
    }

    private int normalizeShift(int key, CipherCommands command) {
        int shift = (command == CipherCommands.ENCODE) ? key : -key;
        return ((shift % ALPHABET_SIZE) + ALPHABET_SIZE) % ALPHABET_SIZE;
    }

    private char shiftChar(char symbol, String alphabet, int shift) {
        int index = alphabet.indexOf(symbol);
        int newIndex = (index + shift) % alphabet.length();
        return alphabet.charAt(newIndex);
    }
}

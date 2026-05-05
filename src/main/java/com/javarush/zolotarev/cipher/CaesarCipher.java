package com.javarush.zolotarev.cipher;

public class CaesarCipher implements CipherStrategy {

    private static final String AlPHABET_UPPER = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    private static final String AlPHABET_LOWER = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    private static final int AlPHABET_SIZE = AlPHABET_UPPER.length();


    @Override
    public String process(String text, int key, CipherMode mode) {

        int shift = normalizeShift(key, mode, AlPHABET_SIZE);

        char[] chars = text.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            char symbol = chars[i];

            if (symbol >= 'А' && symbol <= 'Я') {
                chars[i] = shiftChar(symbol, AlPHABET_UPPER, shift);
            } else if (symbol >= 'а' && symbol <= 'я') {
                chars[i] = shiftChar(symbol, AlPHABET_LOWER, shift);
            }
        }
        return new String(chars);
    }

    private int normalizeShift(int key, CipherMode mode, int alphabetSize) {
        int shift = (mode == CipherMode.ENCODE) ? key : -key;
        return ((shift % alphabetSize) + alphabetSize) % alphabetSize;
    }

    private char shiftChar(char symbol, String alphabet, int shift) {
        int index = alphabet.indexOf(symbol);
        int newIndex = (index+ shift) % alphabet.length();
        return alphabet.charAt(newIndex);
    }
}

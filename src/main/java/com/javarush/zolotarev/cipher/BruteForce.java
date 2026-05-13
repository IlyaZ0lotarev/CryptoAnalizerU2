package com.javarush.zolotarev.cipher;

import java.util.*;

public class BruteForce {

    private final CaesarCipher cipher = new CaesarCipher();

    private static final Set<String> DICT_WORDS = Set.of("ты", "так", "как", "это", "кто", "или", "где", "быть", "он", "она", "уже", "что");

    public static class DecryptAttempt {
        private final int key;
        private final String text;
        private final int score;

        public DecryptAttempt(int key, String text, int score) {
            this.key = key;
            this.text = text;
            this.score = score;
        }

        public int getKey() {
            return key;
        }

        public String getText() {
            return text;
        }

        public int getScore() {
            return score;
        }
    }

    public List<DecryptAttempt> keySearch(String cipherText) {
        int size = CaesarCipher.ALPHABET_SIZE;
        List<DecryptAttempt> attempts = new ArrayList<>(size);

        for (int key = 0; key < size; key++) {
            String decrypted = cipher.process(cipherText, key, CipherCommands.DECODE);
            int score = countWordMatches(decrypted.toLowerCase(), DICT_WORDS);
            attempts.add(new DecryptAttempt(key, decrypted, score));
        }

        Collections.sort(attempts, (o1, o2) -> Integer.compare(o2.getScore(), o1.getScore()));
        return attempts;
    }

    private int countWordMatches(String lowerDecrypted, Set<String> dict) {
        int count = 0;
        String[] words = lowerDecrypted.split("\\P{L}+");

        for (String word : words) {
            if (!word.isEmpty() && dict.contains(word)) {
                count++;
            }
        }
        return count;
    }
}

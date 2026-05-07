package com.javarush.zolotarev.cipher;

public interface CipherStrategy {
  String process(String text, int key, CipherMode mode);


  default String encode(String text, int key) {
      return process(text, key, CipherMode.ENCODE);
  }

  default String decode(String text, int key) {
      return process(text, key, CipherMode.DECODE);
  }
}

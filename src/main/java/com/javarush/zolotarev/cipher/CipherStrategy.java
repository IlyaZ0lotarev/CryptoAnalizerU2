package com.javarush.zolotarev.cipher;

public interface CipherStrategy {
  String process(String text, int key, CipherCommands command);


  default String encode(String text, int key) {
      return process(text, key, CipherCommands.ENCODE);
  }

  default String decode(String text, int key) {
      return process(text, key, CipherCommands.DECODE);
  }
}

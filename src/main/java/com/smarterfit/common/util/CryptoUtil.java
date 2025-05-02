package com.smarterfit.common.util;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CryptoUtil {
   private static String ENCRYPTION_KEY;
   private static String ALGORITHM;

   @Autowired
   public CryptoUtil(@Value("${encryption.key}") String encryptionKey,
         @Value("${encryption.algorithm}") String algorithm) {
      ENCRYPTION_KEY = encryptionKey;
      ALGORITHM = algorithm;
   }

   public static String encrypt(String value) {
      try {
         SecretKeySpec key = new SecretKeySpec(ENCRYPTION_KEY.getBytes(), ALGORITHM);

         Cipher cipher = Cipher.getInstance(ALGORITHM);
         cipher.init(Cipher.ENCRYPT_MODE, key);

         byte[] encryptedBytes = cipher.doFinal(value.getBytes());

         return Base64.getEncoder().encodeToString(encryptedBytes);
      } catch (Exception e) {
         throw new RuntimeException("Encryption failed");
      }
   }

   public static String decrypt(String value) {
      try {
         SecretKeySpec key = new SecretKeySpec(ENCRYPTION_KEY.getBytes(), ALGORITHM);

         Cipher cipher = Cipher.getInstance(ALGORITHM);
         cipher.init(Cipher.DECRYPT_MODE, key);

         byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(value));

         return new String(decryptedBytes);
      } catch (Exception e) {
         throw new RuntimeException("Decryption failed");
      }
   }
}

package io.jotech.classicmodels.vm;

import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import org.apache.commons.codec.binary.Base64;
public class EncryptDecrypt {

  private static String myEncryptionKey = "INZUKHAAHKUZNIINZUKHA#2018";
  private static String myEncryptionScheme = "DESede";
  private static String UNICODE_FORMAT = "UTF8";

  public static String encrypt(String plain) {
    String encryptedString = null;
    try {
      Cipher cipher = Cipher.getInstance(myEncryptionScheme);
      cipher.init(Cipher.ENCRYPT_MODE, getKey());
      byte[] plainText = plain.getBytes(UNICODE_FORMAT);
      byte[] encryptedText = cipher.doFinal(plainText);
      encryptedString =
          new String(org.apache.commons.codec.binary.Base64.encodeBase64(encryptedText));
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
    return encryptedString;
  }

  public static String decrypt(String encryptedString) {
    String decryptedText = null;
    try {
      Cipher cipher = Cipher.getInstance(myEncryptionScheme);
      cipher.init(Cipher.DECRYPT_MODE, getKey());
      byte[] encryptedText = Base64.decodeBase64(encryptedString.getBytes());
      byte[] plainText = cipher.doFinal(encryptedText);
      decryptedText = new String(plainText);
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
    return decryptedText;
  }

  private static SecretKey getKey() throws Exception {
    byte[] arrayBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
    KeySpec ks = new DESedeKeySpec(arrayBytes);
    SecretKeyFactory skf = SecretKeyFactory.getInstance(myEncryptionScheme);
    Cipher cipher = Cipher.getInstance(myEncryptionScheme);
    return skf.generateSecret(ks);
  }

  public static void main(String[] args) {
    String decrypt = EncryptDecrypt.decrypt("KxiOWXYUTkb4HYlVWy3tug==");
    System.out.println(decrypt);
  }
}

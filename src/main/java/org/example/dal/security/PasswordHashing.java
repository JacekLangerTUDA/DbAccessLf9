package org.example.dal.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * (Is this class part of a design pattern or an implementation of a design pattern)
 * If that is the case, what pattern is in place?
 * Is this class a producer or consumer?
 * <p>
 * If the class is a utils class what kind of data or methods does it hold.
 * <p>
 * (What is PasswordHashing responsible for)
 * <a href="/path to doc">PasswordHashing.adoc</a>
 *
 * <pre> YOUR MINIMAL CODE EXAMPLE INCLUDING CONFIGURATION </pre>
 */
public class PasswordHashing {


  public static String hash(String password) throws NoSuchAlgorithmException {

    MessageDigest digest = MessageDigest.getInstance("SHA-512");
    return bytesToHex(digest.digest(password.getBytes(StandardCharsets.UTF_8)));
  }

  private static String bytesToHex(byte[] hash) {
    StringBuilder hexString = new StringBuilder(2 * hash.length);
    for (int i = 0; i < hash.length; i++) {
      String hex = Integer.toHexString(0xff & hash[i]);
      if(hex.length() == 1) {
        hexString.append('0');
      }
      hexString.append(hex);
    }
    return hexString.toString();
  }
}

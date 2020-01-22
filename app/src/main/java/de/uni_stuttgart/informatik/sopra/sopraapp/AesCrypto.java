package de.uni_stuttgart.informatik.sopra.sopraapp;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AesCrypto {
    private static SecretKeySpec secretKey;
    private static byte[] key;
    private static final String INSTANCESTRING = "AES/ECB/PKCS5Padding";

    /**
     * Sets the secret Key for the AesCrypto class and maybe catches an Expcetion.
     *
     * @param myKey
     */
    public static void setSecretKey(String myKey) {
        MessageDigest shaDiggest = null;
        try {
            key = myKey.getBytes("UTF-8");
            shaDiggest = MessageDigest.getInstance("SHA-1");
            key = shaDiggest.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The string gets encrypted by cipher with Aes/Ecp/PKCS5Padding method.
     * It will encrypt it first in a byte, to transform the byte in the returning array.
     *
     * @param stringToEncrypt
     * @param secret
     * @return
     */
    public static String encrypt(String stringToEncrypt, String secret) {
        Cipher cipher;
        try {
            setSecretKey(secret);
            cipher = Cipher.getInstance(INSTANCESTRING);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedByte = cipher.doFinal(stringToEncrypt.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(encryptedByte);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method decryts a string, first in a byte and transform this in a String.
     *
     * @param stringToDecrypt
     * @param secret
     * @return decryptedString or null(if there is no valid decryption)
     */
    public static String decrypt(String stringToDecrypt, String secret) {
        Cipher cipher;
        try {
            setSecretKey(secret);
            cipher = Cipher.getInstance(INSTANCESTRING);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedByte = cipher.doFinal(Base64.getDecoder().decode(stringToDecrypt));
            return new String(decryptedByte);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

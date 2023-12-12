package xyz.ronella.crypto.symmetric.generator;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

final public class SecretMgr {

    private static final String DEFAULT_ALGORITHM = "AES";
    private static final int DEFAULT_KEY_SIZE=256;

    private SecretMgr() {}

    public static SecretKey generateKey() throws NoSuchAlgorithmException {
        return generateKey(DEFAULT_ALGORITHM);
    }

    public static SecretKey generateKey(final String algorithm) throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance(algorithm);
        keyGen.init(DEFAULT_KEY_SIZE);
        return keyGen.generateKey();
    }

    public static String generateKeyAsString(final String algorithm) throws NoSuchAlgorithmException {
        final var secretKey = generateKey(algorithm);
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public static String generateKeyAsString() throws NoSuchAlgorithmException {
        final var secretKey = generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public static SecretKey restoreKey(final String algorithm, final String keyString) {
        byte[] keyBytes = Base64.getDecoder().decode(keyString);
        return new SecretKeySpec(keyBytes, algorithm);
    }

    public static SecretKey restoreAESKey(final String keyString) {
        return restoreKey(DEFAULT_ALGORITHM, keyString);
    }
}

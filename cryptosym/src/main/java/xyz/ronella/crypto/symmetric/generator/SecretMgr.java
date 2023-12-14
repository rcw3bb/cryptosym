package xyz.ronella.crypto.symmetric.generator;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * The class responsible for managing secret key.
 *
 * @author Ron Webb
 */
final public class SecretMgr {

    private static final String DEFAULT_ALGORITHM = "AES";
    private static final int DEFAULT_KEY_SIZE=256;

    private SecretMgr() {}

    /**
     * Generate a key based on the default algorithm (i.e. AES with 256 size).
     *
     * @return An instance of SecretKey.
     * @throws NoSuchAlgorithmException Throws NoSuchAlgorithmException.
     */
    public static SecretKey generateKey() throws NoSuchAlgorithmException {
        return generateKey(DEFAULT_ALGORITHM);
    }

    /**
     * Generate a key based on the algorithm provided.
     *
     * @param algorithm The algorithm to use.
     * @return An instance of SecretKey.
     * @throws NoSuchAlgorithmException Throws NoSuchAlgorithmException.
     */
    public static SecretKey generateKey(final String algorithm) throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance(algorithm);
        keyGen.init(DEFAULT_KEY_SIZE);
        return keyGen.generateKey();
    }

    /**
     * Generate a key based on the algorithm provided.
     *
     * @param algorithm The algorithm to use.
     * @return A string key.
     * @throws NoSuchAlgorithmException Throws NoSuchAlgorithmException.
     */
    public static String generateKeyAsString(final String algorithm) throws NoSuchAlgorithmException {
        final var secretKey = generateKey(algorithm);
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    /**
     * Generate a key based on the default algorithm (i.e. AES with 256 size).
     *
     * @return A string key.
     * @throws NoSuchAlgorithmException Throws NoSuchAlgorithmException.
     */
    public static String generateKeyAsString() throws NoSuchAlgorithmException {
        final var secretKey = generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    /**
     * Restore the string key to an instance of SecretKey.
     *
     * @param algorithm The algorithm to use.
     * @param keyString The string key.
     * @return An instance of SecretKey.
     */
    public static SecretKey restoreKey(final String algorithm, final String keyString) {
        byte[] keyBytes = Base64.getDecoder().decode(keyString);
        return new SecretKeySpec(keyBytes, algorithm);
    }

    /**
     * Restore the string key (i.e. generated with AES-256) to an instance of SecretKey.
     *
     * @param keyString The string key.
     * @return An instance of SecretKey.
     */
    public static SecretKey restoreKey(final String keyString) {
        return restoreKey(DEFAULT_ALGORITHM, keyString);
    }
}

package xyz.ronella.crypto.symmetric;

import xyz.ronella.crypto.symmetric.generator.SecretMgr;
import xyz.ronella.crypto.symmetric.util.KeyResolver;
import xyz.ronella.crypto.symmetric.util.impl.KeyChain;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;

/**
 * The main class the actually do encryption and decryption.
 *
 * @author Ron Webb
 */
public final class CryptoSym {
    private final static String DEFAULT_TRANSFORMATION = "AES/GCM/NoPadding";
    private final static int IV_SIZE_BYTE = 12; //The size of IV in byte size.
    private final static int IV_SIZE_BIT = 96; // The number of bits in IV_SIZE_BYTE.
    private final static int IV_SPLIT_PREFIX = 3; //The size of the IV that will become a prefix. The value must be 0 to 12 only.
    private final static int IV_SPLIT_SUFFIX = IV_SIZE_BYTE - IV_SPLIT_PREFIX; // The size of the IV that will become a suffix.

    private CryptoSym() {}

    private static String encrypt(final String key, final String plainText) throws CryptoSymException {
        try {
            Objects.requireNonNull(key, "key parameter cannot be null");
            Objects.requireNonNull(plainText, "plainText parameter cannot be null");

            final var cipher = Cipher.getInstance(DEFAULT_TRANSFORMATION);
            final var iv = generateRandomIV();
            final var parameterSpec = new GCMParameterSpec(IV_SIZE_BIT, iv);
            final var secretKey = SecretMgr.restoreKey(key);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);

            final var plaintext = plainText.getBytes(StandardCharsets.UTF_8);
            final var ciphertext = cipher.doFinal(plaintext);
            final var result = infuseWithIV(iv, ciphertext);

            return Base64.getEncoder().encodeToString(result);
        }
        catch (Exception exp) {
            throw new CryptoSymException(exp);
        }
    }

    private static byte[] infuseWithIV(byte[] iv, byte[] ciphertext) {
        // Split the IV into two parts
        final var firstPart = new byte[IV_SPLIT_PREFIX];
        final var secondPart = new byte[IV_SPLIT_SUFFIX];
        System.arraycopy(iv, 0, firstPart, 0, IV_SPLIT_PREFIX);
        System.arraycopy(iv, IV_SPLIT_PREFIX, secondPart, 0, IV_SPLIT_SUFFIX);

        // Prepend the first part and append the second part to the ciphertext
        byte[] result = new byte[ciphertext.length + firstPart.length + secondPart.length];
        System.arraycopy(firstPart, 0, result, 0, firstPart.length);
        System.arraycopy(ciphertext, 0, result, firstPart.length, ciphertext.length);
        System.arraycopy(secondPart, 0, result, firstPart.length + ciphertext.length, secondPart.length);

        return result;
    }

    /**
     * Does the encryption based on the key provided by an implementation of KeyResolver.
     *
     * @param keyResolver An implementation of KeyResolver.
     * @param text The text to encrypt.
     * @return The encrypted text.
     * @throws CryptoSymException Throws CryptoSymException
     */
    public static String encrypt(final KeyResolver keyResolver, final String text) throws CryptoSymException {
        return encrypt(keyResolver.resolve(), text);
    }

    /**
     * Does the encryption based on the key provided by the KeyChain implementation of KeyResolver.
     *
     * @param text The text to encrypt.
     * @return The encrypted text.
     * @throws CryptoSymException Throws CryptoSymException
     */
    public static String encrypt(final String text) throws CryptoSymException {
        return encrypt(new KeyChain(), text);
    }

    private static String decrypt(final String key, final String encryptedText) throws CryptoSymException {
        try {
            Objects.requireNonNull(key, "key parameter cannot be null");
            Objects.requireNonNull(encryptedText, "encryptedText parameter cannot be null");

            final var cipher = Cipher.getInstance(DEFAULT_TRANSFORMATION);
            final var cipherText = Base64.getDecoder().decode(encryptedText);
            final var iv = extractIV(cipherText);
            final var ciphertext = extractCiphertext(cipherText, iv.length);
            final var parameterSpec = new GCMParameterSpec(IV_SIZE_BIT, iv);
            final var secretKey = SecretMgr.restoreKey(key);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);

            final var decryptedBytes = cipher.doFinal(ciphertext);

            return new String(decryptedBytes, StandardCharsets.UTF_8);
        }
        catch (Exception exp) {
            throw new CryptoSymException(exp);
        }
    }

    /**
     * Does the decryption based on the key provided by an implementation of KeyResolver.
     *
     * @param keyResolver An implementation of KeyResolver.
     * @param encryptedText The encrypted text.
     * @return The plain text.
     * @throws CryptoSymException Throws CryptoSymException
     */
    public static String decrypt(final KeyResolver keyResolver, final String encryptedText) throws CryptoSymException {
        return decrypt(keyResolver.resolve(), encryptedText);
    }

    /**
     * Does the decryption based on the key provided by the KeyChain implementation of KeyResolver.
     *
     * @param encryptedText The encrypted text.
     * @return The plain text.
     * @throws CryptoSymException Throws CryptoSymException
     */
    public static String decrypt(final String encryptedText) throws CryptoSymException {
        return decrypt(new KeyChain(), encryptedText);
    }

    private static byte[] generateRandomIV() {
        final var random = new SecureRandom();
        final var iv = new byte[IV_SIZE_BYTE]; // GCM nonce size is 12 bytes

        random.nextBytes(iv);
        return iv;
    }

    private static byte[] extractCiphertext(byte[] encryptedData, int ivLength) {
        final var ciphertext = new byte[encryptedData.length - ivLength];

        // Extract the ciphertext from the combined array
        System.arraycopy(encryptedData, IV_SPLIT_PREFIX, ciphertext, 0, ciphertext.length);
        return ciphertext;
    }

    private static byte[] extractIV(byte[] encryptedData) {
        final var iv = new byte[IV_SIZE_BYTE];

        // Extract the IV from the combined array
        System.arraycopy(encryptedData, 0, iv, 0, IV_SPLIT_PREFIX);
        System.arraycopy(encryptedData, encryptedData.length - IV_SPLIT_SUFFIX, iv, IV_SPLIT_PREFIX, IV_SPLIT_SUFFIX);

        return iv;
    }
}

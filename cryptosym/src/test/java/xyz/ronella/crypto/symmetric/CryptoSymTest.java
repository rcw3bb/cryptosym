package xyz.ronella.crypto.symmetric;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import xyz.ronella.crypto.symmetric.generator.SecretMgr;
import xyz.ronella.crypto.symmetric.util.impl.StringAsKey;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CryptoSymTest {

    @Test
    public void encDecWithKeyParameter() throws NoSuchAlgorithmException {
        final var text = "TEST_ONLY";
        final var key = new StringAsKey(SecretMgr.generateKeyAsString());
        final var encryptedText = CryptoSym.encrypt(key, text);
        final var decryptedText = CryptoSym.decrypt(key, encryptedText);
        assertEquals(text, decryptedText);
    }

    @Test
    public void encWithNullKeyParameter() {
        final var text = "TEST_ONLY";
        assertThrows(RuntimeException.class, ()-> CryptoSym.encrypt(new StringAsKey(null), text));
    }

    @Test
    public void decWithNullKeyParameter() {
        final var text = "TEST_ONLY";
        assertThrows(RuntimeException.class, ()-> CryptoSym.decrypt(new StringAsKey(null), text));
    }

    @Test
    public void encWithNullTextParameter() {
        final var key = new StringAsKey("TEST_ONLY");
        assertThrows(RuntimeException.class, ()-> CryptoSym.encrypt(key, null));
    }

    @Test
    public void decWithNullTextParameter() {
        final var key = new StringAsKey("TEST_ONLY");
        assertThrows(RuntimeException.class, ()-> CryptoSym.decrypt(key, null));
    }

    @Test
    public void encDecProperty() throws NoSuchAlgorithmException {
        final var text = "TEST_ONLY";
        final var key = SecretMgr.generateKeyAsString();
        System.setProperty("cryptosym.key", key);
        final var encryptedText = CryptoSym.encrypt(text);
        final var decryptedText = CryptoSym.decrypt(encryptedText);
        assertEquals(text, decryptedText);
    }

    @Test
    @Disabled //Enable this test if you've set CRYPTOSYM_KEY environment variable with a proper key.
    public void encDecEnv() {
        final var text = "TEST_ONLY";
        System.out.println("CRYPTOSYM_KEY: " + System.getenv("CRYPTOSYM_KEY"));
        final var encryptedText = CryptoSym.encrypt(text);
        final var decryptedText = CryptoSym.decrypt(encryptedText);
        assertEquals(text, decryptedText);
    }

    @Test
    @Disabled //Enable this test if you've set DUMMY_KEY environment variable with a proper key.
    public void encDecKeyHolder() {
        final var text = "TEST_ONLY";
        System.setProperty("cryptosym.keyholder", "DUMMY_KEY");
        final var encryptedText = CryptoSym.encrypt(text);
        final var decryptedText = CryptoSym.decrypt(encryptedText);
        assertEquals(text, decryptedText);
    }
}

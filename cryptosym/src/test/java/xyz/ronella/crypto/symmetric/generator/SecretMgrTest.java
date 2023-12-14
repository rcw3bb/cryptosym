package xyz.ronella.crypto.symmetric.generator;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

public class SecretMgrTest {

    @Test
    public void testGenDefaultAsString() throws NoSuchAlgorithmException {
        assertFalse(SecretMgr.generateKeyAsString().isEmpty());
    }

    @Test
    public void testGenKeyAsString() throws NoSuchAlgorithmException {
        assertFalse(SecretMgr.generateKeyAsString("AES").isEmpty());
    }

    @Test
    public void testRestoreDefault() throws NoSuchAlgorithmException {
        final var keyString = SecretMgr.generateKeyAsString();
        final var secretKey =  SecretMgr.restoreKey(keyString);
        final var fromSecretKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        assertEquals(keyString, fromSecretKey);
    }

    @Test
    public void testRestoreKey() throws NoSuchAlgorithmException {
        final var keyString = SecretMgr.generateKeyAsString("AES");
        final var secretKey =  SecretMgr.restoreKey("AES", keyString);
        final var fromSecretKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        assertEquals(keyString, fromSecretKey);
    }

}

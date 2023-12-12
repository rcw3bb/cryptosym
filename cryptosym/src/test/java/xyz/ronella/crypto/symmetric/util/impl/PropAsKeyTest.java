package xyz.ronella.crypto.symmetric.util.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PropAsKeyTest {

    @Test
    public void getExistingEnv() {
        final var existingProp = "___EXISTING___";
        System.setProperty(existingProp, "VALUE");
        final var resolver = new PropAsKey(existingProp);
        final var value = resolver.resolve();
        assertNotNull(value);
    }

    @Test
    public void getNonExistingEnv() {
        final var resolver = new PropAsKey("___NON_EXISTING___");
        final var value = resolver.resolve();
        assertNull(value);
    }
}

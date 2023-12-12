package xyz.ronella.crypto.symmetric.util.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class EnvAsKeyByPropTest {

    @Test
    public void getExistingEnv() {
        final var existingProp = "___EXISTING___";
        System.setProperty(existingProp, "PATH");

        final var resolver = new EnvAsKeyByProp(existingProp);
        final var value = resolver.resolve();

        assertNotNull(value);
    }

    @Test
    public void getNonExistingEnv() {
        final var resolver = new EnvAsKeyByProp("___NON_EXISTING___");
        final var value = resolver.resolve();
        assertNull(value);
    }
}

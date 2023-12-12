package xyz.ronella.crypto.symmetric.util.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class EnvAsKeyTest {

    @Test
    public void nullKeyHolder() {
        final var resolver = new EnvAsKey(null);
        final var value = resolver.resolve();
        assertNull(value);
    }

    @Test
    public void getExistingEnv() {
        final var resolver = new EnvAsKey("PATH");
        final var value = resolver.resolve();
        assertNotNull(value);
    }

    @Test
    public void getNonExistingEnv() {
        final var resolver = new EnvAsKey("___NON_EXISTING___");
        final var value = resolver.resolve();
        assertNull(value);
    }

}

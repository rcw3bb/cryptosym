package xyz.ronella.crypto.symmetric.util.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StringAsKeyTest {
    @Test
    public void getStringKey() {
        final var strKey = "___KEY___";
        final var resolver = new StringAsKey(strKey);
        final var value = resolver.resolve();
        assertEquals(strKey, value);
    }

    @Test
    public void getNullStringKey() {
        final var resolver = new StringAsKey(null);
        final var value = resolver.resolve();
        assertNull(value);
    }
}

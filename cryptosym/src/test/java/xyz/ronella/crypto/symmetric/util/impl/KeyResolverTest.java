package xyz.ronella.crypto.symmetric.util.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class KeyResolverTest {

    @Test
    public void testResolveChainExistingProp() {
        final var existingProp = "___EXISTING___";
        System.setProperty(existingProp, "VALUE");

        final var byEnv = new EnvAsKey("___NON_EXISTING___")
                .chainTo(new PropAsKey(existingProp));

        assertInstanceOf(EnvAsKey.class, byEnv.getTop());
        assertEquals("VALUE", byEnv.getTop().resolve());
    }

    @Test
    public void testResolveChainNonExistingProp() {
        final var byEnv = new EnvAsKey("___NON_EXISTING___");
        final var byProp = new PropAsKey("___NON_EXISTING___");

        byEnv.chainTo(byProp);

        assertNull(byEnv.resolve());
    }
}

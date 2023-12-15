package xyz.ronella.crypto.symmetric.util.impl;

import java.util.Optional;

/**
 * An implementation of KeyResolver that treats the acquired key as the actual key.
 *
 * @author Ron Webb
 */
public class StringAsKey extends AbstractKeyChain {

    final private String key;

    /**
     * Creates an instance of StringAsKey.
     * @param key The actual string key.
     */
    public StringAsKey(String key) {
        super(null);
        this.key=key;
    }

    @Override
    public String resolve() {
        final var toSuccessor = Optional.ofNullable(keyChain)
                .orElse(new NullKeyResolver());

        return Optional.ofNullable(key).orElseGet(toSuccessor::resolve);
    }
}

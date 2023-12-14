package xyz.ronella.crypto.symmetric.util.impl;

import java.util.Optional;

/**
 * An implementation of KeyResolver that can resolve an environment variable as a key.
 *
 * @author Ron Webb
 */
public class EnvAsKey extends AbstractKeyChain {
    public EnvAsKey(String keyHolder) {
        super(keyHolder);
    }

    @Override
    public String resolve() {
        final var toSuccessor = Optional.ofNullable(keyChain)
                .orElse(new NullKeyResolver());

        return Optional.ofNullable(System.getenv(keyHolder))
                .orElseGet(toSuccessor::resolve);
    }
}

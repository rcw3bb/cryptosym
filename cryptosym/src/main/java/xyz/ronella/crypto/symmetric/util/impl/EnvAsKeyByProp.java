package xyz.ronella.crypto.symmetric.util.impl;

import java.util.Optional;

/**
 * An implementation of KeyResolver that can use a property to identify the environment variable that holds the key.
 *
 * @author Ron Webb
 */

public class EnvAsKeyByProp extends AbstractKeyChain {

    /**
     * Creates an instance of EnvAsKeyByProp.
     * @param keyHolder The name of the system property holding the environment variable holding the key.
     */
    public EnvAsKeyByProp(String keyHolder) {
        super(keyHolder);
    }

    @Override
    public String resolve() {
        final var toSuccessor = Optional.ofNullable(keyChain)
                .orElse(new NullKeyResolver());

        final var targetEnv = new PropAsKey(keyHolder).resolve();
        final var envValue = new EnvAsKey(targetEnv).resolve();

        return Optional.ofNullable(envValue).orElseGet(toSuccessor::resolve);
    }
}

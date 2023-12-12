package xyz.ronella.crypto.symmetric.util.impl;

import java.util.Optional;

public class EnvAsKeyByProp extends AbstractKeyChain {
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

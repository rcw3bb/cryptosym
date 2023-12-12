package xyz.ronella.crypto.symmetric.util.impl;

import java.util.Optional;

public class PropAsKey extends AbstractKeyChain {
    public PropAsKey(String keyHolder) {
        super(keyHolder);
    }

    @Override
    public String resolve() {
        final var toSuccessor = Optional.ofNullable(keyChain)
                .orElse(new NullKeyResolver());

        return Optional.ofNullable(System.getProperty(keyHolder))
                .orElseGet(toSuccessor::resolve);
    }
}

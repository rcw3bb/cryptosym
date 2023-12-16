package xyz.ronella.crypto.symmetric.util.impl;

import java.util.Optional;

/**
 * An implementation of KeyResolver that resolves the property to a key.
 *
 * @author Ron Webb
 */
public class PropAsKey extends AbstractKeyChain {

    /**
     * Creates an instance of PropAsKey.
     * @param keyHolder The name of the system property that is holding the key.
     */
    public PropAsKey(String keyHolder) {
        super(keyHolder);
    }

    @Override
    public String resolve() {
        return Optional.ofNullable(System.getProperty(keyHolder))
                .orElseGet(successorLogic());
    }
}

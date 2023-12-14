package xyz.ronella.crypto.symmetric.util.impl;

/**
 * A null object implementation of KeyResolver.
 */
public class NullKeyResolver extends AbstractKeyChain {
    public NullKeyResolver() {
        super(null);
    }

    @Override
    public String resolve() {
        return null;
    }
}

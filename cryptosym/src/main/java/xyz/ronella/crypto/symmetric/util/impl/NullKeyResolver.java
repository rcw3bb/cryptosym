package xyz.ronella.crypto.symmetric.util.impl;

/**
 * A null object implementation of KeyResolver.
 */
public class NullKeyResolver extends AbstractKeyChain {

    /**
     * Creates an instance of NullKeyResolver. This is the null object implementation of KeyResolver.
     */
    public NullKeyResolver() {
        super(null);
    }

    @Override
    public String resolve() {
        return null;
    }
}

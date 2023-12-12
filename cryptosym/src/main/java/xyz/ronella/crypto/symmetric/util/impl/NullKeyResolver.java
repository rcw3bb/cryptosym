package xyz.ronella.crypto.symmetric.util.impl;

public class NullKeyResolver extends AbstractKeyChain {
    public NullKeyResolver() {
        super(null);
    }

    @Override
    public String resolve() {
        return null;
    }
}

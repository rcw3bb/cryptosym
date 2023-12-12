package xyz.ronella.crypto.symmetric.util.impl;

public class StringAsKey extends AbstractKeyChain {
    public StringAsKey(String key) {
        super(key);
    }

    @Override
    public String resolve() {
        return keyHolder;
    }
}

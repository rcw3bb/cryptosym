package xyz.ronella.crypto.symmetric.util.impl;

import xyz.ronella.crypto.symmetric.util.KeyResolver;

import java.util.Optional;

public abstract class AbstractKeyChain implements KeyResolver {

    protected AbstractKeyChain top;

    protected AbstractKeyChain keyChain;

    protected final String keyHolder;
    public AbstractKeyChain(final String keyHolder) {
        this.keyHolder = Optional.ofNullable(keyHolder).orElse("___CRYPTOSYM_NULL_KEY_HOLDER___");
    }

    public AbstractKeyChain chainTo(final AbstractKeyChain successor) {
        this.keyChain = successor;
        Optional.ofNullable(top).ifPresentOrElse(___top -> successor.top = top, ()-> top = this);
        successor.top=top;
        return this.keyChain;
    }

    public KeyResolver getTop() {
        return top;
    }
}

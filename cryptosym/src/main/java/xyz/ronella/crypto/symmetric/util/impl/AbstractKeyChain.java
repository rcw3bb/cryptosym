package xyz.ronella.crypto.symmetric.util.impl;

import xyz.ronella.crypto.symmetric.util.KeyResolver;

import java.util.Optional;

/**
 * A partial implementation of KeyResolver that suppport chaining.
 *
 * @author Ron Webb
 */
public abstract class AbstractKeyChain implements KeyResolver {

    /**
     * Holds the top of the chain.
     */
    protected AbstractKeyChain top;

    /**
     * The chained AbstractKeyChain to the current instance.
     */
    protected AbstractKeyChain keyChain;

    /**
     * The keyHolder that will be converted to string key.
     */
    protected final String keyHolder;

    /**
     * The base constructor that must be used by all the child classes.
     * @param keyHolder The keyHolder to convert to a string key.
     */
    public AbstractKeyChain(final String keyHolder) {
        this.keyHolder = Optional.ofNullable(keyHolder).orElse("___CRYPTOSYM_NULL_KEY_HOLDER___");
    }

    /**
     * Chain the current instance to another AbstractKeyChain.
     * @param successor An instance of AbstractKeyChain chained to the current instance.
     * @return The instance of successor.
     */
    public AbstractKeyChain chainTo(final AbstractKeyChain successor) {
        this.keyChain = successor;
        Optional.ofNullable(top).ifPresentOrElse(___top -> successor.top = top, ()-> top = this);
        successor.top=top;
        return this.keyChain;
    }

    /**
     * The top of the chain.
     * @return An instance of KeyResolver.
     */
    public KeyResolver getTop() {
        return top;
    }
}

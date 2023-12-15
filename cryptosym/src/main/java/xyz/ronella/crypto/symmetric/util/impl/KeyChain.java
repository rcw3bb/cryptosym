package xyz.ronella.crypto.symmetric.util.impl;

import xyz.ronella.crypto.symmetric.util.KeyResolver;

/**
 * An implementation of KeyResolver that seek the key with the following sequence:
 * cryptosym.key property -> cryptosym.keyholder property -> CRYPTOSYM_KEY environment variable
 *
 * @author Ron Webb
 */
public class KeyChain implements KeyResolver {

    private final AbstractKeyChain chain;

    /**
     * Creates an instance of KeyChain. This resolves the key using the following sequence:
     * cryptosym.key property -> cryptosym.keyholder property -> CRYPTOSYM_KEY environment variable
     */
    public KeyChain() {
        chain = new PropAsKey("cryptosym.key")
                .chainTo(new EnvAsKeyByProp("cryptosym.keyholder"))
                .chainTo(new EnvAsKey("CRYPTOSYM_KEY"));
    }

    @Override
    public String resolve() {
        return chain.getTop().resolve();
    }
}

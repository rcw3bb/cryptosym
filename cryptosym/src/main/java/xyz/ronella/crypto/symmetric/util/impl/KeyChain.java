package xyz.ronella.crypto.symmetric.util.impl;

import xyz.ronella.crypto.symmetric.util.KeyResolver;

public class KeyChain implements KeyResolver {

    private final AbstractKeyChain chain;

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

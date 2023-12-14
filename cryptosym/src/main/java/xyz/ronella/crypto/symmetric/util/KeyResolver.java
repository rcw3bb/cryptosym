package xyz.ronella.crypto.symmetric.util;

/**
 * The blueprint for creating an implementation that returns a string key.
 *
 * @author Ron Web
 */
public interface KeyResolver {

    /**
     * Must have the implementation to resolve the string key.
     * @return The string key.
     */
    String resolve();
}

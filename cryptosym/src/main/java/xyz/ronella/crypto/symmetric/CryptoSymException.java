package xyz.ronella.crypto.symmetric;

/**
 * The base exception that the CryptoSym can throw.
 *
 * @author Ron Webb
 */
public class CryptoSymException extends Exception {

    /**
     * Creates an instance of CryptoSymException.
     * @param throwable The exception capture.
     */
    public CryptoSymException(final Throwable throwable) {
        super(throwable);
    }

    /**
     * Creates an instance of CryptoSymException.
     * @param message The error message.
     */
    public CryptoSymException(final String message) {
        super(message);
    }

}

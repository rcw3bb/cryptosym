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

}

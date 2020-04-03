package eu.okaeri.bdistribution.impl;

public class ShuffleIterationLimitException extends RuntimeException {

    public ShuffleIterationLimitException() {
    }

    public ShuffleIterationLimitException(String message) {
        super(message);
    }

    public ShuffleIterationLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShuffleIterationLimitException(Throwable cause) {
        super(cause);
    }

    public ShuffleIterationLimitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

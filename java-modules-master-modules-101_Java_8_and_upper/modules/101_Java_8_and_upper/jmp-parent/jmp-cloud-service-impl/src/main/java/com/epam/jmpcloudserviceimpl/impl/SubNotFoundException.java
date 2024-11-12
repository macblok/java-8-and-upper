package com.epam.jmpcloudserviceimpl.impl;

/**
 * Exception to be thrown when a subscription cannot be found.
 */
public class SubNotFoundException extends RuntimeException {

    /**
     * Constructs a new SubscriptionNotFoundException with the specified detail message.
     *
     * @param message the detail message.
     */
    public SubNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new SubscriptionNotFoundException with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause the cause of the exception.
     */
    public SubNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new SubscriptionNotFoundException with the specified cause.
     *
     * @param cause the cause of the exception.
     */
    public SubNotFoundException(Throwable cause) {
        super(cause);
    }
}

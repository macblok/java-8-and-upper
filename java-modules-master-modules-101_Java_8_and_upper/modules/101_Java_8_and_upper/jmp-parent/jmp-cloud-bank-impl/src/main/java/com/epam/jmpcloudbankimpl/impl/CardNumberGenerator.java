package com.epam.jmpcloudbankimpl.impl;

import java.util.concurrent.atomic.AtomicLong;

class CardNumberGenerator {
    private static final AtomicLong cardNumberCounter = new AtomicLong(1111111111111111L);
    private static final String CARD_NUMBER_FORMAT = "%04d-%04d-%04d-%04d";

    /**
     * Generates a globally unique bank card number using an atomic counter.
     * The method increments the counter each time it is invoked to ensure that every card number generated is distinct.
     * The card number is formatted according to a pre-defined format: {4 digits}-{4 digits}-{4 digits}-{4 digits}.
     * The initial value of the counter is set to make the first generated number "1111-1111-1111-1111".
     *
     * @return A formatted bank card number as a String.
     */
    String generateCardNumber() {
        var currentNumber = cardNumberCounter.getAndIncrement();
        return formatCardNumber(currentNumber);
    }

    private String formatCardNumber(long cardNumber) {
        var part1 = (cardNumber / 1000000000000L) % 10000;
        var part2 = (cardNumber / 100000000L) % 10000;
        var part3 = (cardNumber / 10000L) % 10000;
        var part4 = cardNumber % 10000;

        return String.format(CARD_NUMBER_FORMAT, part1, part2, part3, part4);
    }

}

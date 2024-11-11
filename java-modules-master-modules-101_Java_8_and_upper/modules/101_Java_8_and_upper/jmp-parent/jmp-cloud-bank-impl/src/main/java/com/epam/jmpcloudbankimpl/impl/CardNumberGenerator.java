package com.epam.jmpcloudbankimpl.impl;

import java.util.concurrent.atomic.AtomicLong;

class CardNumberGenerator {
    private static final AtomicLong cardNumberCounter = new AtomicLong(1111111111111111L);
    private static final String CARD_NUMBER_FORMAT = "%04d-%04d-%04d-%04d";

    String generateCardNumber() {
        long currentNumber = cardNumberCounter.getAndIncrement();
        return formatCardNumber(currentNumber);
    }

    private String formatCardNumber(long cardNumber) {
        long part1 = (cardNumber / 1000000000000L) % 10000;
        long part2 = (cardNumber / 100000000L) % 10000;
        long part3 = (cardNumber / 10000L) % 10000;
        long part4 = cardNumber % 10000;

        return String.format(CARD_NUMBER_FORMAT, part1, part2, part3, part4);
    }

}

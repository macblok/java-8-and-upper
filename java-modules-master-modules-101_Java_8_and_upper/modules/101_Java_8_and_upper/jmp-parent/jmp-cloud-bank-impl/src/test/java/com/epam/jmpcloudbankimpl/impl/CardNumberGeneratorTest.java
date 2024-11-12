package com.epam.jmpcloudbankimpl.impl;

import junit.framework.TestCase;

public class CardNumberGeneratorTest extends TestCase {

    private CardNumberGenerator cardNumberGenerator;

    protected void setUp() {
        cardNumberGenerator = new CardNumberGenerator();
    }

    public void testGenerateCardNumberUnique() {
        String firstCardNumber = cardNumberGenerator.generateCardNumber();
        String secondCardNumber = cardNumberGenerator.generateCardNumber();
        assertNotNull("First card number should not be null", firstCardNumber);
        assertNotNull("Second card number should not be null", secondCardNumber);
        assertFalse("Card numbers should be unique", firstCardNumber.equals(secondCardNumber));
    }

    public void testGenerateCardNumberFormat() {
        String cardNumber = cardNumberGenerator.generateCardNumber();
        assertNotNull("Card number should not be null", cardNumber);
        assertTrue("Card number should match the required format",
                cardNumber.matches("\\d{4}-\\d{4}-\\d{4}-\\d{4}"));
    }

    public void testGenerateCardNumberSequentialIncrement() {
        long firstNumber = Long.parseLong(cardNumberGenerator.generateCardNumber().replace("-", ""));
        long secondNumber = Long.parseLong(cardNumberGenerator.generateCardNumber().replace("-", ""));
        assertEquals("The second number should be exactly one greater than the first number",
                firstNumber + 1, secondNumber);
    }
}

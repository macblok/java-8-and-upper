package com.epam.jmpcloudbankimpl.impl;

import com.epam.jmpdto.dto.*;
import junit.framework.TestCase;

import java.time.LocalDate;

public class BankImplTest extends TestCase {

    private BankImpl bank;
    private User user;

    protected void setUp() {
        bank = new BankImpl();
        user = new User("George", "Tester", LocalDate.of(1987, 5, 16));
    }

    public void testShouldCreateCreditBankCardWithValidInputs() {
        BankCard card = bank.createBankCard(user, BankCardType.CREDIT);
        assertNotNull("The bank card should not be null", card);
        assertTrue("The card should be an instance of CreditBankCard", card instanceof CreditBankCard);
    }

    public void testShouldCreateDebitBankCardWithValidInputs() {
        BankCard card = bank.createBankCard(user, BankCardType.DEBIT);
        assertNotNull("The bank card should not be null", card);
        assertTrue("The card should be an instance of CreditBankCard", card instanceof DebitBankCard);
    }

    public void testCreateBankCardWithNullUser() {
        try {
            bank.createBankCard(null, BankCardType.CREDIT);
            fail("Expected an IllegalArgumentException to be thrown when the user is null");
        } catch (IllegalArgumentException e) {
            assertEquals("User and card type must not be null", e.getMessage());
        }
    }

    public void testCreateBankCardWithNullCardType() {
        try {
            bank.createBankCard(user, null);
            fail("Expected an IllegalArgumentException to be thrown when the card type is null");
        } catch (IllegalArgumentException e) {
            assertEquals("User and card type must not be null", e.getMessage());
        }
    }

    public void testCreateBankCardWithNullUserAndCardType() {
        try {
            bank.createBankCard(null, null);
            fail("Expected an IllegalArgumentException to be thrown when both user and card type are null");
        } catch (IllegalArgumentException e) {
            assertEquals("User and card type must not be null", e.getMessage());
        }
    }

}
package com.epam.jmpcloudbankimpl.impl;

import com.epam.jmpbankapi.service.BankCardCreator;
import com.epam.jmpdto.dto.*;
import com.epam.jmpbankapi.service.Bank;

import java.util.EnumMap;

public class BankImpl implements Bank {

    private final EnumMap<BankCardType, BankCardCreator> cardCreators = new EnumMap<>(BankCardType.class);
    private final CardNumberGenerator cardNumberGenerator = new CardNumberGenerator();

    public BankImpl() {
          cardCreators.put(BankCardType.CREDIT, this::createCreditBankCard);
          cardCreators.put(BankCardType.DEBIT, this::createDebitBankCard);
    }

    /**
     * Creates a bank card of the specified type for the given user.
     *
     * @param user the user who will own the bank card
     * @param cardType the type of bank card to create
     * @return a new instance of a BankCard
     * @throws IllegalArgumentException if user or cardType is null
     */
    @Override
    public BankCard createBankCard(User user, BankCardType cardType) {

        if (user == null || cardType == null) {
            throw new IllegalArgumentException("User and card type must not be null");
        }

        var cardCreator = cardCreators.get(cardType);

        if (cardCreator == null) {
            throw new IllegalArgumentException("Unsupported card type: " + cardType);
        }

        return cardCreator.create(user);
    }

    private BankCard createCreditBankCard(User user) {
        return new CreditBankCard(generateCardNumber(), user, BankCardType.INITIAL_BALANCE);
    }

    private BankCard createDebitBankCard(User user) {
        return new DebitBankCard(generateCardNumber(), user, BankCardType.INITIAL_BALANCE);
    }

    private String generateCardNumber() {
        return cardNumberGenerator.generateCardNumber();
    }
}

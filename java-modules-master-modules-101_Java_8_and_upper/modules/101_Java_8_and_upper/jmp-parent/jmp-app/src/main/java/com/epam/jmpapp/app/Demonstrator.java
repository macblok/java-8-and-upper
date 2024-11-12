package com.epam.jmpapp.app;

import com.epam.jmpdto.dto.BankCard;

import java.util.List;

public interface Demonstrator {

    List<BankCard> demonstrateCreateBankCard(int numberOfUsers);

    void demonstrateSubscriber(List<BankCard> bankCards);

    void demonstrateGetSubscriptionByBankCardNumber(String cardNumber);

    void demonstrateGetAllUsers();
}

package com.epam.api.service;

import com.epam.dto.dto.BankCard;
import com.epam.dto.dto.Subscription;
import com.epam.dto.dto.User;

import java.util.List;
import java.util.Optional;

public interface Service {

    void subscribe(BankCard bankCard);

    Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber);

    List<User> getAllUsers();
}

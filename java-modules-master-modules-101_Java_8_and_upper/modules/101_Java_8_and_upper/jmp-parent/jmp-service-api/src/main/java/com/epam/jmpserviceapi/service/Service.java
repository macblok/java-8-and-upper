package com.epam.jmpserviceapi.service;

import com.epam.jmpdto.dto.BankCard;
import com.epam.jmpdto.dto.Subscription;
import com.epam.jmpdto.dto.User;

import java.util.List;
import java.util.Optional;

public interface Service {

    void subscribe(BankCard bankCard);

    Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber);

    List<User> getAllUsers();
}

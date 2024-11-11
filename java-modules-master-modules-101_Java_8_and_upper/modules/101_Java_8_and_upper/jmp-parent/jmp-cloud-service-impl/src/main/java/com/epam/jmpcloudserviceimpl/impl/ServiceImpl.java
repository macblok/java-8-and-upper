package com.epam.jmpcloudserviceimpl.impl;

import com.epam.jmpdto.dto.BankCard;
import com.epam.jmpdto.dto.Subscription;
import com.epam.jmpdto.dto.User;
import com.epam.jmpserviceapi.service.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


public class ServiceImpl implements Service {

    //Simulating database with HashMap
    private final Map<String, Subscription> subscriptionDatabase = new HashMap<String, Subscription>();

    @Override
    public void subscribe(BankCard bankCard) {
        if (bankCard == null) {
            throw new IllegalArgumentException("Bank card must not be null");
        }

        if (subscriptionDatabase.containsKey(bankCard.getNumber())) {
            throw new IllegalStateException("Subscription already exists for this card number!");
        }

        var subscription = new Subscription(bankCard, LocalDate.now());

        subscriptionDatabase.put(bankCard.getNumber(), subscription);
    }

    @Override
    public Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.trim().isEmpty() || cardNumber.length() != 19) {
            throw new IllegalArgumentException("Card number cannot be null, empty or invalid.");
        }

        var foundSubscription = subscriptionDatabase.get(cardNumber);

        return Optional.ofNullable(foundSubscription);
    }

    @Override
    public List<User> getAllUsers() {
        return subscriptionDatabase.values().stream().
                map(Subscription::getCardUser).filter(Objects::nonNull).distinct().collect(Collectors.toUnmodifiableList());
    }
}

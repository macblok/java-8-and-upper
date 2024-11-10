package com.epam.impl.impl;

import com.epam.dto.dto.BankCard;
import com.epam.dto.dto.Subscription;
import com.epam.dto.dto.User;
import com.epam.api.service.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


public class ServiceImpl implements Service {
    @Override
    public void subscribe(BankCard bankCard) {

    }

    @Override
    public Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber) {
        return Optional.empty();
    }

    @Override
    public List<User> getAllUsers() {
        return Collections.EMPTY_LIST;
    }
}

package com.epam.jmpcloudserviceimpl.impl;

import com.epam.jmpdto.dto.BankCard;
import com.epam.jmpdto.dto.Subscription;
import com.epam.jmpdto.dto.User;
import com.epam.jmpserviceapi.service.Service;

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

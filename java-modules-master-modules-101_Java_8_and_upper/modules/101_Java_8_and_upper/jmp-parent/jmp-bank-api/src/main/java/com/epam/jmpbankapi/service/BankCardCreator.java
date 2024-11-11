package com.epam.jmpbankapi.service;

import com.epam.jmpdto.dto.BankCard;
import com.epam.jmpdto.dto.User;

@FunctionalInterface
public interface BankCardCreator {

    BankCard create (User user);
}

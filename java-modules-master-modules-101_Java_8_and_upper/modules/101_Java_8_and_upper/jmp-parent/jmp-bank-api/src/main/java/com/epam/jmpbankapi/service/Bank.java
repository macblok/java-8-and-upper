package com.epam.jmpbankapi.service;

import com.epam.jmpdto.dto.BankCard;
import com.epam.jmpdto.dto.BankCardType;
import com.epam.jmpdto.dto.User;

public interface Bank {
    BankCard createBankCard(User user, BankCardType cardType);
}

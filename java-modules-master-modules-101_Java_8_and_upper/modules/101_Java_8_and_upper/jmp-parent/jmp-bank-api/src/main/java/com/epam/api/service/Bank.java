package com.epam.api.service;

import com.epam.dto.dto.BankCard;
import com.epam.dto.dto.BankCardType;
import com.epam.dto.dto.User;

public interface Bank {
    BankCard createBankCard(User user, BankCardType cardType);
}

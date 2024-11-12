package com.epam.jmpapp.app;

import com.epam.jmpcloudbankimpl.impl.BankImpl;
import com.epam.jmpcloudserviceimpl.impl.ServiceImpl;
import com.epam.jmpdto.dto.*;
import com.epam.jmpserviceapi.service.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DemoDriver implements Demonstrator {

    BankImpl bank = new BankImpl();
    ServiceImpl service = new ServiceImpl();

    @Override
    public List<BankCard> demonstrateCreateBankCard(int numberOfUsers) {
        var bankCards = IntStream.rangeClosed(1, numberOfUsers).mapToObj(i -> bank.createBankCard(
                new User("User" + i, "Surname" + i, getRandomDate()), getRandomBankCardType())
        ).collect(Collectors.toUnmodifiableList());

        bankCards.forEach(bc -> {
            if (bc instanceof DebitBankCard) ((DebitBankCard) bc).setBalance(getRandomDouble());
            else ((CreditBankCard) bc).setCreditLimit(getRandomDouble());
        });

        bankCards.forEach(c -> System.out.println("Created bank card:" + c));

        return bankCards;
    }

    @Override
    public void demonstrateSubscriber(List<BankCard> bankCards) {
        bankCards.forEach(bc -> {
            service.subscribe(bc);
            System.out.println("Subscribed " + bc);
        });
    }

    public void demonstrateGetSubscriptionByBankCardNumber(String cardNumber) {
        var retrievedSubscription = service.getSubscriptionByBankCardNumber(cardNumber);
        if (retrievedSubscription.isPresent()) {
            System.out.println("Retrieved subscription: " + retrievedSubscription.get());
        } else {
            System.out.println("No subscription retrieved.");
        }
    }

    public void demonstrateGetAllUsers() {
        System.out.println("Retrieved users:");
        service.getAllUsers().forEach(System.out::println);
    }

    public void demonstrateGetAverageUsersAge() {
        System.out.println("Average users age is: " + service.getAverageUsersAge());
    }

    public void demonstrateIsPayableUser(User user) {
        System.out.println("User: " + user + " is payable: " + Service.isPayableUser(user));
    }

    public void demonstrateGetAllSubscriptionsByCondition() {
        service.getAllSubscriptionsByCondition(s -> Service.isPayableUser(s.getCardUser()))
                .forEach(rs -> System.out.println("Retrieved subscription by condition: " + rs));
    }

    private LocalDate getRandomDate() {
        var minDay = LocalDate.of(1924, 1, 1).toEpochDay();
        var maxDay = LocalDate.of(2014, 12, 31).toEpochDay();
        var randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay);
    }

    private BankCardType getRandomBankCardType() {
        var cardTypes = BankCardType.values();
        int randomIndex = ThreadLocalRandom.current().nextInt(cardTypes.length);
        return cardTypes[randomIndex];
    }

    private double getRandomDouble() {
        var min = 1.0;
        var max = 10000.0;

        var randomDouble = ThreadLocalRandom.current().nextDouble(min, max);

        return BigDecimal.valueOf(randomDouble).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}

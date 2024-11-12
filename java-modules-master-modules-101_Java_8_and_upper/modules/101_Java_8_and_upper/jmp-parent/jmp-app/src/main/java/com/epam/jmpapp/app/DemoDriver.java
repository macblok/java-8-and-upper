package com.epam.jmpapp.app;

import com.epam.jmpcloudbankimpl.impl.BankImpl;
import com.epam.jmpcloudserviceimpl.impl.ServiceImpl;
import com.epam.jmpdto.dto.*;

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
        ).collect(Collectors.toList());

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

    private LocalDate getRandomDate() {
        long minDay = LocalDate.of(1960, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2006, 12, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay);
    }

    private BankCardType getRandomBankCardType() {
        BankCardType[] cardTypes = BankCardType.values();
        int randomIndex = ThreadLocalRandom.current().nextInt(cardTypes.length);
        return cardTypes[randomIndex];
    }

    private double getRandomDouble() {
        double min = 1.0;
        double max = 10000.0;

        var randomDouble = ThreadLocalRandom.current().nextDouble(min, max);

        return BigDecimal.valueOf(randomDouble).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}

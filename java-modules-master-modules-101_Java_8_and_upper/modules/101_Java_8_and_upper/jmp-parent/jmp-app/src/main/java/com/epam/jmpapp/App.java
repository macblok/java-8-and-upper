package com.epam.jmpapp;

import com.epam.jmpapp.app.DemoDriver;

public class App {
    public static void main(String[] args) {
        var demoDriver = new DemoDriver();

        var bankCards = demoDriver.demonstrateCreateBankCard(5);

        System.out.println("-".repeat(200));
        demoDriver.demonstrateSubscriber(bankCards);
        System.out.println("-".repeat(200));
        demoDriver.demonstrateGetSubscriptionByBankCardNumber(bankCards.get(0).getNumber());
        System.out.println("-".repeat(200));
        demoDriver.demonstrateGetAllUsers();
        System.out.println("-".repeat(200));
        demoDriver.demonstrateGetAverageUsersAge();
    }
}
package com.epam.jmpcloudserviceimpl.impl;

import com.epam.jmpdto.dto.BankCard;
import com.epam.jmpdto.dto.Subscription;
import com.epam.jmpdto.dto.User;
import com.epam.jmpserviceapi.service.Service;

import java.time.LocalDate;
import java.util.*;


public class ServiceImpl implements Service {

    //Simulating database with HashMap
    private final Map<String, Subscription> subscriptionDatabase = new HashMap<>();

    /**
     * Subscribes a {@link BankCard} to the banking service. This involves validating the bank card, checking if
     * a subscription already exists, and then storing the new subscription initialized with the current date.
     *
     * @param bankCard The bank card to subscribe (must not be null).
     * @throws IllegalArgumentException if the provided bank card is null.
     * @throws IllegalStateException if a subscription for the bank card already exists.
     */
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

    /**
     * Retrieves a subscription based on a provided card number.
     *
     * @param cardNumber The card number of the subscription to retrieve (must not be null, empty, or invalid).
     * @return An {@link Optional} containing the subscription if found, or an empty Optional if no subscription is found.
     * @throws IllegalArgumentException if the card number is null, empty, or does not meet valid length criteria.
     */
    @Override
    public Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.trim().isEmpty() || cardNumber.length() != 19) {
            throw new IllegalArgumentException("Card number cannot be null, empty or invalid.");
        }

        var foundSubscription = subscriptionDatabase.get(cardNumber);

        return Optional.ofNullable(foundSubscription);
    }

    /**
     * Fetches all distinct users associated with any subscription in the banking service.
     * This method collects all users from the stored subscriptions, ensuring each user is distinct. The resulting list
     * is unmodifiable, meaning that you cannot perform operations like add, remove, or clear on this list.
     *
     * @return An unmodifiable list of {@link User} representing all unique users who have subscriptions.
     */
    @Override
    public List<User> getAllUsers() {
        return subscriptionDatabase.values().stream()
                .map(Subscription::getCardUser)
                .filter(Objects::nonNull)
                .distinct().toList();
    }
}

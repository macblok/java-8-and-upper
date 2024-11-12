package com.epam.jmpcloudserviceimpl.impl;

import com.epam.jmpdto.dto.BankCard;
import com.epam.jmpdto.dto.Subscription;
import com.epam.jmpdto.dto.User;
import com.epam.jmpserviceapi.service.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;


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
     * @throws SubNotFoundException If no subscription is found for the provided card number.
     */
    @Override
    public Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.trim().isEmpty() || cardNumber.length() != 19) {
            throw new IllegalArgumentException("Card number cannot be null, empty or invalid.");
        }

        var subscriptionOpt = Optional.ofNullable(subscriptionDatabase.get(cardNumber));

        System.out.println("Retrieved subscription" + subscriptionOpt.orElseThrow(() ->
                new SubNotFoundException("Subscription not found for card number: " + cardNumber)));

        return subscriptionOpt;
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

    /**
     * Retrieves all subscriptions that match a specified condition.
     *
     * This method uses a functional interface {@link Predicate} as a condition to test each subscription in the database.
     * Only the subscriptions that satisfy the condition (for which the predicate returns {@code true}) are included in the
     * result list. This method leverages Java's Stream API to filter subscriptions and collect results efficiently.
     *
     * @param condition a {@link Predicate} functional interface that represents a condition to test each subscription.
     *                  It must not be null. The predicate receives a {@link Subscription} object and returns a {@code boolean}
     *                  value, indicating whether the subscription meets the provided condition or not.
     * @return a {@link List} of {@link Subscription} objects that satisfy the specified condition. If no subscriptions meet
     *         the condition, an empty list is returned. This list is unmodifiable, meaning that you cannot perform
     *         operations like add, remove, or clear on this list.
     * @throws NullPointerException if the {@code condition} is null.
     */
    @Override
    public List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> condition) {
        if (condition == null) throw new IllegalArgumentException("Condition must not be null");

        return subscriptionDatabase.values().stream()
                .filter(condition).toList();
    }
}

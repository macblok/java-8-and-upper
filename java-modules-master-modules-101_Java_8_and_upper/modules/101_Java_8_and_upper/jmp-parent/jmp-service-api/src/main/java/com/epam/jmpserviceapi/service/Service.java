package com.epam.jmpserviceapi.service;

import com.epam.jmpdto.dto.BankCard;
import com.epam.jmpdto.dto.Subscription;
import com.epam.jmpdto.dto.User;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

public interface Service {

    void subscribe(BankCard bankCard);

    Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber);

    List<User> getAllUsers();

    /**
     * Calculates the average age of all users.
     * <p>
     * This method computes the average age by iterating over all users obtained from {@link #getAllUsers()}.
     * Only users with a non-null birthday are considered in the calculation.
     * </p>
     * <p>
     * The age is computed based on the difference between the current date and the user's birthday,
     * using {@link java.time.temporal.ChronoUnit#YEARS} to calculate the number of complete years.
     * </p>
     *
     * @return The average age of users as a double. If no users exist or all users have null birthdays,
     *         returns {@link Double#NaN}.
     */
    default double getAverageUsersAge() {
        return getAllUsers().stream()
                .filter(user -> user.getBirthday() != null)
                .mapToDouble(user -> ChronoUnit.YEARS.between(user.getBirthday(), LocalDate.now()))
                .average()
                .orElse(Double.NaN);
    }
}

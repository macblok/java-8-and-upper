package com.epam.jmpdto.dto;

import java.time.LocalDate;
import java.util.Objects;

public class Subscription {
    private BankCard bankcard;
    private LocalDate startDate;

    public Subscription(BankCard bankcard, LocalDate startDate) {
        this.bankcard = bankcard;
        this.startDate = startDate;
    }

    public BankCard getBankCard() {
        return bankcard;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setBankCard(BankCard bankcard) {
        this.bankcard = bankcard;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public User getCardUser() { return bankcard.getUser(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscription that = (Subscription) o;
        return Objects.equals(bankcard, that.bankcard) && Objects.equals(startDate, that.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bankcard, startDate);
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "bankcard='" + bankcard + '\'' +
                ", startDate=" + startDate +
                '}';
    }
}

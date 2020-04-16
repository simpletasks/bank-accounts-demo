package com.javadev.bod.bank.accounts.demo.accounts.savings;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.javadev.bod.bank.accounts.demo.interests.InterestRate;

public class ProvidedInterestHistory {

    private LocalDateTime timeInstance;
    private InterestRate interestRate;
    private BigDecimal amount;

    public ProvidedInterestHistory(InterestRate interestRate, BigDecimal amount) {
        super();
        this.interestRate = interestRate;
        this.amount = amount;
        this.timeInstance = LocalDateTime.now();
    }

    public LocalDateTime getTimeInstance() {
        return timeInstance;
    }

    public InterestRate getInterestRate() {
        return interestRate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

}

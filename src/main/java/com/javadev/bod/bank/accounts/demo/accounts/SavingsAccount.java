package com.javadev.bod.bank.accounts.demo.accounts;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.javadev.bod.bank.accounts.demo.accounts.savings.ProvidedInterestHistory;
import com.javadev.bod.bank.accounts.demo.customers.Customer;
import com.javadev.bod.bank.accounts.demo.interests.InterestRate;

public class SavingsAccount extends Account {

    private InterestRate currentInterestRate;
    private List<ProvidedInterestHistory> providedInterestHistories = new ArrayList<ProvidedInterestHistory>();

    public SavingsAccount(Customer owner, InterestRate interestRate) {
        super(owner);
        this.currentInterestRate = interestRate;
    }

    public BigDecimal provideInterest() {

        BigDecimal providedInterest = getBalance().multiply(currentInterestRate.getValue());

        add(providedInterest);

        ProvidedInterestHistory audit = new ProvidedInterestHistory(currentInterestRate, providedInterest);
        providedInterestHistories.add(audit);

        return providedInterest;
    }

    public InterestRate getCurrentInterestRate() {
        return currentInterestRate;
    }

    public void setCurrentInterestRate(InterestRate currentInterestRate) {
        this.currentInterestRate = currentInterestRate;
    }

    public List<ProvidedInterestHistory> getProvidedInterestHistories() {
        return Collections.unmodifiableList(providedInterestHistories);
    }

}

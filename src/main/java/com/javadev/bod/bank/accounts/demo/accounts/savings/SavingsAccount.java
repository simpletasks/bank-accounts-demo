package com.javadev.bod.bank.accounts.demo.accounts.savings;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.javadev.bod.bank.accounts.demo.accounts.Account;
import com.javadev.bod.bank.accounts.demo.customers.Customer;
import com.javadev.bod.bank.accounts.demo.interests.InterestRate;

public class SavingsAccount extends Account {

    public static final String REQUESTED_WITHDRAW_AMOUNT_BIGGER_THEN_BALANCE = "Requested withdraw amount bigger then balance";
    private static final BigDecimal PERCENTAGE_MULTIPLIER = new BigDecimal("100");

    private InterestRate currentInterestRate;
    private List<ProvidedInterestHistory> providedInterestHistories = new ArrayList<ProvidedInterestHistory>();

    public SavingsAccount(Customer owner, InterestRate interestRate, SavingsAccountsManager manager) {
        super(owner);
        this.currentInterestRate = interestRate;

        Objects.requireNonNull(manager);
        manager.add(this);
    }

    public BigDecimal provideInterest() {

        BigDecimal providedInterest = getBalance().multiply(currentInterestRate.getValue().divide(PERCENTAGE_MULTIPLIER));

        add(providedInterest);

        ProvidedInterestHistory audit = new ProvidedInterestHistory(currentInterestRate, providedInterest);
        providedInterestHistories.add(audit);

        return providedInterest;
    }

    public InterestRate getCurrentInterestRate() {
        return currentInterestRate;
    }

    public void setCurrentInterestRate(InterestRate currentInterestRate) {
        Objects.requireNonNull(currentInterestRate);
        this.currentInterestRate = currentInterestRate;
    }

    public List<ProvidedInterestHistory> getProvidedInterestHistories() {
        return Collections.unmodifiableList(providedInterestHistories);
    }

    @Override
    public BigDecimal withdraw(BigDecimal amount) {

        validateWithdrawValue(amount);

        return super.withdraw(amount);
    }

    private void validateWithdrawValue(BigDecimal amount) {
        if (amount == null) {
            throw new NullPointerException(SUBSTRACTED_AMOUNT_OBJECT_CAN_NOT_BE_NULL);
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(SUBSTRACTED_AMOUNT_MUST_BE_POSITIVE);
        }
        if (amount.compareTo(getBalance()) > 0) {
            throw new IllegalArgumentException(REQUESTED_WITHDRAW_AMOUNT_BIGGER_THEN_BALANCE);
        }
    }

}

package com.javadev.bod.bank.accounts.demo.accounts;

import java.math.BigDecimal;

import com.javadev.bod.bank.accounts.demo.customers.Customer;

public abstract class Account {

    private Customer owner;
    private BigDecimal balance = BigDecimal.ZERO;

    public Account(Customer owner) {
        super();
        this.owner = owner;
    }

    public void add(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Added amount object can not be null");
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Added amount must be positive");
        }

        balance = balance.add(amount);
    }

    public BigDecimal withdraw(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Substracted amount object can not be null");
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Substracted amount must be positive");
        }

        balance = balance.subtract(amount);
        return amount;
    }

    public Customer getOwner() {
        return owner;
    }

    public BigDecimal getBalance() {
        return balance;
    }

}

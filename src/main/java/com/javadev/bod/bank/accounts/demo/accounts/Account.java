package com.javadev.bod.bank.accounts.demo.accounts;

import java.math.BigDecimal;

import com.javadev.bod.bank.accounts.demo.customers.Customer;

public abstract class Account {

    public static final String SUBSTRACTED_AMOUNT_MUST_BE_POSITIVE = "Substracted amount must be positive";
    public static final String SUBSTRACTED_AMOUNT_OBJECT_CAN_NOT_BE_NULL = "Substracted amount object can not be null";
    public static final String ADDED_AMOUNT_MUST_BE_POSITIVE = "Added amount must be positive";
    public static final String ADDED_AMOUNT_OBJECT_CAN_NOT_BE_NULL = "Added amount object can not be null";

    private Customer owner;
    private BigDecimal balance = BigDecimal.ZERO;

    public Account(Customer owner) {
        super();
        this.owner = owner;
    }

    public void add(BigDecimal amount) {
        if (amount == null) {
            throw new NullPointerException(ADDED_AMOUNT_OBJECT_CAN_NOT_BE_NULL);
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(ADDED_AMOUNT_MUST_BE_POSITIVE);
        }
        balance = balance.add(amount);
    }

    public BigDecimal withdraw(BigDecimal amount) {
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

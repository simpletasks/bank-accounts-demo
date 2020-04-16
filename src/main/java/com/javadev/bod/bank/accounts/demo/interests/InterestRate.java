package com.javadev.bod.bank.accounts.demo.interests;

import java.math.BigDecimal;

public class InterestRate {

    private BigDecimal value;

    public InterestRate(BigDecimal value) {
        super();
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

}

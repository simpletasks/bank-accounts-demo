package com.javadev.bod.bank.accounts.demo.interests;

import java.math.BigDecimal;

/**
 * Interest rate number must be in percentage format, 3.2 for 3.2%
 * @author Krunoslav Magazin
 * Apr 16, 2020
 */
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

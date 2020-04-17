package com.javadev.bod.bank.accounts.demo.accounts.checkings;

import java.math.BigDecimal;

import com.javadev.bod.bank.accounts.demo.accounts.Account;
import com.javadev.bod.bank.accounts.demo.accounts.checkings.transfers.TransferManager;
import com.javadev.bod.bank.accounts.demo.customers.Customer;

public class CheckingAccount extends Account {

    public static final String REQUESTED_WITHDRAW_BIGGER_THAN_APPROVED_LIMIT_APPROVED_MINUS = "Requested withdraw bigger than current balance plus approved limit. Approved minus:";
    public static final String SUBSTRACTED_AMOUNT_MUST_BE_POSITIVE = "Substracted amount must be positive";
    public static final String SUBSTRACTED_AMOUNT_OBJECT_CAN_NOT_BE_NULL = "Substracted amount object can not be null";
    
    private BigDecimal approvedMinus = BigDecimal.ZERO;

    public CheckingAccount(Customer owner) {
        super(owner);
    }

    public void setApprovedMinus(BigDecimal approvedMinus) {
        this.approvedMinus = approvedMinus;
    }

    public BigDecimal getApprovedMinus() {
        return approvedMinus;
    }

    @Override
    public BigDecimal withdraw(BigDecimal amount) {
        if (amount == null) {
            throw new NullPointerException(SUBSTRACTED_AMOUNT_OBJECT_CAN_NOT_BE_NULL);
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(SUBSTRACTED_AMOUNT_MUST_BE_POSITIVE);
        }

        if (amount.compareTo(getBalance().add(approvedMinus)) > 0) {
            throw new IllegalArgumentException(REQUESTED_WITHDRAW_BIGGER_THAN_APPROVED_LIMIT_APPROVED_MINUS + getBalance().add(approvedMinus));
        }
        return super.withdraw(amount);
    }
    
    public boolean transferMoney(Account target, BigDecimal amount) {
        
        return TransferManager.getTransferManager(this, target, amount).transfer();
        
    }

}

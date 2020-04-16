package com.javadev.bod.bank.accounts.demo.accounts;

import java.math.BigDecimal;

import com.javadev.bod.bank.accounts.demo.customers.Customer;
import com.javadev.bod.bank.accounts.demo.transfers.TransferManager;

public class CheckingAccount extends Account {

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
            throw new IllegalArgumentException("Substracted amount object can not be null");
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Substracted amount must be positive");
        }

        if (amount.compareTo(approvedMinus) > 0) {
            // TODO - checked exception
            throw new IllegalArgumentException("Requested withdraw bigger than approved limit. Approved minus:" + approvedMinus);
        }
        return super.withdraw(amount);
    }
    
    public boolean transferMoney(Account target, BigDecimal amount) {
        
        return TransferManager.getTransferManager(this, target, amount).transfer();
        
    }

}

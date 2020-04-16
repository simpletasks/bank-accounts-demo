package com.javadev.bod.bank.accounts.demo.transfers;

import java.math.BigDecimal;

import com.javadev.bod.bank.accounts.demo.accounts.Account;
import com.javadev.bod.bank.accounts.demo.accounts.CheckingAccount;

public class BasicTransfer implements Transfer {

    private CheckingAccount sourceAccount;
    private Account targetAccount;
    private BigDecimal transferAmount;

    public BasicTransfer(CheckingAccount sourceAccount, Account targetAccount, BigDecimal transferAmount) {
        super();
        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
        this.transferAmount = transferAmount;
    }

    @Override
    public boolean transfer() {
        
        BigDecimal withdrawAmount = sourceAccount.withdraw(transferAmount);
        if (transferAmount.compareTo(withdrawAmount) == 0) {
            targetAccount.add(withdrawAmount);
            return true;
        }
        return false;
    }
    
    public CheckingAccount getSourceAccount() {
        return sourceAccount;
    }

    public Account getTargetAccount() {
        return targetAccount;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

}

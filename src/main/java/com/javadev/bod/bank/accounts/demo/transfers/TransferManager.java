package com.javadev.bod.bank.accounts.demo.transfers;

import java.math.BigDecimal;

import com.javadev.bod.bank.accounts.demo.accounts.Account;
import com.javadev.bod.bank.accounts.demo.accounts.CheckingAccount;

public class TransferManager {

    private TransferManager() {
    }

    public static Transfer getTransferManager(CheckingAccount sourceAccount, Account targetAccount, BigDecimal transferAmount) {
        return new BasicTransfer(sourceAccount, targetAccount, transferAmount);
    }
}

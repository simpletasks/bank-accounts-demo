package com.javadev.bod.bank.accounts.demo.accounts.managers;

import java.util.ArrayList;
import java.util.List;

import com.javadev.bod.bank.accounts.demo.accounts.SavingsAccount;
import com.javadev.bod.bank.accounts.demo.customers.Customer;
import com.javadev.bod.bank.accounts.demo.interests.InterestRate;

public class SavingsAccountsManager {

    List<SavingsAccount> accounts = new ArrayList<SavingsAccount>();

    public void provideInterestsToAllAccounts() {

        accounts.forEach(acc -> acc.provideInterest());
    }

    public SavingsAccount create(Customer owner, InterestRate interestRate) {
        SavingsAccount account = new SavingsAccount(owner, interestRate);
        accounts.add(account);
        return account;
    }

}

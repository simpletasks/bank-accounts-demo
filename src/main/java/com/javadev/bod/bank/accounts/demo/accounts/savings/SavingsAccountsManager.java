package com.javadev.bod.bank.accounts.demo.accounts.savings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.javadev.bod.bank.accounts.demo.customers.Customer;
import com.javadev.bod.bank.accounts.demo.interests.InterestRate;

public class SavingsAccountsManager {

    private final List<SavingsAccount> accounts = new ArrayList<SavingsAccount>();

    public void provideInterestsToAllAccounts() {

        accounts.forEach(acc -> acc.provideInterest());
    }

    public SavingsAccount create(Customer owner, InterestRate interestRate) {
        return  new SavingsAccount(owner, interestRate, this);
    }
    
    public boolean add(SavingsAccount account) {
        Objects.requireNonNull(account);
        if (!accounts.contains(account)) {
            return accounts.add(account);
        }
        return false;
    }
    
    public boolean remove(SavingsAccount account) {
        Objects.requireNonNull(account);
        if (accounts.contains(account)) {
            return accounts.remove(account);
        }
        return false;
        
    }
    
    public List<SavingsAccount> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }

}

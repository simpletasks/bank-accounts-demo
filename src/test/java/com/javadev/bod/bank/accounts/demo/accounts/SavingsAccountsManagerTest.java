package com.javadev.bod.bank.accounts.demo.accounts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.javadev.bod.bank.accounts.demo.accounts.savings.SavingsAccount;
import com.javadev.bod.bank.accounts.demo.accounts.savings.SavingsAccountsManager;
import com.javadev.bod.bank.accounts.demo.customers.Customer;
import com.javadev.bod.bank.accounts.demo.interests.InterestRate;

class SavingsAccountsManagerTest {

    private Customer defaultCustomer;
    private SavingsAccountsManager manager;
    private final List<SavingsAccount> accounts = new ArrayList<SavingsAccount>();
    private final List<InterestRate> interestRates = new ArrayList<InterestRate>();
    private final List<BigDecimal> endBalances = new ArrayList<BigDecimal>();

    @BeforeEach
    void setUp() throws Exception {

        defaultCustomer = new Customer("1", "Kruno", "Kruno");

        interestRates.add(new InterestRate(new BigDecimal("1")));
        interestRates.add(new InterestRate(new BigDecimal("3")));
        interestRates.add(new InterestRate(new BigDecimal("5")));
        interestRates.add(new InterestRate(new BigDecimal("7.7")));

        endBalances.add(new BigDecimal("1010"));
        endBalances.add(new BigDecimal("1030"));
        endBalances.add(new BigDecimal("1050"));
        endBalances.add(new BigDecimal("1077"));

        manager = new SavingsAccountsManager();

        createAaccounts();

        fillAccountsBalances();

    }

    void fillAccountsBalances() {
        accounts.forEach(x -> x.add(new BigDecimal("1000")));
    }

    void createAaccounts() {
        interestRates.forEach(ir -> accounts.add(new SavingsAccount(defaultCustomer, ir, manager)));
    }

    @AfterEach
    void tearDown() throws Exception {
        manager = null;
        accounts.clear();
        interestRates.clear();
    }

    @Test
    void testProvideInterestsToAllAccounts() {

        manager.provideInterestsToAllAccounts();

        for (int i = 0; i < manager.getAccounts().size(); i++) {
            BigDecimal currentBalance = manager.getAccounts().get(i).getBalance();
            BigDecimal expectedResult = endBalances.get(i);

            assertTrue(expectedResult.compareTo(currentBalance) == 0,
                    "Balance with provided interests excepion for index i=" + i + ", currentbalance = " + currentBalance + ", expected balance = " + expectedResult);
        }
    }

    @Test
    void testCreate() {
        int sizeBefore = manager.getAccounts().size();
        SavingsAccount acc = manager.create(defaultCustomer, new InterestRate(new BigDecimal("22")));
        int sizeAfter = manager.getAccounts().size();

        assertEquals(sizeBefore + 1, sizeAfter);
        assertTrue(manager.getAccounts().contains(acc));

        SavingsAccount result = manager.getAccounts().get(manager.getAccounts().size() - 1);
        assertTrue(result.getBalance().compareTo(acc.getBalance()) == 0);
    }

}

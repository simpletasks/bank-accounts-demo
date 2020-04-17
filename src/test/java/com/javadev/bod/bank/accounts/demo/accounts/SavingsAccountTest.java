package com.javadev.bod.bank.accounts.demo.accounts;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.javadev.bod.bank.accounts.demo.accounts.savings.ProvidedInterestHistory;
import com.javadev.bod.bank.accounts.demo.accounts.savings.SavingsAccount;
import com.javadev.bod.bank.accounts.demo.accounts.savings.SavingsAccountsManager;
import com.javadev.bod.bank.accounts.demo.customers.Customer;
import com.javadev.bod.bank.accounts.demo.interests.InterestRate;

class SavingsAccountTest {

    private static final String INIT_INTEREST_RATE_VALUE = "3.2";
    private Customer customer;
    private InterestRate interestRate;
    private SavingsAccount savingsAccount;
    private SavingsAccountsManager manager;

    @BeforeEach
    void setUp() throws Exception {
        customer = new Customer("1", "Kruno", "Kruno");
        interestRate = new InterestRate(new BigDecimal(INIT_INTEREST_RATE_VALUE));
        manager = new SavingsAccountsManager();
        savingsAccount = new SavingsAccount(customer, interestRate, manager);
    }

    @AfterEach
    void tearDown() throws Exception {
        savingsAccount = null;
        customer = null;
        manager = null;

    }

    @Test
    void testProvideInterestOnZeroBalance() {
        
        BigDecimal providedInterest = savingsAccount.provideInterest();
        
        assertTrue(BigDecimal.ZERO.compareTo(providedInterest) == 0);

    }
    
    @Test
    void testProvideInterestOnbalance50() {
        
        savingsAccount.add(new BigDecimal("50"));
        BigDecimal providedInterest = savingsAccount.provideInterest();
        
        assertTrue(new BigDecimal("1.6").compareTo(providedInterest) == 0);
        assertTrue(new BigDecimal("51.6").compareTo(savingsAccount.getBalance()) == 0);
        
    }

    @Test
    void testSetCurrentInterestRate() {
        
        //confirm init state
        assertTrue(new BigDecimal(INIT_INTEREST_RATE_VALUE).compareTo(savingsAccount.getCurrentInterestRate().getValue()) == 0);

        // test change
        String rate = "123456.56";
        savingsAccount.setCurrentInterestRate(new InterestRate(new BigDecimal(rate)));
        
        assertTrue(new BigDecimal(rate).compareTo(savingsAccount.getCurrentInterestRate().getValue()) == 0);
    }
    
    @Test
    void testSetCurrentInterestRateNullException() {
        
        //confirm init state
        assertTrue(new BigDecimal(INIT_INTEREST_RATE_VALUE).compareTo(savingsAccount.getCurrentInterestRate().getValue()) == 0);
        
        assertThrows(NullPointerException.class, () -> savingsAccount.setCurrentInterestRate(null));
    }

    @Test
    void testGetProvidedInterestHistories() {
        savingsAccount.add(new BigDecimal("111"));
        savingsAccount.provideInterest();
        assertEquals(1, savingsAccount.getProvidedInterestHistories().size());
        
        List<ProvidedInterestHistory> history = savingsAccount.getProvidedInterestHistories();
        assertTrue(new BigDecimal("3.552").compareTo(history.get(0).getAmount()) == 0);
        assertTrue(new BigDecimal(INIT_INTEREST_RATE_VALUE).compareTo(history.get(0).getInterestRate().getValue()) == 0);
    }
    
    @Test
    void testGetProvidedInterestHistoriesEmpty() {
        assertEquals(0, savingsAccount.getProvidedInterestHistories().size());
    }

    @Test
    void testAdd() {
        BigDecimal addValue = new BigDecimal("22.456");
        savingsAccount.add(addValue);

        assertTrue(addValue.compareTo(savingsAccount.getBalance()) == 0);
    }
    
    @Test
    void testAddWithNegativeValue() {
        BigDecimal addValue = new BigDecimal("-22.456");

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> savingsAccount.add(addValue));
        assertEquals(exception.getMessage(), SavingsAccount.ADDED_AMOUNT_MUST_BE_POSITIVE);

    }
    
    @Test
    void testAddNull() {
        BigDecimal addValue = null;

        Throwable exception = assertThrows(NullPointerException.class, () -> savingsAccount.add(addValue));
        assertEquals(exception.getMessage(), SavingsAccount.ADDED_AMOUNT_OBJECT_CAN_NOT_BE_NULL);

    }

    
    @Test
    void testWithdrawZero() {
        BigDecimal value = new BigDecimal("0");
        savingsAccount.withdraw(value);

        assertTrue(value.compareTo(savingsAccount.getBalance()) == 0);
    }
    
    @Test
    void testWithdrawFromZero() {
        BigDecimal value = new BigDecimal("10");

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> savingsAccount.withdraw(value));
        assertEquals(exception.getMessage(), SavingsAccount.REQUESTED_WITHDRAW_AMOUNT_BIGGER_THEN_BALANCE);

    }
    
    @Test
    void testWithdrawNull() {
        BigDecimal value = null;

        Throwable exception = assertThrows(NullPointerException.class, () -> savingsAccount.withdraw(value));
        assertEquals(exception.getMessage(), SavingsAccount.SUBSTRACTED_AMOUNT_OBJECT_CAN_NOT_BE_NULL);

    }

    @Test
    void testWithdraw() {
        BigDecimal newBalance = new BigDecimal("100.0");
        
        savingsAccount.add(newBalance);
        
        BigDecimal value = new BigDecimal("88");
        
        savingsAccount.withdraw(value);

        assertTrue(new BigDecimal("12").compareTo(savingsAccount.getBalance()) == 0);

    }
    
    @Test
    void testWithdrawOutOfRange() {
        BigDecimal newBalance = new BigDecimal("100.0");
        
        savingsAccount.add(newBalance);
        
        BigDecimal value = new BigDecimal("111");
        
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> savingsAccount.withdraw(value));
        assertEquals(exception.getMessage(), SavingsAccount.REQUESTED_WITHDRAW_AMOUNT_BIGGER_THEN_BALANCE);

    }

}

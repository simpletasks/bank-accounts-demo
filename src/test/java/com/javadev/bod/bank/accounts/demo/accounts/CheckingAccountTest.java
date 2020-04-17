package com.javadev.bod.bank.accounts.demo.accounts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.javadev.bod.bank.accounts.demo.accounts.checkings.CheckingAccount;
import com.javadev.bod.bank.accounts.demo.customers.Customer;

public class CheckingAccountTest {

    private Customer customer;
    private CheckingAccount checkingAccount;

    @BeforeEach
    void setUp() throws Exception {
        customer = new Customer("1", "Kruno", "Kruno");
        checkingAccount = new CheckingAccount(customer);
    }

    @AfterEach
    void tearDown() throws Exception {
        checkingAccount = null;
        customer = null;

    }

    @Test
    void testAdd() {
        BigDecimal addValue = new BigDecimal("22.456");
        checkingAccount.add(addValue);

        assertTrue(addValue.compareTo(checkingAccount.getBalance()) == 0);
    }

    @Test
    void testAddZero() {
        BigDecimal addValue = new BigDecimal("0");
        checkingAccount.add(addValue);

        assertTrue(addValue.compareTo(checkingAccount.getBalance()) == 0);
    }

    @Test
    void testAddWithNegativeValue() {
        BigDecimal addValue = new BigDecimal("-22.456");

        assertThrows(IllegalArgumentException.class, () -> checkingAccount.add(addValue));
    }

    @Test
    void testWithdrawZero() {
        BigDecimal value = new BigDecimal("0");
        checkingAccount.withdraw(value);

        assertTrue(value.compareTo(checkingAccount.getBalance()) == 0);
    }

    @Test
    void testWithdrawFromZero() {
        BigDecimal value = new BigDecimal("10");

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> checkingAccount.withdraw(value));
        assertEquals(exception.getMessage(), CheckingAccount.REQUESTED_WITHDRAW_BIGGER_THAN_APPROVED_LIMIT_APPROVED_MINUS + checkingAccount.getBalance());

    }

    @Test
    void testWithdrawFromZeroWithApprovedMinusInRange() {
        checkingAccount.setApprovedMinus(new BigDecimal("50"));

        BigDecimal value = new BigDecimal("10");
        checkingAccount.withdraw(value);

        assertEquals(value.negate(), checkingAccount.getBalance());
    }

    @Test
    void testWithdrawFromZeroWithApprovedMinusOutOfRange() {
        BigDecimal approvedMinus = new BigDecimal("50");
        checkingAccount.setApprovedMinus(approvedMinus);

        BigDecimal value = new BigDecimal("60");

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> checkingAccount.withdraw(value));
        assertEquals(exception.getMessage(),
                CheckingAccount.REQUESTED_WITHDRAW_BIGGER_THAN_APPROVED_LIMIT_APPROVED_MINUS + checkingAccount.getBalance().add(approvedMinus));
    }

    @Test
    void testWithdrawNull() {

        BigDecimal value = null;

        Throwable exception = assertThrows(NullPointerException.class, () -> checkingAccount.withdraw(value));
        assertEquals(exception.getMessage(), CheckingAccount.SUBSTRACTED_AMOUNT_OBJECT_CAN_NOT_BE_NULL);
    }

    @Test
    void testWithdrawNegativeValue() {

        BigDecimal value = new BigDecimal("-10");

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> checkingAccount.withdraw(value));
        assertEquals(exception.getMessage(), CheckingAccount.SUBSTRACTED_AMOUNT_MUST_BE_POSITIVE);
    }

    @Test
    void testWithdrawFromAmountWithApprovedMinusInRange() {
        checkingAccount.setApprovedMinus(new BigDecimal("50"));
        BigDecimal newBalance = new BigDecimal("30");
        checkingAccount.add(newBalance);

        // stage test
        assertEquals(newBalance, checkingAccount.getBalance());

        BigDecimal value = new BigDecimal("70");
        checkingAccount.withdraw(value);

        assertEquals(new BigDecimal("-40"), checkingAccount.getBalance());
    }

    @Test
    void testTransfer() {

        CheckingAccount sourceAccount = checkingAccount;
        sourceAccount.add(new BigDecimal("12000"));
        Customer targetOwner = new Customer("2", "Petar", "Petar");
        CheckingAccount targetAccount = new CheckingAccount(targetOwner);
        
        assertTrue(new BigDecimal("12000").compareTo(sourceAccount.getBalance()) == 0);
        assertTrue(BigDecimal.ZERO.compareTo(targetAccount.getBalance()) == 0);
        
        sourceAccount.transferMoney(targetAccount, new BigDecimal("1500"));
        
        assertTrue(new BigDecimal("10500").compareTo(sourceAccount.getBalance()) == 0);
        assertTrue(new BigDecimal("1500").compareTo(targetAccount.getBalance()) == 0);
        
    }
    
    @Test
    void testTransferOverApprovedMinus() {

        CheckingAccount sourceAccount = checkingAccount;
        BigDecimal approvedMinus = new BigDecimal("12000");
        sourceAccount.add(approvedMinus);
        Customer targetOwner = new Customer("2", "Petar", "Petar");
        CheckingAccount targetAccount = new CheckingAccount(targetOwner);
        
        assertTrue(new BigDecimal("12000").compareTo(sourceAccount.getBalance()) == 0);
        assertTrue(BigDecimal.ZERO.compareTo(targetAccount.getBalance()) == 0);
        
       
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> sourceAccount.transferMoney(targetAccount, new BigDecimal("15000")));
        assertEquals(exception.getMessage(), CheckingAccount.REQUESTED_WITHDRAW_BIGGER_THAN_APPROVED_LIMIT_APPROVED_MINUS + approvedMinus);
        
    }

}

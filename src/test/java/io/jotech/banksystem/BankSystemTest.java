package io.jotech.banksystem;

import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import io.jotech.classicmodels.entity.Customer;

class BankSystemTest {
    BankSystem bankSystem;

    @BeforeEach
    public void setup() {
        bankSystem = new BankSystem();
    }

    @Test
    void processLoanApplication() {
        //given inputs

        var principle = BigDecimal.valueOf(450_000);
        var income1 = BigDecimal.valueOf(50_000);
        var income2 = BigDecimal.valueOf(300_000);
        var months = 6;
        var customer = Customer.builder()
                .customerNumber(1)
                .customerName("Duncan")
                .city("Nairobi")
                .creditLimit(BigDecimal.valueOf(600_000))
                .build();

        //when

        boolean isProcessed = bankSystem.processLoanApplication(customer, principle, income1, months);

        //then expectation
        boolean expected = false;

        //Confirmation/Assertions

        assertEquals(
                expected,
                bankSystem.processLoanApplication(customer, principle, income1, months),
                () -> "The loan should not be processed given the principle is " + principle
                        + ", income is " + income1);


        assertTrue(bankSystem.processLoanApplication(customer, principle, income2, months), () -> "The loan should be processed given the principle is " + principle
                + ", income is " + income2);


    }
}
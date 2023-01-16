package io.jotech.banksystem;

import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import io.jotech.classicmodels.entity.Customer;

class BankSystemTest {
    BankSystem bankSystem;

    @Test
    void processLoanApplication() {
        //given inputs

        var principle = BigDecimal.valueOf(450_000);
        var income = BigDecimal.valueOf(50_000);
        var months = 6;
        var customer = Customer.builder()
                .customerNumber(1)
                .customerName("Duncan")
                .city("Nairobi")
                .creditLimit(BigDecimal.valueOf(600_000))
                .build();

        //when
        bankSystem = new BankSystem();

        boolean isProcessed = bankSystem.processLoanApplication(customer,principle,income,months);

        //then expectation
        boolean expected = false;

        //Confirmation/Assertions

        Assertions.assertEquals(
                expected,
                isProcessed,
                ()->"The loan should not be processed given the principle is "+principle
                        + ", income is "+income);


    }
}
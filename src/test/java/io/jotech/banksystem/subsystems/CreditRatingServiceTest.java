package io.jotech.banksystem.subsystems;

import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import io.jotech.classicmodels.entity.Customer;
import io.jotech.classicmodels.service.CustomerService;

class CreditRatingServiceTest {
    CreditRatingService  creditRatingService;
    @BeforeEach
    public void setup(){
        creditRatingService = new CreditRatingService();
    }

    @Test
    void checkCustomerRating() {
        //given
        var customer1 = Customer.builder()
                .customerNumber(1)
                .customerName("Duncan")
                .city("Nairobi")
                .creditLimit(BigDecimal.valueOf(400_000))
                .build();
        var customer2 = Customer.builder()
                .customerNumber(1)
                .customerName("Duncan")
                .city("Nairobi")
                .creditLimit(BigDecimal.valueOf(600_000))
                .build();
        var customer3 = Customer.builder()
                .customerNumber(1)
                .customerName("Duncan")
                .city("Nairobi")
                .creditLimit(BigDecimal.valueOf(300_000))
                .build();

        //then expectation
        boolean expected = true;




        Assertions.assertAll(
                ()-> Assertions.assertEquals(
                        expected,creditRatingService.checkCustomerRating(customer1)),
                ()-> Assertions.assertEquals(
                        expected,creditRatingService.checkCustomerRating(customer2)),
                ()-> assertFalse(creditRatingService.checkCustomerRating(customer3))
        );


    }
}
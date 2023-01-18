package io.jotech.banksystem.subsystems;

import static org.junit.jupiter.api.Assertions.assertTrue;
import io.jotech.classicmodels.entity.Customer;

import org.junit.jupiter.api.Test;

class InterBankPolicyServiceTest {
    /**
     * Method under test: {@link InterBankPolicyService#checkCompliance(Customer)}
     */
    @Test
    void testCheckCompliance() {
        InterBankPolicyService interBankPolicyService = new InterBankPolicyService();
        assertTrue(interBankPolicyService.checkCompliance(new Customer()));
    }
}


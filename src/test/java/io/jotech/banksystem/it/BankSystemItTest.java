package io.jotech.banksystem.it;

import java.math.BigDecimal;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import classicmodels.util.ArquillianWarUtils;
import io.jotech.banksystem.BankSystem;
import io.jotech.banksystem.subsystems.CreditRatingService;
import io.jotech.banksystem.subsystems.InterBankPolicyService;
import io.jotech.banksystem.subsystems.RepaymentPayabilityService;
import io.jotech.banksystem.subsystems.RepaymentService;
import io.jotech.banksystem.subsystems.TransferService;
import io.jotech.classicmodels.entity.Customer;
import io.jotech.classicmodels.entity.Employee;
import io.jotech.classicmodels.entity.Office;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@ExtendWith(ArquillianExtension.class)
@Slf4j
public class BankSystemItTest {

    @Deployment
    public static Archive<?> createDeployment() {
        WebArchive webArchive = ArquillianWarUtils
                .getBasicWebArchive("BankSystemItTest.war")
                .addClasses(
                        CreditRatingService.class,
                        RepaymentPayabilityService.class,
                        InterBankPolicyService.class,
                        TransferService.class,
                        RepaymentService.class,
                        BankSystem.class

                )
                .addPackages(true,"io.jotech.classicmodels.entity");
        log.info(webArchive.toString(true));
        return webArchive;
    }
    @BeforeEach
    public void setup(){

    }
    @Inject
    private BankSystem  bankSystem;

    @Test
    @DisplayName("testCorrectInjectionOfBankSystem")
    void testCorrectInjectionOfBankSystem() {

        Assertions.assertNotNull(bankSystem);
    }
    @Test
    @DisplayName("processLoanApplication")
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

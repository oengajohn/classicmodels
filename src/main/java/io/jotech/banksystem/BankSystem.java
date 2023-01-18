package io.jotech.banksystem;

import java.math.BigDecimal;

import io.jotech.banksystem.subsystems.CreditRatingService;
import io.jotech.banksystem.subsystems.InterBankPolicyService;
import io.jotech.banksystem.subsystems.RepaymentPayabilityService;
import io.jotech.banksystem.subsystems.RepaymentService;
import io.jotech.banksystem.subsystems.TransferService;
import io.jotech.classicmodels.entity.Customer;
import jakarta.inject.Inject;


public class BankSystem {
    @Inject

    private CreditRatingService creditRatingService;

    @Inject
    private InterBankPolicyService interBankPolicyService;

    @Inject

    private RepaymentPayabilityService repaymentPayabilityService;

    @Inject

    private RepaymentService repaymentService;

    @Inject

    private TransferService transferService;


    public boolean processLoanApplication(
            Customer customer,
            BigDecimal principle,
            BigDecimal income,
            Integer months) {
        boolean passes = creditRatingService.checkCustomerRating(customer);
        if (!passes) {
            return false;
        }
        boolean affordable = repaymentPayabilityService.calculatePayability(principle, income, months);

        if (!affordable) {
            return false;
        }
        boolean compliant = interBankPolicyService.checkCompliance(customer);

        if (!compliant) {
            return false;
        }
        //Application is successful, transfer the money to the customer account

        transferService.makeTransfer(principle, customer);
        repaymentService.setupPaymentSchedule(customer, principle, months);
        return true;


    }
}

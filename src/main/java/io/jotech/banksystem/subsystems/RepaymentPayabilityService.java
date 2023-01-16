package io.jotech.banksystem.subsystems;

import java.math.BigDecimal;
import java.math.MathContext;

public class RepaymentPayabilityService {
    public boolean calculatePayability(BigDecimal principle, BigDecimal income, Integer months){

        var interestRate =BigDecimal.valueOf(0.1);

        var interest  = interestRate.multiply(principle).multiply(BigDecimal.valueOf(
                Double.valueOf(months)/12
        ));

        var repaymentAmount = principle.add(interest);

        var installments = repaymentAmount.divide(BigDecimal.valueOf(months), MathContext.DECIMAL32);

        var thirtyPrctIncome = BigDecimal.valueOf(0.3).multiply(income);


        return  installments.compareTo(thirtyPrctIncome)<=0;
    }
}

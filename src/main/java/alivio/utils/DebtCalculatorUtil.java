package eu.sii.pl.alivio.utils;

import eu.sii.pl.alivio.model.Debt;
import eu.sii.pl.alivio.model.Payment;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class DebtCalculatorUtil {
    private DebtCalculatorUtil() {
    }

    public static BigDecimal getDebtPaymentsSum(Debt debt) {
        return debt.getPayments().stream().map(Payment::getPaymentAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_EVEN);
    }

    public static BigDecimal getRemainingAmount(Debt debt) {
        return debt.getDebtAmount()
                .subtract(getDebtPaymentsSum(debt)
                        .setScale(2, RoundingMode.HALF_EVEN));
    }
}

package eu.sii.pl.alivio.views.plannedpayment;

import eu.sii.pl.alivio.model.PlannedPayment;
import eu.sii.pl.alivio.views.debtor.DebtView;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PlannedPaymentView {
    private final DebtView debtView;
    private final PlannedPayment plannedPayment;
    private final BigDecimal plannedRemainingAmount;

    public DebtView getDebtView() {
        return debtView;
    }

    public PlannedPayment getPlannedPayment() {
        return plannedPayment;
    }

    public BigDecimal getPlannedRemainingAmount() {
        return plannedRemainingAmount;
    }

    public PlannedPaymentView(DebtView debtView, PlannedPayment plannedPayment) {
        this.debtView = debtView;
        this.plannedPayment = plannedPayment;
        this.plannedRemainingAmount = debtView
                .getDebtAmount()
                .subtract(debtView.getDebtPaymentSum())
                .subtract(plannedPayment.getAmountOfRepaymentDebt())
                .setScale(2, RoundingMode.HALF_EVEN);
    }
}


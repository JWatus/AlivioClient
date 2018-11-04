package alivio.views.plannedpayment;

import alivio.model.Debtor;
import alivio.model.PaymentPlan;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class PaymentPlanView {
    private final Debtor debtor;
    private final PaymentPlan paymentPlan;
    private final BigDecimal plannedPaymentSum;
    private final BigDecimal plannedRemainingSum;
    private List<PlannedPaymentView> plannedPaymentViewList;

    public Debtor getDebtor() {
        return debtor;
    }

    public PaymentPlan getPaymentPlan() {
        return paymentPlan;
    }

    public BigDecimal getPlannedPaymentSum() {
        return plannedPaymentSum;
    }

    public BigDecimal getPlannedRemainingSum() {
        return plannedRemainingSum;
    }

    public List<PlannedPaymentView> getPlannedPaymentViewList() {
        return Collections.unmodifiableList(plannedPaymentViewList);
    }

    public PaymentPlanView(Debtor debtor, PaymentPlan paymentPlan, BigDecimal plannedPaymentSum, BigDecimal plannedRemainingSum, List<PlannedPaymentView> plannedPaymentViewList) {
        this.debtor = debtor;
        this.paymentPlan = paymentPlan;
        this.plannedPaymentSum = plannedPaymentSum;
        this.plannedRemainingSum = plannedRemainingSum;
        this.plannedPaymentViewList = plannedPaymentViewList;
    }
}

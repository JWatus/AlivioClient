package eu.sii.pl.alivio.views.builders;

import eu.sii.pl.alivio.model.Debtor;
import eu.sii.pl.alivio.model.PaymentPlan;
import eu.sii.pl.alivio.model.PlannedPayment;
import eu.sii.pl.alivio.views.debtor.DebtView;
import eu.sii.pl.alivio.views.plannedpayment.PaymentPlanView;
import eu.sii.pl.alivio.views.plannedpayment.PlannedPaymentView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class PaymentPlanViewBuilder {
    private final Debtor debtor;
    private final PaymentPlan paymentPlan;

    public PaymentPlanViewBuilder(Debtor debtor, PaymentPlan paymentPlan) {
        this.debtor = debtor;
        this.paymentPlan = paymentPlan;
    }

    public PaymentPlanView build() {
        return new PaymentPlanView(debtor, paymentPlan,
                this.getPlannedPaymentSum(),
                this.getPlannedRemainingSum(),
                this.createPlannedPaymentsViewList());
    }
    private BigDecimal getPlannedPaymentSum() {
        return paymentPlan.getPlannedPaymentList().stream()
                .map(PlannedPayment::getAmountOfRepaymentDebt)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_EVEN);
    }

    private BigDecimal getPlannedRemainingSum() {
        return this.createPlannedPaymentsViewList().stream()
                .map(PlannedPaymentView::getPlannedRemainingAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_EVEN);
    }

    private List<PlannedPaymentView> createPlannedPaymentsViewList() {
        List<PlannedPaymentView> plannedPaymentViewList = new ArrayList<>();
        Map<String, PlannedPayment> plannedPaymentMap = new HashMap<>();
        paymentPlan.getPlannedPaymentList().forEach(x -> plannedPaymentMap.put(x.getUuid(), x));
        debtor.getDebts().forEach(x -> {
            DebtView debtView = new DebtViewBuilder(x).build();
            PlannedPayment plannedPayment = new PlannedPayment();
            if (plannedPaymentMap.containsKey(x.getUuid())) {
                plannedPayment = plannedPaymentMap.get(x.getUuid());
            } else {
                plannedPayment.setAmountOfRepaymentDebt(BigDecimal.ZERO);
                plannedPayment.setUuid(x.getUuid());
            }
            plannedPaymentViewList.add(new PlannedPaymentView(debtView, plannedPayment));
        });
        return Collections.unmodifiableList(plannedPaymentViewList);
    }
}
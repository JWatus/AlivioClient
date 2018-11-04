package alivio.views.builders;

import alivio.model.*;
import alivio.utils.DebtCalculatorUtil;
import alivio.views.debtor.DebtorSummaryView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class DebtorSummaryViewBuilder {
    private final Debtor debtor;

    public DebtorSummaryViewBuilder(Debtor debtor) {
        this.debtor = debtor;
    }

    public DebtorSummaryView build() {
        return new DebtorSummaryView(
                debtor.getDebts().size(),
                this.getAllDebtsAmountSum(),
                this.getAllPaymentSum(),
                this.getRemainingAmountSum());
    }

    private BigDecimal getAllDebtsAmountSum() {
        return debtor.getDebts().stream()
                .map(Debt::getDebtAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_EVEN);
    }

    private BigDecimal getAllPaymentSum() {
        List<BigDecimal> allPayments = new ArrayList<>();
        for (Debt item : debtor.getDebts()) {
            BigDecimal debtPaymentsSum = item.getPayments().stream()
                    .map(Payment::getPaymentAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .setScale(2, RoundingMode.HALF_EVEN);
            allPayments.add(debtPaymentsSum);
        }
        return allPayments.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_EVEN);
    }

    private BigDecimal getRemainingAmountSum() {
        List<BigDecimal> remainingAmountList = new ArrayList<>();
        debtor.getDebts().forEach(x -> remainingAmountList
                .add(this.getRemainingAmount(x)));
        return remainingAmountList.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_EVEN);
    }

    private BigDecimal getRemainingAmount(Debt debt) {
        return DebtCalculatorUtil.getRemainingAmount(debt);
    }
}

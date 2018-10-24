package eu.sii.pl.alivio.views.debtor;

import java.math.BigDecimal;

public class DebtorSummaryView {
    private final int debtsNumberSum;
    private final BigDecimal debtsAmountSum;
    private final BigDecimal payedAmountSum;
    private final BigDecimal remainingAmountSum;

    public int getDebtsNumberSum() {
        return debtsNumberSum;
    }

    public BigDecimal getDebtsAmountSum() {
        return debtsAmountSum;
    }

    public BigDecimal getPayedAmountSum() {
        return payedAmountSum;
    }

    public BigDecimal getRemainingAmountSum() {
        return remainingAmountSum;
    }

    public DebtorSummaryView(int debtsNumberSum, BigDecimal debtsAmountSum, BigDecimal payedAmountSum, BigDecimal remainingAmountSum) {
        this.debtsNumberSum = debtsNumberSum;
        this.debtsAmountSum = debtsAmountSum;
        this.payedAmountSum = payedAmountSum;
        this.remainingAmountSum = remainingAmountSum;
    }
}

package eu.sii.pl.alivio.views.debtor;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DebtView {
    private final Long id;
    private final String debtName;
    private final String uuid;
    private final LocalDate repaymentDate;
    private final BigDecimal debtAmount;
    private final BigDecimal debtPaymentSum;
    private final BigDecimal debtRemainingSum;

    public Long getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public String getDebtName() {
        return debtName;
    }

    public LocalDate getRepaymentDate() {
        return repaymentDate;
    }

    public BigDecimal getDebtAmount() {
        return debtAmount;
    }

    public BigDecimal getDebtPaymentSum() {
        return debtPaymentSum;
    }

    public BigDecimal getDebtRemainingSum() {
        return debtRemainingSum;
    }

    public DebtView(Long id, String debtName, String uuid, LocalDate repaymentDate, BigDecimal debtAmount, BigDecimal debtPaymentSum, BigDecimal debtRemainingSum) {
        this.id = id;
        this.debtName = debtName;
        this.uuid = uuid;
        this.repaymentDate = repaymentDate;
        this.debtAmount = debtAmount;
        this.debtPaymentSum = debtPaymentSum;
        this.debtRemainingSum = debtRemainingSum;
    }
}

package eu.sii.pl.alivio.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class Debt implements Serializable {
    private Long id;
    private String debtName;
    private BigDecimal debtAmount;
    private LocalDate repaymentDate;
    private List<Payment> payments;
    private String uuid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getDebtAmount() {
        return debtAmount.setScale(2, RoundingMode.HALF_EVEN);
    }

    public void setDebtAmount(BigDecimal debtAmount) {
        this.debtAmount = debtAmount;
    }

    public LocalDate getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(LocalDate repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public List<Payment> getPayments() {
        return Collections.unmodifiableList(payments);
    }

    public void setPayments(List<Payment> payments) {
        this.payments = Collections.unmodifiableList(payments);
    }

    public String getDebtName() {
        return debtName;
    }

    public void setDebtName(String debtName) {
        this.debtName = debtName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Debt() {
    }

    public Debt(Long id, String uuid, String debtName, BigDecimal debtAmount, LocalDate repaymentDate, List<Payment> payments) {
        this.id = id;
        this.uuid = uuid;
        this.debtName = debtName;
        this.debtAmount = debtAmount;
        this.repaymentDate = repaymentDate;
        this.payments = payments;
    }

    @Override
    public String toString() {
        return "Debt{" +
                "id=" + id +
                ", debtName='" + debtName + '\'' +
                ", debtAmount=" + debtAmount +
                ", repaymentDate=" + repaymentDate +
                ", payments=" + payments +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}

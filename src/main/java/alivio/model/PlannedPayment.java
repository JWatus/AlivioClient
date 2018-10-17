package eu.sii.pl.alivio.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class PlannedPayment implements Serializable {
    private String uuid;
    private BigDecimal amountOfRepaymentDebt;

    public PlannedPayment() {
    }

    public PlannedPayment(String uuid, BigDecimal amountOfRepaymentDebt) {
        this.uuid = uuid;
        this.amountOfRepaymentDebt = amountOfRepaymentDebt;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public BigDecimal getAmountOfRepaymentDebt() {
        return amountOfRepaymentDebt;
    }

    public void setAmountOfRepaymentDebt(BigDecimal amountOfRepaymentDebt) {
        this.amountOfRepaymentDebt = amountOfRepaymentDebt;
    }
}

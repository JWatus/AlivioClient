package eu.sii.pl.alivio.model;

import eu.sii.pl.alivio.validator.Ssn;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;

public class PaymentDeclaration implements Serializable {
    @NotNull
    @Positive
    @Digits(integer = 6, fraction = 2)
    private BigDecimal paymentAmount;

    @Ssn
    private String ssn;

    @Pattern(regexp = "[0-9A-Z//]*")
    private String debtUuid;

    public PaymentDeclaration() {
    }

    public PaymentDeclaration(BigDecimal paymentAmount, String ssn, String debtUuid) {
        this.paymentAmount = paymentAmount;
        this.ssn = ssn;
        this.debtUuid = debtUuid;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getDebtUuid() {
        return debtUuid;
    }

    public void setDebtUuid(String debtUuid) {
        this.debtUuid = debtUuid;
    }
}

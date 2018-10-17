package eu.sii.pl.alivio.model;

import eu.sii.pl.alivio.validator.Ssn;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class PaymentForm implements Serializable {
    @Valid
    private Payment payment;

    @NotNull
    @Ssn
    private String ssn;

    @NotNull
    @Size(min = 12, max = 12, message = "size must be equals 12 digits")
    @Pattern(regexp = "[0-9]*")
    private String debtUuid;

    public PaymentForm() {
    }

    public PaymentForm(Payment payment, String ssn, String debtUuid) {
        this.payment = payment;
        this.ssn = ssn;
        this.debtUuid = debtUuid;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
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

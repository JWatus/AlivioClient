package eu.sii.pl.alivio.model;

import eu.sii.pl.alivio.validator.Ssn;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PaymentPlan implements Serializable {
    private String message;

    @NotNull
    @Ssn
    private String ssn;

    private List<PlannedPayment> plannedPaymentList = Collections.emptyList();

    public PaymentPlan() {
    }

    public PaymentPlan(String message, String ssn, List<PlannedPayment> plannedPaymentList) {
        this.message = message;
        this.ssn = ssn;
        this.plannedPaymentList = new ArrayList<>(plannedPaymentList);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public List<PlannedPayment> getPlannedPaymentList() {
        return Collections.unmodifiableList(plannedPaymentList);
    }

    public void setPlannedPaymentList(List<PlannedPayment> plannedPaymentList) {
        this.plannedPaymentList = new ArrayList<>(plannedPaymentList);
    }
}

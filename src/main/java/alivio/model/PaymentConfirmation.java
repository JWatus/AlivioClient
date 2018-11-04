package alivio.model;

import javax.validation.Valid;
import java.io.Serializable;

public class PaymentConfirmation implements Serializable {
    private final String clientId = "Alivio";

    @Valid
    private PaymentDeclaration paymentDeclaration;

    @Valid
    private CreditCard creditCard;

    public PaymentConfirmation() {
    }

    public PaymentConfirmation(PaymentDeclaration paymentDeclaration, CreditCard creditCard) {
        this.paymentDeclaration = paymentDeclaration;
        this.creditCard = creditCard;
    }

    public String getClientId() {
        return clientId;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public PaymentDeclaration getPaymentDeclaration() {
        return paymentDeclaration;
    }

    public void setPaymentDeclaration(PaymentDeclaration paymentDeclaration) {
        this.paymentDeclaration = paymentDeclaration;
    }
}

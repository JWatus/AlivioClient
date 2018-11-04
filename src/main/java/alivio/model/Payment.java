package alivio.model;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Payment implements Serializable {
    private static final String CLIENT_ID = "Alivio";
    private Long id;

    private LocalDate paymentDate;

    @NotNull
    @Positive
    @Digits(integer = 6, fraction = 2)
    private BigDecimal paymentAmount;

    @Valid
    private CreditCard creditCard;
    private String clientId;

    public Payment() {
    }

    public Payment(LocalDate paymentDate, BigDecimal paymentAmount, CreditCard creditCard) {
        this.paymentDate = paymentDate;
        this.paymentAmount = paymentAmount;
        this.creditCard = creditCard;
        this.clientId = CLIENT_ID;
    }

    public static String getClientId() {
        return CLIENT_ID;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", paymentDate=" + paymentDate +
                ", paymentAmount=" + paymentAmount +
                ", creditCard=" + creditCard +
                ", clientId='" + clientId + '\'' +
                '}';
    }
}
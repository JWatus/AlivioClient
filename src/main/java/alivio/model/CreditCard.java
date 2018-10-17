package eu.sii.pl.alivio.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

public class CreditCard implements Serializable {
    private Long id;

    private CreditCardType creditCardType;

    @NotNull
    @Size(min = 16, max = 16)
    @Pattern(regexp = "[0-9]*")
    private String ccNumber;

    @NotNull
    @Size(min = 3, max = 3, message = "size must be equals 3 digits")
    @Pattern(regexp = "[0-9]*")
    private String cvv;

    @NotNull
    @Size(min = 2, max = 30)
    private String issuingNetwork;

    @NotNull
    @Size(min = 2, max = 30)
    @Pattern(regexp = "[a-zA-Z0-9_.]*")
    private String firstName;

    @NotNull
    @Size(min = 2, max = 30)
    @Pattern(regexp = "[a-zA-Z0-9_.]*")
    private String lastName;

    @NotNull
    @FutureOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expDate;

    public CreditCard() {
    }

    public CreditCard(String ccNumber, String cvv, String issuingNetwork, String firstName, String lastName, LocalDate expDate) {
        this.ccNumber = ccNumber;
        this.cvv = cvv;
        this.issuingNetwork = issuingNetwork;
        this.firstName = firstName;
        this.lastName = lastName;
        this.expDate = expDate;
    }

    public CreditCard(CreditCardType creditCardType) {
        this.creditCardType = creditCardType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCcNumber() {
        return ccNumber;
    }

    public void setCcNumber(String ccNumber) {
        this.ccNumber = ccNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getIssuingNetwork() {
        return issuingNetwork;
    }

    public void setIssuingNetwork(String issuingNetwork) {
        this.issuingNetwork = issuingNetwork;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getExpDate() {
        return expDate;
    }

    public void setExpDate(LocalDate expDate) {
        this.expDate = expDate;
    }

    public CreditCardType getCreditCardType() {
        return creditCardType;
    }

    public void setCreditCardType(CreditCardType creditCardType) {
        this.creditCardType = creditCardType;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "id=" + id +
                ", creditCardType=" + creditCardType +
                ", ccNumber='" + ccNumber + '\'' +
                ", cvv='" + cvv + '\'' +
                ", issuingNetwork='" + issuingNetwork + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", expDate=" + expDate +
                '}';
    }
}
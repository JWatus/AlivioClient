package eu.sii.pl.alivio.model;

import eu.sii.pl.alivio.validator.Ssn;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Debtor implements Serializable {
    private Long id;

    @NotNull
    @Size(min = 2, max = 30)
    @Pattern(regexp = "[a-zA-Z0-9_.]*")
    private String firstName;

    @NotNull
    @Size(min = 2, max = 30)
    @Pattern(regexp = "[a-zA-Z0-9_.]*")
    private String lastName;

    @NotNull
    @Ssn
    private String ssn;

    private List<Debt> debts = Collections.emptyList();

    public Debtor() {
    }

    public Debtor(Long id, String firstName, String lastName, String ssn, List<Debt> debts) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.ssn = ssn;
        this.debts = new ArrayList<>(debts);
    }

    public List<Debt> getDebts() {
        return debts.stream()
                .sorted(Comparator.comparing(Debt::getRepaymentDate))
                .collect(Collectors.toList());
    }

    public void setDebts(List<Debt> debts) {
        this.debts = new ArrayList<>(debts);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    @Override
    public String toString() {
        return "Debtor{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", ssn='" + ssn + '\'' +
                ", debts=" + debts +
                '}';
    }
}

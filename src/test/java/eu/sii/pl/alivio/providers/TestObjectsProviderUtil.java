package eu.sii.pl.alivio.providers;

import eu.sii.pl.alivio.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class TestObjectsProviderUtil {
    private TestObjectsProviderUtil() {
    }

    public static Debtor getExampleDebtor() {
        CreditCard creditCard = new CreditCard(
                "443",
                "1234567890123456",
                "VISA",
                "Joe",
                "Doe",
                LocalDate.of(2020, 10, 11));
        Payment payment = new Payment(LocalDate.of(2018, 10, 10), new BigDecimal("100.0")
                , creditCard);
        List<Payment> paymentList = Arrays.asList(payment);
        Debt debt = new Debt(new Long("1"), "111222333444", "Speed loan", new BigDecimal("150"),
                LocalDate.of(2017, 11, 10),
                paymentList);
        Debt debt2 = new Debt(new Long("1"), "111222333555", "Fast loan", new BigDecimal("160"),
                LocalDate.of(2018, 12, 11),
                paymentList);
        return new Debtor(1L,
                "Joe",
                "Doe",
                "980-112-111",
                Arrays.asList(debt, debt2));
    }

    public static PaymentPlan getExamplePaymentPlan(){
        PaymentPlan paymentPlan = new PaymentPlan();
        PlannedPayment plannedPayment = new PlannedPayment();
        plannedPayment.setUuid("111222333444");
        plannedPayment.setAmountOfRepaymentDebt(new BigDecimal("50"));

        List<PlannedPayment> plannedPayments = new ArrayList<>();
        plannedPayments.add(plannedPayment);

        paymentPlan.setMessage("Example Payment Plan Message.");
        paymentPlan.setSsn("980-122-111");
        paymentPlan.setPlannedPaymentList(plannedPayments);
        return paymentPlan;
    }
}

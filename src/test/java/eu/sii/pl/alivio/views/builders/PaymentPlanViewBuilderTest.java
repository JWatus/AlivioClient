package eu.sii.pl.alivio.views.builders;

import eu.sii.pl.alivio.model.Debtor;
import eu.sii.pl.alivio.model.PaymentPlan;
import eu.sii.pl.alivio.model.PlannedPayment;
import eu.sii.pl.alivio.providers.TestObjectsProviderUtil;
import eu.sii.pl.alivio.views.plannedpayment.PaymentPlanView;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class PaymentPlanViewBuilderTest {
    private PaymentPlan expectedPaymentPlan;
    private Debtor expectedDebtor;
    private PaymentPlanViewBuilder paymentPlanViewBuilder;

    @Before
    public void init() {
        expectedDebtor = TestObjectsProviderUtil.getExampleDebtor();
        expectedPaymentPlan = TestObjectsProviderUtil.getExamplePaymentPlan();
        paymentPlanViewBuilder = new PaymentPlanViewBuilder(expectedDebtor, expectedPaymentPlan);
    }

    @Test
    public void shouldPaymentPlanViewContainExpectedDebtorProperties() {
        //given
        PaymentPlanView paymentPlanView = paymentPlanViewBuilder.build();

        //when
        int expectedDebtListSize = expectedDebtor.getDebts().size();
        String expectedDebtorFirstName = expectedDebtor.getFirstName();
        String expectedDebtorLastName = expectedDebtor.getLastName();
        String expectedSsn = expectedDebtor.getSsn();

        //then
        assertThat(paymentPlanView.getDebtor().getDebts().size(), equalTo(expectedDebtListSize));
        assertThat(paymentPlanView.getDebtor().getFirstName(), equalTo(expectedDebtorFirstName));
        assertThat(paymentPlanView.getDebtor().getLastName(), equalTo(expectedDebtorLastName));
        assertThat(paymentPlanView.getDebtor().getSsn(), equalTo(expectedSsn));
    }

    @Test
    public void shouldPaymentPlanViewContainExpectedPaymentPlanProperties() {
        //given
        PaymentPlanView paymentPlanView = paymentPlanViewBuilder.build();

        //when
        int expectedPlannedPaymentListSize = expectedPaymentPlan.getPlannedPaymentList().size();
        String expectedMessage = expectedPaymentPlan.getMessage();
        String expectedSsn = expectedPaymentPlan.getSsn();

        //then
        assertThat(paymentPlanView.getPaymentPlan().getPlannedPaymentList().size(), equalTo(expectedPlannedPaymentListSize));
        assertThat(paymentPlanView.getPaymentPlan().getMessage(), equalTo(expectedMessage));
        assertThat(paymentPlanView.getPaymentPlan().getSsn(), equalTo(expectedSsn));
    }

    @Test
    public void shouldPaymentPlanViewContainExpectedPlannedPaymentInPaymentPlanList() {
        //given
        PaymentPlanView paymentPlanView = paymentPlanViewBuilder.build();

        //when
        PlannedPayment plannedPayment = expectedPaymentPlan.getPlannedPaymentList().get(0);
        String expectedUuid = plannedPayment.getUuid();
        BigDecimal expectedRepaymentDebt = plannedPayment.getAmountOfRepaymentDebt();

        //then
        PlannedPayment plannedPaymentFromView = paymentPlanView.getPaymentPlan().getPlannedPaymentList().get(0);
        assertThat(plannedPaymentFromView.getUuid(), equalTo(expectedUuid));
        assertThat(plannedPaymentFromView.getAmountOfRepaymentDebt(), equalTo(expectedRepaymentDebt));
    }
}
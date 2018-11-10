package alivio.views.builders;

import alivio.model.Debtor;
import alivio.providers.TestObjectsProviderUtil;
import alivio.views.debtor.DebtView;
import alivio.views.debtor.DebtorSummaryView;
import alivio.views.debtor.DebtorView;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class DebtorViewBuilderTest {
    private Debtor expectedDebtor;
    private DebtorViewBuilder debtorViewBuilder;

    @Before
    public void init() {
        expectedDebtor = TestObjectsProviderUtil.getExampleDebtor();
        debtorViewBuilder = new DebtorViewBuilder(expectedDebtor);
    }

    @Test
    public void shouldBuildPaymentPlanViewWithExpectedProperties() {
        //given
        DebtorView debtorView = debtorViewBuilder.build();

        //when
        String debtorSsn = debtorView.getDebtorSsn();
        String debtorFirstName = debtorView.getDebtorFirstName();
        String debtorLastName = debtorView.getDebtorLastName();
        List<DebtView> debtViewList = debtorView.getDebtViewList();

        //then
        assertThat(debtorSsn, equalTo(expectedDebtor.getSsn()));
        assertThat(debtorFirstName, equalTo(expectedDebtor.getFirstName()));
        assertThat(debtorLastName, equalTo(expectedDebtor.getLastName()));
        assertThat(debtViewList.size(), equalTo(expectedDebtor.getDebts().size()));
    }

    @Test
    public void shouldBuildPaymentPlanViewWithExpectedDebtSummary() {
        //given
        DebtorView debtorView = debtorViewBuilder.build();

        //when
        DebtorSummaryView debtorSummaryView = debtorView.getDebtorSummaryView();

        //then
        assertThat(debtorSummaryView.getDebtsNumberSum(), equalTo(2));
        assertThat(debtorSummaryView.getDebtsAmountSum(), equalTo(new BigDecimal("310.00")));
        assertThat(debtorSummaryView.getPayedAmountSum(), equalTo(new BigDecimal("200.00")));
        assertThat(debtorSummaryView.getRemainingAmountSum(), equalTo(new BigDecimal("110.00")));
    }
}
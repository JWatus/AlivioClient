package eu.sii.pl.alivio.utils;

import eu.sii.pl.alivio.model.Debt;
import eu.sii.pl.alivio.providers.TestObjectsProviderUtil;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class DebtCalculatorUtilTest {
    private Debt debt;

    @Before
    public void init() {
        debt = TestObjectsProviderUtil
                .getExampleDebtor()
                .getDebts()
                .stream()
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    @Test
    public void shouldCalculateDebtPaymentSum() {
        //given
        //when
        BigDecimal debtPaymentsSum = DebtCalculatorUtil.getDebtPaymentsSum(debt);

        //then
        assertThat(debtPaymentsSum, equalTo(new BigDecimal("100.00")));
    }

    @Test
    public void shouldCalculateDebtRemainingAmount() {
        //given
        //when
        BigDecimal remainingAmount = DebtCalculatorUtil.getRemainingAmount(debt);

        //then
        assertThat(remainingAmount, equalTo(new BigDecimal("50.00")));
    }
}
package eu.sii.pl.alivio.views.builders;

import eu.sii.pl.alivio.model.Debt;
import eu.sii.pl.alivio.utils.DebtCalculatorUtil;
import eu.sii.pl.alivio.views.debtor.DebtView;

public class DebtViewBuilder {
    private final Debt debt;

    public DebtViewBuilder(Debt debt) {
        this.debt = debt;
    }

    public DebtView build() {
        return new DebtView(
                debt.getId(),
                debt.getDebtName(),
                debt.getUuid(),
                debt.getRepaymentDate(),
                debt.getDebtAmount(),
                DebtCalculatorUtil.getDebtPaymentsSum(debt),
                DebtCalculatorUtil.getRemainingAmount(debt)
        );
    }
}

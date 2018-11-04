package alivio.views.builders;

import alivio.model.Debt;
import alivio.utils.DebtCalculatorUtil;
import alivio.views.debtor.DebtView;

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

package alivio.views.builders;

import alivio.model.Debtor;
import alivio.views.debtor.DebtView;
import alivio.views.debtor.DebtorSummaryView;
import alivio.views.debtor.DebtorView;

import java.util.ArrayList;
import java.util.List;

public class DebtorViewBuilder {
    private final Debtor debtor;
    private final DebtorSummaryView debtorSummaryView;
    private List<DebtView> debtorViewList;

    public DebtorViewBuilder(Debtor debtor) {
        this.debtor = debtor;
        this.debtorSummaryView = new DebtorSummaryViewBuilder(debtor).build();
        this.debtorViewList = this.createDebtsViewList();
    }

    public DebtorView build() {
        return new DebtorView(debtor, debtorViewList, debtorSummaryView);
    }

    private List<DebtView> createDebtsViewList() {
        List<DebtView> debtViewList = new ArrayList<>(debtor.getDebts().size());
        debtor.getDebts().forEach(x -> debtViewList.add(new DebtViewBuilder(x).build()));
        return debtViewList;
    }
}

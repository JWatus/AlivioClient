package eu.sii.pl.alivio.views.builders;

import eu.sii.pl.alivio.model.Debtor;
import eu.sii.pl.alivio.views.debtor.DebtView;
import eu.sii.pl.alivio.views.debtor.DebtorSummaryView;
import eu.sii.pl.alivio.views.debtor.DebtorView;

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

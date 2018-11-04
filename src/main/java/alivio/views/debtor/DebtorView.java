package alivio.views.debtor;

import alivio.model.Debtor;

import java.util.Collections;
import java.util.List;

public class DebtorView {
    private final String debtorFirstName;
    private final String debtorLastName;
    private final String debtorSsn;
    private List<DebtView> debtViewList;
    private final DebtorSummaryView debtorSummaryView;

    public String getDebtorFirstName() {
        return debtorFirstName;
    }

    public String getDebtorLastName() {
        return debtorLastName;
    }

    public String getDebtorSsn() {
        return debtorSsn;
    }

    public List<DebtView> getDebtViewList() {
        return Collections.unmodifiableList(debtViewList);
    }

    public DebtorSummaryView getDebtorSummaryView() {
        return debtorSummaryView;
    }

    public DebtorView(Debtor debtor, List<DebtView> debtViewList, DebtorSummaryView debtorSummaryView) {
        this.debtorFirstName = debtor.getFirstName();
        this.debtorLastName = debtor.getLastName();
        this.debtorSsn = debtor.getSsn();
        this.debtViewList = debtViewList;
        this.debtorSummaryView = debtorSummaryView;
    }
}



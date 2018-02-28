package com.example.asusx555l.projecttoolbar.ui.fragmets;


import android.support.v4.app.Fragment;

import com.example.asusx555l.projecttoolbar.beans.Expense;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConsumptionPage extends ExpensePage {

    @Override
    protected boolean satisfied(Expense expense) {
        return expense.isSpend();
    }
}

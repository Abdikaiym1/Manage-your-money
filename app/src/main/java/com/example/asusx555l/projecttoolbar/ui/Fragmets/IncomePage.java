package com.example.asusx555l.projecttoolbar.ui.fragmets;

import android.support.v4.app.Fragment;
import android.view.View;

import com.example.asusx555l.projecttoolbar.beans.Expense;
import com.example.asusx555l.projecttoolbar.ui.ExpensesRecyclerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class IncomePage extends ExpensePage {

    protected boolean satisfied(Expense expense) {
        return !expense.isSpend();
    }

}

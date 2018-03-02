package com.example.asusx555l.projecttoolbar.ui.fragmets;

import android.support.v4.app.Fragment;

import com.example.asusx555l.projecttoolbar.ObservableArrayList;
import com.example.asusx555l.projecttoolbar.beans.Expense;
import java.util.List;

public abstract class BasePage extends Fragment implements ObservableArrayList.OnChangeListener<Expense> {

    protected List<Expense> expenseList;
    protected ObservableArrayList<Expense> expenses;

    public void setExpenses(ObservableArrayList<Expense> expenses) {
        this.expenses = expenses;
    }

    protected abstract boolean satisfied(Expense expense);

}

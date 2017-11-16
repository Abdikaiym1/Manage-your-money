package com.example.asusx555l.projecttoolbar.ui.fragmets;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asusx555l.projecttoolbar.R;
import com.example.asusx555l.projecttoolbar.beans.Expense;
import com.example.asusx555l.projecttoolbar.ui.activities.SecondActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticsPage extends BasePage {
    private List<BigDecimal> listMoneyLeave;
    TextView textMoneyDayL;
    TextView textMoneyWeekL;
    TextView textMoneyMonthL;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics_page, container, false);
        listMoneyLeave = new ArrayList<>();
        textMoneyDayL = view.findViewById(R.id.moneyDayL);
        textMoneyWeekL = view.findViewById(R.id.moneyWeekL);
        textMoneyMonthL = view.findViewById(R.id.moneyMonthL);
        return view;
    }

    @Override
    public void getExpense(Expense expense) {
        super.getExpense(expense);
        if (expense.isSpend()) {
            listMoneyLeave.add(expense.getMoney());
            BigDecimal sumToDay = new BigDecimal(0);
            for (int i = 0; i < listMoneyLeave.size(); i++) {
                sumToDay = sumToDay.add(listMoneyLeave.get(i));
            }
            textMoneyDayL.setText(String.valueOf(sumToDay));
        }
    }
}

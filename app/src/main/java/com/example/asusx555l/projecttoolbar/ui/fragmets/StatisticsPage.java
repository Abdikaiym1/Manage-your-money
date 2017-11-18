package com.example.asusx555l.projecttoolbar.ui.fragmets;


import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asusx555l.projecttoolbar.R;
import com.example.asusx555l.projecttoolbar.beans.Expense;
import com.example.asusx555l.projecttoolbar.ui.activities.SecondActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Math.abs;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticsPage extends BasePage {

    public static final String FormatTIME = "HH";
    public static final String FormatDAY = "dd";
    public static final String FormatMonth = "MM";
    public static final String RESET = "0.00";


    private List<BigDecimal> listMoneyLeave;
    TextView textMoneyDayL;
    TextView textMoneyWeekL;
    TextView textMoneyMonthL;
    private Timer timer;
    private TimerTask timerTask;
    private Handler handler;
    private SimpleDateFormat simpleDateFormat;
    private SimpleDateFormat simpleDateFormatDay;
    private SimpleDateFormat simpleDateFormatMonth;
    private int lastDay, lastMonth;
    private BigDecimal sumToDay;
    private int count;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics_page, container, false);
        listMoneyLeave = new ArrayList<>();
        textMoneyDayL = view.findViewById(R.id.moneyDayL);
        textMoneyWeekL = view.findViewById(R.id.moneyWeekL);
        textMoneyMonthL = view.findViewById(R.id.moneyMonthL);

        simpleDateFormat = new SimpleDateFormat(FormatTIME);
        simpleDateFormatDay = new SimpleDateFormat(FormatDAY);
        simpleDateFormatMonth = new SimpleDateFormat(FormatMonth);

        timer = new Timer();
        handler = new Handler();

        timeSetIncome();
        Simple();
        //timer.schedule(timerTask, 10, 86400*1000);
        return view;
    }

    @Override
    public void getExpense(Expense expense) {
        super.getExpense(expense);
        if (expense.isSpend()) {
            listMoneyLeave.add(expense.getMoney());
            sumToDay = new BigDecimal(0);
            for (int i = 0; i < listMoneyLeave.size(); i++) {
                sumToDay = sumToDay.add(listMoneyLeave.get(i));
            }
            textMoneyDayL.setText(String.valueOf(sumToDay));
            textMoneyWeekL.setText(String.valueOf(sumToDay));
            textMoneyMonthL.setText(String.valueOf(sumToDay));
        }
    }

    public void timeSetIncome() {
        count = 0;
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        count++;
                        if (count > 1)
                            textMoneyDayL.setText(RESET);
                        if (count % 7 == 0)
                            textMoneyWeekL.setText(RESET);
                        if (count % 30 == 0)
                            textMoneyMonthL.setText(RESET);
                    }
                });
            }
        };
    }

    public void Simple() {
        int diffTime = (24 - Integer.parseInt(simpleDateFormat.format(new Date()))) * 3600;
        int curDay = Integer.parseInt(simpleDateFormatDay.format(new Date()));
        int curMonth = Integer.parseInt(simpleDateFormatMonth.format(new Date()));
        if (abs(lastDay - curDay) != 0 && lastMonth - curMonth == 0) {
            textMoneyDayL.setText(RESET);
        } else if (abs(curDay - lastDay) != 7 && lastMonth - curMonth == 0) {
            textMoneyWeekL.setText(RESET);
        } else {
            textMoneyWeekL.setText(RESET);
            textMoneyDayL.setText(RESET);
            textMoneyMonthL.setText(RESET);
        }
        timer.schedule(timerTask, diffTime, 86400);
    }

    @Override
    public void onDestroy() {
        /*currentTime = Integer.parseInt(simpleDateFormat.format(new Date()));
        int diffTime = Integer.parseInt(simpleDateFormat.format(new Date())) - currentTime;*/

        lastDay = Integer.parseInt(simpleDateFormatDay.format(new Date()));
        lastMonth = Integer.parseInt(simpleDateFormatMonth.format(new Date()));

        super.onDestroy();
    }
}

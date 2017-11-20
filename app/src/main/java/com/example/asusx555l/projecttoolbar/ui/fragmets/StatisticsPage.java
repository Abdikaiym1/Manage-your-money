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
import com.example.asusx555l.projecttoolbar.StringToIntegerDate;
import com.example.asusx555l.projecttoolbar.beans.Expense;
import com.example.asusx555l.projecttoolbar.ui.activities.SecondActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Math.E;
import static java.lang.Math.abs;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticsPage extends BasePage {

    private static final String FormatTIME = "HH";
    private static final String FormatDAY = "dd";
    private static final String FormatMonth = "MM";
    private static final String RESET = "0.00";
    private static final String DATE = "dd-MM-yyyy";


    TextView textMoneyDayL;
    TextView textMoneyWeekL;
    TextView textMoneyMonthL;

    private Timer timer;
    private TimerTask timerTask;
    private Handler handler;
    private SimpleDateFormat simpleDateFormat;
    private SimpleDateFormat simpleDateFormatDay;
    private SimpleDateFormat simpleDateFormatMonth;
    private SimpleDateFormat simpleDate;
    private int lastDay, lastMonth;

    private BigDecimal[] dmyMoneyLeave = {BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO};

    private StringToIntegerDate stringToIntegerDate = new StringToIntegerDate();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics_page, container, false);
        textMoneyDayL = view.findViewById(R.id.moneyDayL);
        textMoneyWeekL = view.findViewById(R.id.moneyWeekL);
        textMoneyMonthL = view.findViewById(R.id.moneyMonthL);

        simpleDateFormat = new SimpleDateFormat(FormatTIME);
        simpleDateFormatDay = new SimpleDateFormat(FormatDAY);
        simpleDateFormatMonth = new SimpleDateFormat(FormatMonth);
        simpleDate = new SimpleDateFormat(DATE);


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
            Log.d("TEST", simpleDate.format(new Date()));
            if (stringToIntegerDate.getDay(expense.getDate()) == Integer.parseInt(simpleDateFormatDay.format(new Date()))
                    && stringToIntegerDate.getMonth(expense.getDate()) == (Integer.parseInt(simpleDateFormatMonth.format(new Date())) - 1)
                    ) {
                dmyMoneyLeave[0] = dmyMoneyLeave[0].add(expense.getMoney());
                dmyMoneyLeave[1] = dmyMoneyLeave[1].add(expense.getMoney());
                dmyMoneyLeave[2] = dmyMoneyLeave[2].add(expense.getMoney());

                textMoneyDayL.setText(String.valueOf(dmyMoneyLeave[0]));
                textMoneyWeekL.setText(String.valueOf(dmyMoneyLeave[1]));
                textMoneyMonthL.setText(String.valueOf(dmyMoneyLeave[2]));
            } else if (stringToIntegerDate.getDay(expense.getDate()) - Integer.parseInt(simpleDateFormatDay.format(new Date())) <= 6 &&
                    stringToIntegerDate.getMonth(expense.getDate()) == (Integer.parseInt(simpleDateFormatMonth.format(new Date())) - 1)) {

                dmyMoneyLeave[1] = dmyMoneyLeave[1].add(expense.getMoney());
                dmyMoneyLeave[2] = dmyMoneyLeave[2].add(expense.getMoney());

                textMoneyWeekL.setText(String.valueOf(dmyMoneyLeave[1]));
                textMoneyMonthL.setText(String.valueOf(dmyMoneyLeave[2]));
            } else if (stringToIntegerDate.getMonth(expense.getDate())
                    == (Integer.parseInt(simpleDateFormatMonth.format(new Date())) - 1)) {

                dmyMoneyLeave[2] = dmyMoneyLeave[2].add(expense.getMoney());

                textMoneyMonthL.setText(String.valueOf(dmyMoneyLeave[2]));
            }
        }
    }

    public void timeSetIncome() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        textMoneyDayL.setText(RESET);
                        if (stringToIntegerDate.dayOfWeek(simpleDate.format(new Date())) == 2)
                            textMoneyWeekL.setText(RESET);
                        if (Integer.parseInt(simpleDateFormatDay.format(new Date())) == 1)
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
        } else if (abs(curDay - lastDay) <= 6 && lastMonth - curMonth == 0) {
            textMoneyDayL.setText(RESET);
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

        lastDay = Integer.parseInt(simpleDateFormatDay.format(new Date()));
        lastMonth = Integer.parseInt(simpleDateFormatMonth.format(new Date()));

        super.onDestroy();
    }
}

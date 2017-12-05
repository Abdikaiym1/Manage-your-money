package com.example.asusx555l.projecttoolbar.ui.fragmets;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asusx555l.projecttoolbar.R;
import com.example.asusx555l.projecttoolbar.StringToIntegerDate;
import com.example.asusx555l.projecttoolbar.ValueToCurrentValue;
import com.example.asusx555l.projecttoolbar.Valute;
import com.example.asusx555l.projecttoolbar.XMLParser;
import com.example.asusx555l.projecttoolbar.beans.Expense;
import com.example.asusx555l.projecttoolbar.ui.activities.SecondActivity;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Math.E;
import static java.lang.Math.abs;


public class StatisticsPage extends BasePage implements XMLParser.SendResult {

    private static final String FormatTIME = "HH";
    private static final String FormatDAY = "dd";
    private static final String FormatMonth = "MM";
    private static final String RESET = "0.00";
    private static final String DATE = "dd-MM-yyyy";
    private static final String USD = "USD";
    private static final String EUR = "EUR";
    private static final String RUB = "RUB";


    TextView textMoneyDayL;
    TextView textMoneyWeekL;
    TextView textMoneyMonthL;
    TextView textTitleMonth;
    TextView textTitleAllMoney;
    TextView textTitleMoneyName;
    TextView textMoneyMonthI;
    TextView textMoneyB;

    private View view;
    private Timer timer;
    private TimerTask timerTask;
    private Handler handler;
    private SimpleDateFormat simpleDateFormat;
    private SimpleDateFormat simpleDateFormatDay;
    private SimpleDateFormat simpleDateFormatMonth;
    private SimpleDateFormat simpleDate;
    private int lastDay, lastMonth;
    private RadioGroup radioGroup;
    private RadioButton radioButtonUSD;
    private RadioButton radioButtonEUR;
    private RadioButton radioButtonRUB;
    private String curVaute = RUB;

    private BigDecimal[] dmyMoneyLeave = {BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO};
    private BigDecimal allTimeMoney = BigDecimal.ZERO;
    private BigDecimal mMoneyIncome = BigDecimal.ZERO;
    private Valute[] valutes = {null, null};
    private ValueToCurrentValue convertToCurValute = new ValueToCurrentValue();

    private StringToIntegerDate stringToIntegerDate = new StringToIntegerDate();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_statistics_page, container, false);
        textMoneyDayL = view.findViewById(R.id.moneyDayL);
        textMoneyWeekL = view.findViewById(R.id.moneyWeekL);
        textMoneyMonthL = view.findViewById(R.id.moneyMonthL);
        textMoneyMonthI = view.findViewById(R.id.moneyIncomeMonth);
        textMoneyB = view.findViewById(R.id.allmoney);

        radioGroup = view.findViewById(R.id.radioGroupValute);
        radioButtonUSD = view.findViewById(R.id.radio_USDValute);
        radioButtonEUR = view.findViewById(R.id.radio_EURValute);
        radioButtonRUB = view.findViewById(R.id.radio_RUBValute);

        simpleDateFormat = new SimpleDateFormat(FormatTIME);
        simpleDateFormatDay = new SimpleDateFormat(FormatDAY);
        simpleDateFormatMonth = new SimpleDateFormat(FormatMonth);
        simpleDate = new SimpleDateFormat(DATE);

        textTitleMonth = view.findViewById(R.id.title_income);
        GradientDrawable gradientDrawable = (GradientDrawable) textTitleMonth.getBackground().mutate();
        gradientDrawable.setColor(Color.rgb(68, 204, 0));

        textTitleAllMoney = view.findViewById(R.id.title_allMoney);
        gradientDrawable = (GradientDrawable) textTitleAllMoney.getBackground().mutate();
        gradientDrawable.setColor(Color.rgb(255, 153, 153));


        timer = new Timer();
        handler = new Handler();

        timeSetIncome();
        Simple();

        return view;
    }

    @Override
    public void getExpense(Expense expense) {
        super.getExpense(expense);
        BigDecimal valueUSD = new BigDecimal(valutes[0].getValue().replace(",", "."));
        BigDecimal valueEUR = new BigDecimal(valutes[1].getValue().replace(",", "."));
        Log.e("TEST", String.valueOf(1));
        BigDecimal[] allValueMoney = new BigDecimal[]{valueUSD, valueEUR, expense.getMoney()};

        if (expense.isSpend()) {
            if (stringToIntegerDate.getDay(expense.getDate()) == Integer.parseInt(simpleDateFormatDay.format(new Date()))
                    && stringToIntegerDate.getMonth(expense.getDate()) == Integer.parseInt(simpleDateFormatMonth.format(new Date()))
                    ) {

                BigDecimal curMoney = convertToCurValute.convetValute(curVaute, allValueMoney, expense.getCurrency().name());
                Log.e("TEST", String.valueOf(curMoney));
                dmyMoneyLeave[0] = dmyMoneyLeave[0].add(curMoney);
                dmyMoneyLeave[1] = dmyMoneyLeave[1].add(curMoney);
                dmyMoneyLeave[2] = dmyMoneyLeave[2].add(curMoney);

                textMoneyDayL.setText(String.valueOf(dmyMoneyLeave[0]));
                textMoneyWeekL.setText(String.valueOf(dmyMoneyLeave[1]));
                textMoneyMonthL.setText(String.valueOf(dmyMoneyLeave[2]));
            } else if (stringToIntegerDate.getDay(expense.getDate()) - Integer.parseInt(simpleDateFormatDay.format(new Date())) <= 6 &&
                    stringToIntegerDate.getMonth(expense.getDate()) == Integer.parseInt(simpleDateFormatMonth.format(new Date()))) {

                BigDecimal curMoney = convertToCurValute.convetValute(curVaute, allValueMoney, expense.getCurrency().name());
                dmyMoneyLeave[1] = dmyMoneyLeave[1].add(curMoney);
                dmyMoneyLeave[2] = dmyMoneyLeave[2].add(curMoney);

                textMoneyWeekL.setText(String.valueOf(dmyMoneyLeave[1]));
                textMoneyMonthL.setText(String.valueOf(dmyMoneyLeave[2]));
            } else if (stringToIntegerDate.getMonth(expense.getDate())
                    == Integer.parseInt(simpleDateFormatMonth.format(new Date())) &&
                    stringToIntegerDate.getDay(expense.getDate()) - Integer.parseInt(simpleDateFormatDay.format(new Date())) > 6) {

                BigDecimal curMoney = convertToCurValute.convetValute(curVaute, allValueMoney, expense.getCurrency().name());

                dmyMoneyLeave[2] = dmyMoneyLeave[2].add(curMoney);

                textMoneyMonthL.setText(String.valueOf(dmyMoneyLeave[2]));
            }
            allTimeMoney = allTimeMoney.subtract(dmyMoneyLeave[0]);
            textMoneyB.setText(String.valueOf(allTimeMoney));
        } else {
            if (stringToIntegerDate.getDay(expense.getDate()) - Integer.parseInt(simpleDateFormatDay.format(new Date())) <= 6 &&
                    stringToIntegerDate.getMonth(expense.getDate()) == Integer.parseInt(simpleDateFormatMonth.format(new Date()))) {
                mMoneyIncome = convertToCurValute.convetValute(curVaute, allValueMoney, expense.getCurrency().name());
                textMoneyMonthI.setText(String.valueOf(mMoneyIncome));
            }
            allTimeMoney = allTimeMoney.add(mMoneyIncome);
            textMoneyB.setText(String.valueOf(allTimeMoney));
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
        int diffTime = (24 - Integer.parseInt(simpleDateFormat.format(new Date()))) * 60 * 60 * 1000;
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
        timer.schedule(timerTask, diffTime, 24 * 60 * 60 * 1000);
    }

    @Override
    public void onDestroy() {
        lastDay = Integer.parseInt(simpleDateFormatDay.format(new Date()));
        lastMonth = Integer.parseInt(simpleDateFormatMonth.format(new Date()));
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();

        XMLParser xmlParser = new XMLParser(StatisticsPage.this, simpleDate.format(new Date()));
        xmlParser.execute();
        radioButtonUSD.setOnClickListener(radioButtonClickVaute);
        radioButtonEUR.setOnClickListener(radioButtonClickVaute);
        radioButtonRUB.setOnClickListener(radioButtonClickVaute);

    }

    @Override
    public void send(Valute[] valute) {
        valutes = valute;
    }

    View.OnClickListener radioButtonClickVaute = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            BigDecimal valueUSD = new BigDecimal(valutes[0].getValue().replace(",", "."));
            BigDecimal valueEUR = new BigDecimal(valutes[1].getValue().replace(",", "."));
            BigDecimal[] valuteMoney = new BigDecimal[]{valueUSD, valueEUR};
            RadioButton radioButton = (RadioButton) v;
            boolean flag;
            BigDecimal[] bigDecimals;
            switch (radioButton.getId()) {
                case R.id.radio_USDValute:
                    bigDecimals = convertToCurValute.convertValuteRadioButtonSpend(curVaute, USD, dmyMoneyLeave, valuteMoney);
                    setTextMoneySpend(bigDecimals);
                    bigDecimals = convertToCurValute.convertValuteRadioButtonSpend(curVaute, USD, new BigDecimal[]{mMoneyIncome}, valuteMoney);
                    setTextMoneyIncome(bigDecimals);
                    bigDecimals = convertToCurValute.convertValuteRadioButtonSpend(curVaute, USD, new BigDecimal[]{allTimeMoney}, valuteMoney);
                    setTextAllMoney(bigDecimals);
                    curVaute = USD;
                    setTitleMoneyName(USD);
                    break;
                case R.id.radio_EURValute:
                    bigDecimals = convertToCurValute.convertValuteRadioButtonSpend(curVaute, EUR, dmyMoneyLeave, valuteMoney);
                    setTextMoneySpend(bigDecimals);
                    bigDecimals = convertToCurValute.convertValuteRadioButtonSpend(curVaute, EUR, new BigDecimal[]{mMoneyIncome}, valuteMoney);
                    setTextMoneyIncome(bigDecimals);
                    bigDecimals = convertToCurValute.convertValuteRadioButtonSpend(curVaute, EUR, new BigDecimal[]{allTimeMoney}, valuteMoney);
                    setTextAllMoney(bigDecimals);
                    curVaute = EUR;
                    setTitleMoneyName(EUR);
                    break;
                case R.id.radio_RUBValute:
                    bigDecimals = convertToCurValute.convertValuteRadioButtonSpend(curVaute, RUB, dmyMoneyLeave, valuteMoney);
                    setTextMoneySpend(bigDecimals);
                    bigDecimals = convertToCurValute.convertValuteRadioButtonSpend(curVaute, RUB, new BigDecimal[]{mMoneyIncome}, valuteMoney);
                    setTextMoneyIncome(bigDecimals);
                    bigDecimals = convertToCurValute.convertValuteRadioButtonSpend(curVaute, RUB, new BigDecimal[]{allTimeMoney}, valuteMoney);
                    setTextAllMoney(bigDecimals);
                    curVaute = RUB;
                    setTitleMoneyName(RUB);
                    break;
            }
        }
    };

    void setTitleMoneyName(String money) {
        textTitleMoneyName = view.findViewById(R.id.title_money);
        textTitleMoneyName.setText(money);

        textTitleMoneyName = view.findViewById(R.id.title_income_money);
        textTitleMoneyName.setText(money);

        textTitleMoneyName = view.findViewById(R.id.title_allmoney_money);
        textTitleMoneyName.setText(money);
    }

    void setTextMoneySpend(BigDecimal[] bigDecimals) {
        boolean flag = true;
        for (BigDecimal bigDecimal : bigDecimals) if (bigDecimal == null) flag = false;
        if (flag) {
            dmyMoneyLeave = bigDecimals;
            textMoneyDayL.setText(String.valueOf(bigDecimals[0]));
            textMoneyWeekL.setText(String.valueOf(bigDecimals[1]));
            textMoneyMonthL.setText(String.valueOf(bigDecimals[2]));
        }
    }

    void setTextMoneyIncome(BigDecimal[] bigDecimals) {
        boolean flag = true;
        for (BigDecimal bigDecimal : bigDecimals) if (bigDecimal == null) flag = false;
        if (flag) {
            mMoneyIncome = bigDecimals[0];
            textMoneyMonthI.setText(String.valueOf(bigDecimals[0]));
        }
    }

    void setTextAllMoney(BigDecimal[] bigDecimals) {
        boolean flag = true;
        for (BigDecimal bigDecimal : bigDecimals) if (bigDecimal == null) flag = false;
        if (flag) {
            allTimeMoney = bigDecimals[0];
            textMoneyB.setText(String.valueOf(bigDecimals[0]));
        }
    }
}

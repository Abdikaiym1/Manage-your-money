package com.example.asusx555l.projecttoolbar.ui.fragmets;


import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asusx555l.projecttoolbar.HelperForStatisticsPage;
import com.example.asusx555l.projecttoolbar.R;
import com.example.asusx555l.projecttoolbar.ValueToCurrentValue;
import com.example.asusx555l.projecttoolbar.Valute;
import com.example.asusx555l.projecttoolbar.XMLParser;
import com.example.asusx555l.projecttoolbar.beans.Expense;
import com.example.asusx555l.projecttoolbar.ui.NotifyDialogFragment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import static java.lang.Math.abs;


public class StatisticsPage extends BasePage {

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
    TextView textMoneyAll;

    private View view;
    private SimpleDateFormat simpleDate;
    private RadioGroup radioGroup;
    private RadioButton radioButtonUSD;
    private RadioButton radioButtonEUR;
    private RadioButton radioButtonRUB;
    private String curVaute = RUB;
    private enum StateXML {IOException}

    private BigDecimal[] dwmMoneyLeave = {BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO};
    private BigDecimal allTimeMoney = BigDecimal.ZERO;
    private BigDecimal mMoneyIncome = BigDecimal.ZERO;
    private ValueToCurrentValue convertToCurValute = new ValueToCurrentValue();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_statistics_page, container, false);
        textMoneyDayL = view.findViewById(R.id.moneyDayL);
        textMoneyWeekL = view.findViewById(R.id.moneyWeekL);
        textMoneyMonthL = view.findViewById(R.id.moneyMonthL);
        textMoneyMonthI = view.findViewById(R.id.moneyIncomeMonth);
        textMoneyAll = view.findViewById(R.id.allmoney);

        radioGroup = view.findViewById(R.id.radioGroupValute);
        radioButtonUSD = view.findViewById(R.id.radio_USDValute);
        radioButtonEUR = view.findViewById(R.id.radio_EURValute);
        radioButtonRUB = view.findViewById(R.id.radio_RUBValute);

        simpleDate = new SimpleDateFormat(DATE);

        textTitleMonth = view.findViewById(R.id.title_income);
        GradientDrawable gradientDrawable = (GradientDrawable) textTitleMonth.getBackground().mutate();
        gradientDrawable.setColor(Color.rgb(68, 204, 0));

        textTitleAllMoney = view.findViewById(R.id.title_allMoney);
        gradientDrawable = (GradientDrawable) textTitleAllMoney.getBackground().mutate();
        gradientDrawable.setColor(Color.rgb(255, 153, 153));

        expenseList = new ArrayList<>();

        radioButtonUSD.setOnClickListener(radioButtonClickVaute);
        radioButtonEUR.setOnClickListener(radioButtonClickVaute);
        radioButtonRUB.setOnClickListener(radioButtonClickVaute);

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.filter_elements).setVisible(false);
    }

    public void addMoneyExpense(Expense expense) {
        BigDecimal valueUSD = new BigDecimal(valutes[0].getValue().replace(",", "."));
        BigDecimal valueEUR = new BigDecimal(valutes[1].getValue().replace(",", "."));
        BigDecimal[] allValueMoney = new BigDecimal[]{valueUSD, valueEUR, expense.getMoney()};
        BigDecimal curMoney = convertToCurValute.convertValute(curVaute, allValueMoney, expense.getCurrency().name());

        HelperForStatisticsPage helperForStatisticsPage = new HelperForStatisticsPage();
        if (expense.isSpend()) {
            if (helperForStatisticsPage.defineDWM(expense.getDate()) == 1) {
                for (int i = 0; i < 3; i++) dwmMoneyLeave[i] = dwmMoneyLeave[i].add(curMoney);
            } else if (helperForStatisticsPage.defineDWM(expense.getDate()) == 2) {
                for (int i = 1; i < 3; i++) dwmMoneyLeave[i] = dwmMoneyLeave[i].add(curMoney);
            } else if (helperForStatisticsPage.defineDWM(expense.getDate()) == 3) {
                dwmMoneyLeave[2] = dwmMoneyLeave[2].add(curMoney);
            }
            textMoneyDayL.setText(String.valueOf(dwmMoneyLeave[0]));
            textMoneyWeekL.setText(String.valueOf(dwmMoneyLeave[1]));
            textMoneyMonthL.setText(String.valueOf(dwmMoneyLeave[2]));

            allTimeMoney = allTimeMoney.subtract(curMoney);
            textMoneyAll.setText(String.valueOf(allTimeMoney));
        } else {
            if (helperForStatisticsPage.defineDWM(expense.getDate()) != 0) {
                mMoneyIncome = mMoneyIncome.add(curMoney);
            }
            textMoneyMonthI.setText(String.valueOf(mMoneyIncome));

            allTimeMoney = allTimeMoney.add(curMoney);
            textMoneyAll.setText(String.valueOf(allTimeMoney));
        }

    }


    public void removeMoneyExpense(Expense expense) {
        //DWM ---- DAY WEEK MONTH
        BigDecimal valueUSD = new BigDecimal(valutes[0].getValue().replace(",", "."));
        BigDecimal valueEUR = new BigDecimal(valutes[1].getValue().replace(",", "."));
        BigDecimal[] allValueMoney = new BigDecimal[]{valueUSD, valueEUR, expense.getMoney()};
        BigDecimal curMoney = convertToCurValute.convertValute(curVaute, allValueMoney, expense.getCurrency().name());

        HelperForStatisticsPage helperForStatisticsPage = new HelperForStatisticsPage();
        if (expense.isSpend()) {
            if (helperForStatisticsPage.defineDWM(expense.getDate()) == 1) {
                for (int i = 0; i < 3; i++) dwmMoneyLeave[i] = dwmMoneyLeave[i].subtract(curMoney);
            } else if (helperForStatisticsPage.defineDWM(expense.getDate()) == 2) {
                for (int i = 1; i < 3; i++) dwmMoneyLeave[i] = dwmMoneyLeave[i].subtract(curMoney);
            } else if (helperForStatisticsPage.defineDWM(expense.getDate()) == 3) {
                dwmMoneyLeave[2] = dwmMoneyLeave[2].subtract(curMoney);
            }
            textMoneyDayL.setText(String.valueOf(dwmMoneyLeave[0]));
            textMoneyWeekL.setText(String.valueOf(dwmMoneyLeave[1]));
            textMoneyMonthL.setText(String.valueOf(dwmMoneyLeave[2]));

            allTimeMoney = allTimeMoney.add(curMoney);
            textMoneyAll.setText(String.valueOf(allTimeMoney));
        } else {
            if (helperForStatisticsPage.defineDWM(expense.getDate()) != 0) {
                mMoneyIncome = mMoneyIncome.subtract(curMoney);
            }
            textMoneyMonthI.setText(String.valueOf(mMoneyIncome));

            allTimeMoney = allTimeMoney.subtract(curMoney);
            textMoneyAll.setText(String.valueOf(allTimeMoney));
        }
    }

    @Override
    public void onItemRemoved(Expense item, int position) {
        expenseList.remove(item);
        removeMoneyExpense(item);
    }

    @Override
    public void onItemAdded(Expense item, int position) {
        expenseList.add(position, item);
        addMoneyExpense(item);
    }

    @Override
    public void onItemChanged(Expense item, Expense oldItem, int position) {
        expenseList.set(position, item);
        removeMoneyExpense(oldItem);
        addMoneyExpense(item);
    }

    @Override
    protected boolean satisfied(Expense expense) {
        return true;
    }

    View.OnClickListener radioButtonClickVaute = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            BigDecimal valueUSD = new BigDecimal(valutes[0].getValue().replace(",", "."));
            BigDecimal valueEUR = new BigDecimal(valutes[1].getValue().replace(",", "."));
            BigDecimal[] valuteMoney = new BigDecimal[]{valueUSD, valueEUR};
            RadioButton radioButton = (RadioButton) v;
            BigDecimal[] bigDecimals;
            switch (radioButton.getId()) {
                case R.id.radio_USDValute:
                    bigDecimals = convertToCurValute.convertValuteRadioButtonSpend(curVaute, USD, dwmMoneyLeave, valuteMoney);
                    setTextMoneySpend(bigDecimals);
                    bigDecimals = convertToCurValute.convertValuteRadioButtonSpend(curVaute, USD, new BigDecimal[]{mMoneyIncome}, valuteMoney);
                    setTextMoneyIncome(bigDecimals);
                    bigDecimals = convertToCurValute.convertValuteRadioButtonSpend(curVaute, USD, new BigDecimal[]{allTimeMoney}, valuteMoney);
                    setTextAllMoney(bigDecimals);
                    curVaute = USD;
                    setTitleMoneyName(USD);
                    break;
                case R.id.radio_EURValute:
                    bigDecimals = convertToCurValute.convertValuteRadioButtonSpend(curVaute, EUR, dwmMoneyLeave, valuteMoney);
                    setTextMoneySpend(bigDecimals);
                    bigDecimals = convertToCurValute.convertValuteRadioButtonSpend(curVaute, EUR, new BigDecimal[]{mMoneyIncome}, valuteMoney);
                    setTextMoneyIncome(bigDecimals);
                    bigDecimals = convertToCurValute.convertValuteRadioButtonSpend(curVaute, EUR, new BigDecimal[]{allTimeMoney}, valuteMoney);
                    setTextAllMoney(bigDecimals);
                    curVaute = EUR;
                    setTitleMoneyName(EUR);
                    break;
                case R.id.radio_RUBValute:
                    bigDecimals = convertToCurValute.convertValuteRadioButtonSpend(curVaute, RUB, dwmMoneyLeave, valuteMoney);
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
            dwmMoneyLeave = bigDecimals;
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
            textMoneyAll.setText(String.valueOf(bigDecimals[0]));
        }
    }
}

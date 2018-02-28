package com.example.asusx555l.projecttoolbar.ui.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.asusx555l.projecttoolbar.R;
import com.example.asusx555l.projecttoolbar.beans.Expense;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Objects;

public class SecondActivity extends AppCompatActivity  {


    public static final int CODE = 3434;
    public static final int BaseCODE = 123;

    private Toolbar toolbar;
    private Calendar calendar;
    private EditText editText;
    private EditText moneyEditText;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private RadioButton radioButtonSend;
    private RadioGroup radioGroupSend;
    private LinearLayout linearLayout;
    private AutoCompleteTextView autoCompleteTextView;
    private Expense curExpense;
    private boolean flag;
    private String[] TAG = {};
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_date);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        moneyEditText = (EditText) findViewById(R.id.edit_money);
        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        setupDateFun();
        setupSendFUB();

        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        radioGroupSend = (RadioGroup) findViewById(R.id.radioGroupSpend);
        linearLayout = (LinearLayout) findViewById(R.id.liner_layout);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.auto_complete);
        autoCompleteTextView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, TAG));

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });

        getDateFromBasePage();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                if (curExpense != null) {
                    Intent intent = new Intent();
                    intent.putExtra(Expense.KEY, curExpense);
                    intent.putExtra(Expense.POSITION, position);
                    intent.putExtra(Expense.MAIN_POSITION, getIntent().getIntExtra(Expense.MAIN_POSITION, 0));
                    setResult(RESULT_OK, intent);
                    finish();
                }
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getDateFromBasePage() {
        Intent intent = getIntent();
        Expense expense = (Expense) intent.getSerializableExtra(Expense.KEY);
        curExpense = expense;
        position = intent.getIntExtra(Expense.POSITION, 0);

        if (expense != null) {
            moneyEditText.setText(String.valueOf(expense.getMoney()));
            autoCompleteTextView.setText(expense.getTag());

            RadioButton radioButtonUSD = (RadioButton) findViewById(R.id.radio_USD);
            RadioButton radioButtonEUR = (RadioButton) findViewById(R.id.radio_EUR);
            RadioButton radioButtonRUB = (RadioButton) findViewById(R.id.RUB);

            if (Objects.equals(radioButtonEUR.getText().toString(), expense.getCurrency().name())) {
                radioButtonEUR.setChecked(true);
            } else if (Objects.equals(radioButtonUSD.getText().toString(), expense.getCurrency().name())) {
                radioButtonUSD.setChecked(true);
            } else {
                radioButtonRUB.setChecked(true);
            }

            RadioButton radioButtonSpend = (RadioButton) findViewById(R.id.radio_spend);
            if (expense.isSpend()) {
                flag = true;
                radioButtonSpend.setChecked(true);
            } else flag = false;

            editText.setText(expense.getDate());
        }
    }

    public void setupDateFun() { //simple test for GIT
        editText = (EditText)findViewById(R.id.dateEditText);
        calendar = Calendar.getInstance();
        editText.setText(calendar.get(Calendar.DAY_OF_MONTH) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR));

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog pickerDialog = new DatePickerDialog(SecondActivity.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        editText.setText(i2 + "-" + (i1 + 1) + "-" + i);
                        calendar.set(i, i1, i2);
                    }
                }, year, month, day);
                pickerDialog.show();
            }
        });
    }


    public void setupSendFUB () {
        FloatingActionButton FAB = (FloatingActionButton) findViewById(R.id.fab);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (moneyEditText.getText().length() == 0 || autoCompleteTextView.getText().length() == 0) {
                    if (curExpense != null) {
                        Intent intent = new Intent();
                        intent.putExtra(Expense.KEY, curExpense);
                        intent.putExtra(Expense.POSITION, position);
                        intent.putExtra(Expense.MAIN_POSITION, getIntent().getIntExtra(Expense.MAIN_POSITION, 0));
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                    finish();
                } else {
                    int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                    radioButton = (RadioButton) findViewById(checkedRadioButtonId);
                    checkedRadioButtonId = radioGroupSend.getCheckedRadioButtonId();
                    radioButtonSend = (RadioButton) findViewById(R.id.radio_spend);

                    Expense exp = new Expense();
                    exp.setCurrency(Expense.Currency.valueOf(radioButton.getText().toString()));
                    exp.setDate(editText.getText().toString());
                    exp.setMoney(BigDecimal.valueOf((Double.valueOf(moneyEditText.getText().toString()))).setScale(2, BigDecimal.ROUND_HALF_UP));
                    exp.setSpend(checkedRadioButtonId == radioButtonSend.getId());
                    exp.setTag(autoCompleteTextView.getText().toString());

                    if (exp.isSpend() != flag) {
                        exp.setFlagForChangeFragment(true);
                    } else {
                        exp.setFlagForChangeFragment(false);
                    }
                    Intent intent = new Intent();
                    intent.putExtra(Expense.KEY, exp);
                    intent.putExtra(Expense.POSITION, position);
                    intent.putExtra(Expense.MAIN_POSITION, getIntent().getIntExtra(Expense.MAIN_POSITION, 0));
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

}

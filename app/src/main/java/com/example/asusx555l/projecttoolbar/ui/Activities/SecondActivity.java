package com.example.asusx555l.projecttoolbar.ui.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.asusx555l.projecttoolbar.R;
import com.example.asusx555l.projecttoolbar.beans.Expense;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;

public class SecondActivity extends AppCompatActivity  {


    public static final int CODE = 3434;

    private Toolbar toolbar;
    private Calendar calendar;
    private EditText editText;
    private EditText moneyEditText;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private RadioButton radioButtonSend;
    private RadioGroup radioGroupSend;


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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
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
                if (moneyEditText.getText().length() == 0) {
                    finish();
                } else {
                    Intent intent = new Intent();
                    int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                    radioButton = (RadioButton) findViewById(checkedRadioButtonId);
                    checkedRadioButtonId = radioGroupSend.getCheckedRadioButtonId();
                    radioButtonSend = (RadioButton) findViewById(R.id.radio_spend);

                    Expense exp = new Expense();
                    exp.setCurrency(Expense.Currency.valueOf(radioButton.getText().toString()));
                    exp.setDate(editText.getText().toString());
                    exp.setMoney(BigDecimal.valueOf((Double.valueOf(moneyEditText.getText().toString()))).setScale(2, BigDecimal.ROUND_HALF_UP));
                    exp.setSpend(checkedRadioButtonId == radioButtonSend.getId());

                    intent.putExtra(Expense.KEY, exp);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }


}

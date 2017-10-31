package com.example.asusx555l.projecttoolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asusx555l.projecttoolbar.beans.Expense;
import com.example.asusx555l.projecttoolbar.beans.Expense2;

import java.util.Calendar;

public class SecondActivity extends AppCompatActivity  {


    public static final int CODE = 3434;

    private Toolbar toolbar;
    private Calendar calendar;
    private EditText editText;
    private EditText moneyEditText;
    private RadioGroup radioGroup;
    private RadioButton radioButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_date_2);

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
        editText.setText(calendar.get(Calendar.DAY_OF_MONTH)+"-"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.YEAR));

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog pickerDialog = new DatePickerDialog(SecondActivity.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        editText.setText(i2+"-"+i1+"-"+i);
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

                    /*Expense2 exp2 = new Expense2();
                    exp2.setDate(editText.getText().toString());
                    exp2.setMoney(Double.valueOf(moneyEditText.getText().toString()));
                    exp2.setCurrency(Expense2.Currency.valueOf(radioButton.getText().toString()));*/

                    Expense exp = new Expense();
                    exp.setCurrency(Expense.Currency.valueOf(radioButton.getText().toString()));
                    exp.setDate(editText.getText().toString());
                    exp.setMoney(Double.valueOf(moneyEditText.getText().toString()));

                    intent.putExtra("exp", exp);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }


}

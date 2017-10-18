package com.example.asusx555l.projecttoolbar;

import android.app.DatePickerDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class SecondActivity extends AppCompatActivity  {

    private Toolbar toolbar;
    private Calendar calendar;
    private EditText editText;
    private String stringMoney;
    private String stringDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_date);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        setupDateFun();
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

    public void setupTextInput(){
        TextInputLayout inputLayout = (TextInputLayout) findViewById(R.id.text_input);
        final EditText editTextOne = (EditText)findViewById(R.id.edit_money);
        editTextOne.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction() == KeyEvent.ACTION_DOWN && (i == KeyEvent.KEYCODE_ENTER)) {
                    stringMoney = editTextOne.getText().toString();
                    editTextOne.setText(stringMoney+"dd");
                }
                return false;
            }
        });
    }

    public void setupDateFun() { //simple test for GIT
        editText = (EditText)findViewById(R.id.dateEditText);
        calendar = Calendar.getInstance();
        editText.setText(calendar.get(Calendar.DAY_OF_MONTH)+"-"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.YEAR));
        stringDate = editText.getText().toString();

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog pickerDialog = new DatePickerDialog(SecondActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        editText.setText(i2+"-"+i1+"-"+i);
                        stringDate = editText.getText().toString();
                        calendar.set(i, i1, i2);
                    }
                }, year, month, day);
                pickerDialog.show();
            }
        });
    }


}

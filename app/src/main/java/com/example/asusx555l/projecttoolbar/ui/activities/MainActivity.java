package com.example.asusx555l.projecttoolbar.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.asusx555l.projecttoolbar.ObservableArrayList;
import com.example.asusx555l.projecttoolbar.Valute;
import com.example.asusx555l.projecttoolbar.XMLParser;
import com.example.asusx555l.projecttoolbar.ui.FragmentAdapter;
import com.example.asusx555l.projecttoolbar.ui.NotifyDialogFragment;
import com.example.asusx555l.projecttoolbar.ui.fragmets.BasePage;
import com.example.asusx555l.projecttoolbar.ui.fragmets.IncomePage;
import com.example.asusx555l.projecttoolbar.R;
import com.example.asusx555l.projecttoolbar.ui.fragmets.ConsumptionPage;
import com.example.asusx555l.projecttoolbar.beans.Expense;
import com.example.asusx555l.projecttoolbar.ui.fragmets.StatisticsPage;


import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements XMLParser.SendResult, NotifyDialogFragment.RateDialogListener {

    FragmentAdapter fragmentAdapter;
    ViewPager viewPager;

    private ObservableArrayList<Expense> expenseList;
    private List<BasePage> pages;
    private SimpleDateFormat simpleDate;

    private enum StateXML {IOException}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expenseList = new ObservableArrayList<>();
        simpleDate = new SimpleDateFormat("dd-MM-yyyy");

        Toolbar();
        setupFragment();
        xmlParser();
    }

    private void xmlParser() {
        XMLParser xmlParser = new XMLParser(this, simpleDate.format(new Date()));
        xmlParser.execute();
    }


    public void Toolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent i = new Intent(this, ExpanseAddActivity.class);
                startActivityForResult(i, ExpanseAddActivity.CODE);
                return true;
            case R.id.filter_elements:
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ExpanseAddActivity.CODE) {
            if (resultCode == RESULT_OK) {
                Expense expense = (Expense) data.getSerializableExtra(Expense.KEY);
                expenseList.add(expense);
                viewPager.setCurrentItem(expense.isSpend() ? 2 : 1, true);
            }
        } else {
            if (resultCode == RESULT_OK) {
                Expense expense = (Expense) data.getSerializableExtra(Expense.KEY);
                int positionExpense = data.getIntExtra(Expense.MAIN_POSITION, 0);
                expenseList.set(positionExpense, expense);
            }
        }
    }

    public void setupFragment () {
        pages = Arrays.asList(new StatisticsPage(), new IncomePage(), new ConsumptionPage());
        fragmentAdapter = new FragmentAdapter(this, getSupportFragmentManager());

        for (BasePage page : pages) {
            fragmentAdapter.addFragment(page);
            expenseList.addListener(page);
            page.setExpenses(expenseList);
        }

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(fragmentAdapter);
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        TabLayout.Tab tabHome = tabLayout.getTabAt(0);
        tabHome.setIcon(R.drawable.ic_home);
        TabLayout.Tab tabGet = tabLayout.getTabAt(1);
        tabGet.setIcon(R.drawable.ic_arrow_downward_black_24px);
        TabLayout.Tab tabSpend = tabLayout.getTabAt(2);
        tabSpend.setIcon(R.drawable.ic_arrow_upward_black_24px);

    }

    @Override
    public void send(Valute[] valutes) {
        for (BasePage page : pages) {
            page.valutes = valutes;
        }
    }

    @Override
    public void caution(String s) {
        if (Objects.equals(s, StateXML.IOException.toString())) {
            StateXML stateXML = StateXML.IOException;
        }
        openDialog();
    }

    public void openDialog() {
        NotifyDialogFragment notifyDialogFragment = new NotifyDialogFragment(this);
        notifyDialogFragment.setCancelable(false);
        notifyDialogFragment.show(getSupportFragmentManager(), "NotifyDialogFragment");

    }

    @Override
    public void applyRate(String valueUSD, String valueEUR) {
        if (valueEUR.isEmpty() || valueUSD.isEmpty()) {
            NotifyDialogFragment notifyDialogFragment = new NotifyDialogFragment(this);
            notifyDialogFragment.setCancelable(false);
            notifyDialogFragment.show(getSupportFragmentManager(), "NotifyDialogFragment");
            Toast.makeText(this, R.string.caution_alert_dialog, Toast.LENGTH_SHORT).show();
        } else {
            for (BasePage page : pages) {
                page.valutes = new Valute[]{new Valute("USD", valueUSD), new Valute("EUR", valueEUR)};
            }
        }
    }

    @Override
    public void isDefaultRate(boolean check) {
        for (BasePage page : pages) {
            if (check) page.valutes = new Valute[]{new Valute("USD", "56,4334"), new Valute("EUR", "68,8826")};
        }
    }

}

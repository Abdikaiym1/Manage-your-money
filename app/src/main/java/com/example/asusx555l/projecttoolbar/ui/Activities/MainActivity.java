package com.example.asusx555l.projecttoolbar.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.asusx555l.projecttoolbar.ui.ExpensesRecyclerViewAdapter;
import com.example.asusx555l.projecttoolbar.ui.FragmentAdapter;
import com.example.asusx555l.projecttoolbar.ui.fragmets.BasePage;
import com.example.asusx555l.projecttoolbar.ui.fragmets.IncomePage;
import com.example.asusx555l.projecttoolbar.R;
import com.example.asusx555l.projecttoolbar.ui.fragmets.ConsumptionPage;
import com.example.asusx555l.projecttoolbar.beans.Expense;
import com.example.asusx555l.projecttoolbar.ui.fragmets.StatisticsPage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FragmentAdapter fragmentAdapter;
    ViewPager viewPager;
    IncomePage incomePage;
    ConsumptionPage consumptionPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar();
        setupFragment();
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
                Intent i = new Intent(this, SecondActivity.class);
                startActivityForResult(i, SecondActivity.CODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SecondActivity.CODE) {
            if (resultCode == RESULT_OK) {
                Expense expense = (Expense) data.getSerializableExtra(Expense.KEY);
                (fragmentAdapter.getFragment(0)).getExpense(expense);
                Log.e("String", expense.getDate());
                int position = expense.isSpend() ? 1 : 0;
                if (fragmentAdapter.getFragment(position + 1) != null) {
                    fragmentAdapter.getFragment(position + 1).addNewExpense(expense);
                    viewPager.setCurrentItem(position + 1, true);
                }
            }
        } else {
            if (resultCode == RESULT_OK) {
                Expense expense = (Expense) data.getSerializableExtra(Expense.KEY);
                int positionExpense = data.getIntExtra(Expense.POSITION, 0);
                (fragmentAdapter.getFragment(0)).getExpense(expense);
                int positionFragment = expense.isSpend() ? 1 : 0;
                if (fragmentAdapter.getFragment(positionFragment + 1) != null) {
                    fragmentAdapter.getFragment(positionFragment + 1).addNewExpense(expense, positionExpense);
                    viewPager.setCurrentItem(positionFragment + 1, true);
                }
            }
        }
    }

    public void setupFragment () {
        fragmentAdapter = new FragmentAdapter(this, getSupportFragmentManager());
        fragmentAdapter.addFragment(new StatisticsPage());
        fragmentAdapter.addFragment(new IncomePage());
        fragmentAdapter.addFragment(new ConsumptionPage());
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

}

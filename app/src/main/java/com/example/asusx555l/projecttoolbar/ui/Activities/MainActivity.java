package com.example.asusx555l.projecttoolbar.ui.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.asusx555l.projecttoolbar.ui.FragmentAdapter;
import com.example.asusx555l.projecttoolbar.ui.Fragmets.IncomePage;
import com.example.asusx555l.projecttoolbar.R;
import com.example.asusx555l.projecttoolbar.ui.Fragmets.ConsumptionPage;
import com.example.asusx555l.projecttoolbar.beans.Expense;

public class MainActivity extends AppCompatActivity {

    FragmentAdapter fragmentAdapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar();
        setupFragment();
        Log.e("dauren", "onCreate");
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
                int position = expense.isSpend() ? 1 : 0;
                fragmentAdapter.getFragment(position).addNewExpense(expense);
                viewPager.setCurrentItem(position, true);
            }
        }
    }

    public void setupFragment () {
        fragmentAdapter = new FragmentAdapter(this, getSupportFragmentManager());
        fragmentAdapter.addFragment(new IncomePage());
        fragmentAdapter.addFragment(new ConsumptionPage());
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(fragmentAdapter);
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        TabLayout.Tab tabHome = tabLayout.getTabAt(0);
        TabLayout.Tab tabLike = tabLayout.getTabAt(1);
    }

}

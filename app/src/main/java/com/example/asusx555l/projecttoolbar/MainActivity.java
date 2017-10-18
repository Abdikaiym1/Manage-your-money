package com.example.asusx555l.projecttoolbar;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar();
        setupFragment();
    }

    public void Toolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
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
            case R.id.action_setting:
                Intent i = new Intent(this, SecondActivity.class);
                startActivity(i);
                return true;
            default:
            return super.onOptionsItemSelected(item);
        }
    }

    public void setupFragment () {
        FragmentAdapter fragmentAdapter = new FragmentAdapter(this, getSupportFragmentManager());
        fragmentAdapter.addFragment(new PageFragment());
        fragmentAdapter.addFragment(new SecondPageFragment());
        ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
        viewPager.setAdapter(fragmentAdapter);
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        TabLayout.Tab tabHome = tabLayout.getTabAt(0);
        TabLayout.Tab tabLike = tabLayout.getTabAt(1);

    }
}

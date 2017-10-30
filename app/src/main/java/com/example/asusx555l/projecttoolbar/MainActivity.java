package com.example.asusx555l.projecttoolbar;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.asusx555l.projecttoolbar.beans.Expense;
import com.example.asusx555l.projecttoolbar.beans.Expense2;

public class MainActivity extends AppCompatActivity {

    FragmentAdapter fragmentAdapter;

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
                //startActivity(i);
                startActivityForResult(i, SecondActivity.CODE);
                return true;
            default:
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = fragmentAdapter.getFragment(0);
        if (fragment != null) fragment.onActivityResult(requestCode, resultCode, data);
    }

    public void setupFragment () {
        fragmentAdapter = new FragmentAdapter(this, getSupportFragmentManager());
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

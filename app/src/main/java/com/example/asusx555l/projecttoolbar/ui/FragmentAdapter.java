package com.example.asusx555l.projecttoolbar.ui;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;

import com.example.asusx555l.projecttoolbar.ui.fragmets.BasePage;

import java.util.ArrayList;
import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter {
    private Context context;
    private final List<BasePage> fragmentList = new ArrayList<>();

    public FragmentAdapter (Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addFragment(BasePage fragment) {
        fragmentList.add(fragment);
    }

    public BasePage getFragment(int ind) {
        return fragmentList.get(ind);
    }
}

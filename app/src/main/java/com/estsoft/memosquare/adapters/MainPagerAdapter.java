package com.estsoft.memosquare.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by sun on 2016-10-18.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragments = new ArrayList<>();
    private final List<String> mFragmentTitles = new ArrayList<>();

    public MainPagerAdapter(FragmentManager fm){
        super(fm);
    }

    public void addFragment(Fragment fragment, String title)
    {
        mFragments.add(fragment);
        mFragmentTitles.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return mFragmentTitles.get(position);
    }

    @Override
    public Fragment getItem(int position)
    {
        return mFragments.get(position);
    }

    @Override
    public int getCount()
    {
        return mFragments.size();
    }
}

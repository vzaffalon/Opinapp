package com.opinnapp.opinnapp.tabholder.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.opinnapp.opinnapp.tabholder.Fragments.PopularFragment;
import com.opinnapp.opinnapp.tabholder.Fragments.RandomFragment;

/**
 * Created by vzaffalon on 08/05/17.
 */

public class HomeFragmentPagerAdapter extends FragmentPagerAdapter {

    private static int NUM_ITEMS = 2;

    public HomeFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return PopularFragment.newInstance();
            case 1:
                return RandomFragment.newInstance();
            default:
                return null;
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "POPULAR";
            case 1:
                return "ALEATÓRIO";
        }

        return "Page " + position;
    }
}

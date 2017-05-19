package com.opinnapp.opinnapp.tabholder.explore;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.opinnapp.opinnapp.tabholder.explore.tabs.PerfilsFragment;
import com.opinnapp.opinnapp.tabholder.explore.tabs.TagsFragment;
import com.opinnapp.opinnapp.tabholder.home.tabs.PopularFragment;
import com.opinnapp.opinnapp.tabholder.home.tabs.RandomFragment;

/**
 * Created by vzaffalon on 08/05/17.
 */

public class ExploreFragmentPagerAdapter extends FragmentPagerAdapter {

    private static int NUM_ITEMS = 2;

    public ExploreFragmentPagerAdapter(FragmentManager fragmentManager) {
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
                return TagsFragment.newInstance();
            case 1:
                return PerfilsFragment.newInstance();
            default:
                return null;
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "TAGS";
            case 1:
                return "PERFIS";
        }

        return "Page " + position;
    }
}

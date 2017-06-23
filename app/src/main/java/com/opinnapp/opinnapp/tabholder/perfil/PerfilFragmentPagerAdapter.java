package com.opinnapp.opinnapp.tabholder.perfil;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.opinnapp.opinnapp.tabholder.perfil.tabs.QuestionsFragment;
import com.opinnapp.opinnapp.tabholder.perfil.tabs.SavedFragment;

/**
 * Created by vzaffalon on 08/05/17.
 */

public class PerfilFragmentPagerAdapter extends FragmentPagerAdapter {

    private static int NUM_ITEMS = 2;

    public PerfilFragmentPagerAdapter(FragmentManager fragmentManager) {
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
                return QuestionsFragment.newInstance();
            case 1:
                return SavedFragment.newInstance();
            default:
                return null;
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "PERGUNTAS";
            case 1:
                return "SALVOS";
        }

        return "Page " + position;
    }
}

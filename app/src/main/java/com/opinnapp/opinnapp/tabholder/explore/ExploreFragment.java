package com.opinnapp.opinnapp.tabholder.explore;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opinnapp.opinnapp.R;
import com.opinnapp.opinnapp.tabholder.home.HomeFragmentPagerAdapter;

/**
 * Created by vzaffalon on 11/05/17.
 */

public class ExploreFragment extends Fragment {
    private View view;

    // newInstance constructor for creating fragment with arguments
    public static ExploreFragment newInstance() {
        ExploreFragment fragment = new ExploreFragment();
        return fragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_explore, container, false);
        setUpTabBar();
        return view;
    }

    private void setUpTabBar(){
        ExploreFragmentPagerAdapter adapter = new ExploreFragmentPagerAdapter(getChildFragmentManager());
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}

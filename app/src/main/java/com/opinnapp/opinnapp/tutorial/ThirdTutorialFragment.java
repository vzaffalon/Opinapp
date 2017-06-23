package com.opinnapp.opinnapp.tutorial;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opinnapp.opinnapp.R;

/**
 * Created by vzaffalon on 22/06/17.
 */

public class ThirdTutorialFragment extends android.support.v4.app.Fragment{

    // newInstance constructor for creating fragment with arguments
    public static ThirdTutorialFragment newInstance() {
        ThirdTutorialFragment fragmentFirst = new ThirdTutorialFragment();
        return fragmentFirst;
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
        View view = inflater.inflate(R.layout.fragment_third_tutorial, container, false);
        return view;
    }
}

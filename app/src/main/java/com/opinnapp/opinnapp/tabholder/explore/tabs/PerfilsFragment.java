package com.opinnapp.opinnapp.tabholder.explore.tabs;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opinnapp.opinnapp.R;
import com.opinnapp.opinnapp.tabholder.home.tabs.PopularFragment;

/**
 * Created by vzaffalon on 19/05/17.
 */

public class PerfilsFragment extends Fragment {

    private RecyclerView recyclerView;
    private Context context;
    private View view;

    // newInstance constructor for creating fragment with arguments
    public static PerfilsFragment newInstance() {
        PerfilsFragment fragment = new PerfilsFragment();
        return fragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_perfils, container, false);
        context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_perfils_recycler);
        return view;
    }

}

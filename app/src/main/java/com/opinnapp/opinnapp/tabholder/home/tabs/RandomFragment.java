package com.opinnapp.opinnapp.tabholder.home.tabs;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opinnapp.opinnapp.R;
import com.opinnapp.opinnapp.models.OAStory;
import com.opinnapp.opinnapp.models.OAStoryMultiChoiceImages;
import com.opinnapp.opinnapp.models.OAStoryTextOnly;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vzaffalon on 08/05/17.
 */

public class RandomFragment extends Fragment {
    private RecyclerView recyclerView;
    private Context context;
    private List<OAStory> stories;
    private View view;


    // newInstance constructor for creating fragment with arguments
    public static RandomFragment newInstance() {
        RandomFragment fragment = new RandomFragment();
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
        view = inflater.inflate(R.layout.fragment_popular, container, false);
        context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_home_recycler);

        generateStories();
        mountRecycler();

        return view;
    }

    private void mountRecycler() {
        if (stories != null) {
            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);
            //recyclerView.setAdapter(new OAStoriesAdapter(stories, context));
        }
    }

    private void generateStories () {
        stories = new ArrayList<>();
        stories.add(new OAStoryTextOnly());
        stories.add(new OAStoryMultiChoiceImages());
        stories.add(new OAStoryTextOnly());
        stories.add(new OAStoryMultiChoiceImages());
        stories.add(new OAStoryTextOnly());
        stories.add(new OAStoryTextOnly());
        stories.add(new OAStoryTextOnly());
        stories.add(new OAStoryTextOnly());
        stories.add(new OAStoryTextOnly());
        stories.add(new OAStoryTextOnly());
        stories.add(new OAStoryTextOnly());
    }


}
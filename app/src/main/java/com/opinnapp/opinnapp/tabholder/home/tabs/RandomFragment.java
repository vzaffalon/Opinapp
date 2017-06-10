package com.opinnapp.opinnapp.tabholder.home.tabs;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.opinnapp.opinnapp.R;
import com.opinnapp.opinnapp.models.OADatabase;
import com.opinnapp.opinnapp.models.OAFirebaseCallback;
import com.opinnapp.opinnapp.models.OAStory;
import com.opinnapp.opinnapp.models.OAStoryMultiChoiceImages;
import com.opinnapp.opinnapp.models.OAStoryTextOnly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import link.fls.swipestack.SwipeStack;

/**
 * Created by vzaffalon on 08/05/17.
 */

public class RandomFragment extends Fragment {
    private Context context;
    private List<OAStory> stories;
    private View view;
    private SwipeStack swipeStack;
    private BaseAdapter baseAdapter;


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
        view = inflater.inflate(R.layout.fragment_random, container, false);
        context = view.getContext();
        stories = new ArrayList<>();
        mountSwipeView();
        getStories();
        return view;
    }

    private void mountSwipeView(){
        if(swipeStack == null) {
            swipeStack = (SwipeStack) view.findViewById(R.id.swipeStack);
        }
        baseAdapter = new SwipeCardsAdapter(stories,getContext());
        swipeStack.setAdapter(baseAdapter);
    }

    private void getStories() {
        OADatabase.getAllStories(new OAFirebaseCallback() {
            @Override
            public void onSuccess(Object object) {

                List<OAStory> storiesAux = (List<OAStory>) object;
                for (int i=0;i<storiesAux.size();i++){
                    stories.add(storiesAux.get(i));
                }

                //gambiarra pra setar os users e comments
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Collections.reverse(stories);
                        baseAdapter.notifyDataSetChanged();
                    }
                }, 1000);
            }

            @Override
            public void onFailure(DatabaseError databaseError) {
                Toast.makeText(context, databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
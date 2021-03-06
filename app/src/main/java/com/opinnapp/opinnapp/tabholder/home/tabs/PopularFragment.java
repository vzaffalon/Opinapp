package com.opinnapp.opinnapp.tabholder.home.tabs;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.opinnapp.opinnapp.R;
import com.opinnapp.opinnapp.adapters.OALoadingStoryAdapter;
import com.opinnapp.opinnapp.adapters.OAStoriesAdapter;
import com.opinnapp.opinnapp.models.OADatabase;
import com.opinnapp.opinnapp.models.OAFirebaseCallback;
import com.opinnapp.opinnapp.models.OAStory;

import java.util.Collections;
import java.util.List;

/**
 * Created by vzaffalon on 08/05/17.
 */

public class PopularFragment extends Fragment {
    private SwipeRefreshLayout swipeContainer;
    private RecyclerView recyclerView;
    private Context context;
    private List<OAStory> stories;
    private View view;
    private boolean isLoading = false;


    // newInstance constructor for creating fragment with arguments
    public static PopularFragment newInstance() {
        PopularFragment fragment = new PopularFragment();
        return fragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getStories();
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_popular, container, false);
        context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_home_recycler);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getStories();
            }
        });

        mountRecycler();

        return view;
    }

    private void getStories() {
        isLoading = true;

        OADatabase.getAllStories(new OAFirebaseCallback() {
            @Override
            public void onSuccess(Object object) {
                stories = (List<OAStory>) object;

                //gambiarra pra setar os users e comments
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        isLoading = false;
                        swipeContainer.setRefreshing(false);

                        Collections.reverse(stories);
                        mountRecycler();
                    }
                }, 3000);
            }

            @Override
            public void onFailure(DatabaseError databaseError) {
                isLoading = false;
                swipeContainer.setRefreshing(false);

                mountRecycler();
                Toast.makeText(context, databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void mountRecycler() {
        if (stories != null) {
            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(new OAStoriesAdapter(stories, context));
        }
        else if (isLoading) {
            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(new OALoadingStoryAdapter());
        }
        else {
            //mostrar recycler vazia
        }
    }
}

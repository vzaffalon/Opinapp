package com.opinnapp.opinnapp.tabholder.perfil.tabs;

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
import com.opinnapp.opinnapp.tabholder.OAApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vzaffalon on 22/06/17.
 */

public class SavedFragment extends Fragment {
    private SwipeRefreshLayout swipeContainer;
    private RecyclerView recyclerView;
    private Context context;
    private List<OAStory> stories;
    private View view;
    private boolean isLoading = false;


    // newInstance constructor for creating fragment with arguments
    public static SavedFragment newInstance() {
        SavedFragment fragment = new SavedFragment();
        return fragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stories = new ArrayList<>();
        getStories();
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_perfil_saved, container, false);
        context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_perfil_saved_recycler);
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
        stories.clear();

        OADatabase.getBookmarksFromUser(OAApplication.getUser(), new OAFirebaseCallback() {
            @Override
            public void onSuccess(Object object) {
                isLoading = false;
                swipeContainer.setRefreshing(false);

                OAStory story = (OAStory) object;
                stories.add(story);

                //gambiarra pra setar os users e comments
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        mountRecycler();
                    }
                }, 2000);
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

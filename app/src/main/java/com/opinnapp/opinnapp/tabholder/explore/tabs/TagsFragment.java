package com.opinnapp.opinnapp.tabholder.explore.tabs;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.opinnapp.opinnapp.R;
import com.opinnapp.opinnapp.adapters.OAStoriesAdapter;
import com.opinnapp.opinnapp.models.OAStory;
import com.opinnapp.opinnapp.models.OAStoryMultiChoiceImages;
import com.opinnapp.opinnapp.models.OAStoryTextOnly;
import com.opinnapp.opinnapp.tabholder.home.HomeFragment;
import com.opinnapp.opinnapp.tabholder.home.tabs.PopularFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vzaffalon on 19/05/17.
 */

public class TagsFragment extends Fragment {

    private RecyclerView recyclerView;
    private Context context;
    private View view;
    private List<Tag> tags;

    // newInstance constructor for creating fragment with arguments
    public static TagsFragment newInstance() {
        TagsFragment fragment = new TagsFragment();
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
        view = inflater.inflate(R.layout.fragment_tags, container, false);
        context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_tags_recycler);
        generateTags();
        mountRecycler();
        return view;
    }

    private void mountRecycler() {
        if (tags != null) {
            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(new TagsAdapter(tags, context,new TagsAdapter.OnItemClickListener() {
                @Override public void onItemClick(Tag item) {
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, HomeFragment.newInstance());
                    transaction.commit();
                }
            }));
        }
    }

    private void generateTags () {
        tags = new ArrayList<>();
        tags.add(new Tag("#política","3500","1"));
        tags.add(new Tag("#presentes","500","2"));
        tags.add(new Tag("#roupas","200","3"));
        tags.add(new Tag("#memes","50","4"));
        tags.add(new Tag("#filmes","35","5"));
        tags.add(new Tag("#conselhos","30","6"));
        tags.add(new Tag("#comidas","20","7"));
        tags.add(new Tag("#passatempo","5","8"));
    }


}

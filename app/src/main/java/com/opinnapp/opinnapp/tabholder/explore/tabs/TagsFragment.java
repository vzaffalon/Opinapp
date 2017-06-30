package com.opinnapp.opinnapp.tabholder.explore.tabs;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.opinnapp.opinnapp.R;
import com.opinnapp.opinnapp.adapters.OAStoriesAdapter;
import com.opinnapp.opinnapp.models.OAFirebaseCallback;
import com.opinnapp.opinnapp.models.OAStory;
import com.opinnapp.opinnapp.models.OAStoryMultiChoiceImages;
import com.opinnapp.opinnapp.models.OAStoryTextOnly;
import com.opinnapp.opinnapp.models.OAUser;
import com.opinnapp.opinnapp.tabholder.home.HomeFragment;
import com.opinnapp.opinnapp.tabholder.home.tabs.PopularFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by vzaffalon on 19/05/17.
 */

public class TagsFragment extends Fragment {

    private RecyclerView recyclerView;
    private Context context;
    private View view;
    private List<Tag> tags;
    private int ranking = 1;
    private int numberOfPosts = 12;

    // newInstance constructor for creating fragment with arguments
    public static TagsFragment newInstance() {
        TagsFragment fragment = new TagsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu, menu);
        setUpSearchMenu(menu);

        MenuItem menuItem = menu.findItem(R.id.menu_search);

        MenuItemCompat.setOnActionExpandListener(menuItem,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem menuItem) {
                        // Return true to allow the action view to expand
                        final ImageView toolbar_logo = (ImageView) getActivity().findViewById(R.id.toolbar_logo);
                        toolbar_logo.setVisibility(View.GONE);
                        return true;
                    }
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                        // When the action view is collapsed, reset the query
                        final ImageView toolbar_logo = (ImageView) getActivity().findViewById(R.id.toolbar_logo);
                        toolbar_logo.setVisibility(View.VISIBLE);
                        // Return true to allow the action view to collapse
                        return true;
                    }
                });
    }
    private void setUpSearchMenu(Menu menu) {
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                List<Tag> tagsAux = new ArrayList<Tag>();

                for (int i=0;i<tags.size();i++){
                    if(tags.get(i).getTagName().contains(query)){
                        tagsAux.add(tags.get(i));
                    }
                }

                recyclerView.setAdapter(new TagsAdapter(tagsAux, context,new TagsAdapter.OnItemClickListener() {
                    @Override public void onItemClick(Tag item) {
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, HomeFragment.newInstance());
                        transaction.commit();
                    }
                }));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tags, container, false);
        context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_tags_recycler);
        generateTags();
        mountRecycler();
        getStorysFromFirebase();
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
    }


    private void getStorysFromFirebase(){
            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("story");

            usersRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    OAStory story = dataSnapshot.getValue(OAStory.class);
                    getTagsFromStory(story.getId());
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

    private void getTagsFromStory(String storyId){
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("story/" + storyId + "/tags");

        usersRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String tagAux = dataSnapshot.getValue().toString();
                Tag tagObject = new Tag(tagAux,Integer.toString(numberOfPosts),Integer.toString(ranking));
                tags.add(tagObject);
                recyclerView.getAdapter().notifyDataSetChanged();
                ranking = ranking + 1;
                if(numberOfPosts > 1) {
                    numberOfPosts = numberOfPosts - 1;
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


}

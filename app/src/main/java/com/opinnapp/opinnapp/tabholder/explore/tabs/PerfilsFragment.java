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

import com.google.firebase.database.DatabaseError;
import com.opinnapp.opinnapp.R;
import com.opinnapp.opinnapp.models.OADatabase;
import com.opinnapp.opinnapp.models.OAFirebaseCallback;
import com.opinnapp.opinnapp.models.OAUser;
import com.opinnapp.opinnapp.tabholder.home.HomeFragment;
import com.opinnapp.opinnapp.tabholder.perfil.PerfilFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vzaffalon on 19/05/17.
 */

public class PerfilsFragment extends Fragment {

    private RecyclerView recyclerView;
    private Context context;
    private View view;
    private List<OAUser> perfils;
    private ImageView toolbar_logo;

    // newInstance constructor for creating fragment with arguments
    public static PerfilsFragment newInstance() {
        PerfilsFragment fragment = new PerfilsFragment();
        return fragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        perfils = new ArrayList<>();
        getUsers();
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu, menu);
        toolbar_logo = (ImageView) getActivity().findViewById(R.id.toolbar_logo);
        setUpSearchMenu(menu);

        MenuItem menuItem = menu.findItem(R.id.menu_search);

        MenuItemCompat.setOnActionExpandListener(menuItem,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem menuItem) {
                        // Return true to allow the action view to expand
                        toolbar_logo.setVisibility(View.INVISIBLE);
                        return true;
                    }
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                        // When the action view is collapsed, reset the query
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
        final SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                List<OAUser> perfilsAux = new ArrayList<OAUser>();

                for (int i=0;i<perfils.size();i++){
                    if(perfils.get(i).getfName().toLowerCase().contains(query.toLowerCase()) || perfils.get(i).getlName().toLowerCase().contains(query.toLowerCase())){
                        perfilsAux.add(perfils.get(i));
                    }
                }

                recyclerView.setAdapter(new PerfilAdapter(perfilsAux, context,new PerfilAdapter.OnItemClickListener() {
                    @Override public void onItemClick(OAUser item) {
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, PerfilFragment.newInstance());
                        transaction.commit();
                    }
                }));
                toolbar_logo.setVisibility(View.VISIBLE);
                searchView.clearFocus();
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
        view = inflater.inflate(R.layout.fragment_perfils, container, false);
        context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_perfils_recycler);
        mountRecycler();
        return view;
    }

    private void mountRecycler() {
        if (perfils != null) {
            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(new PerfilAdapter(perfils, context,new PerfilAdapter.OnItemClickListener() {
                @Override public void onItemClick(OAUser item) {
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, PerfilFragment.newInstance());
                    transaction.commit();
                }
            }));
        }
    }

    private void getUsers(){
        OADatabase.getAllUsers(new OAFirebaseCallback() {
            @Override
            public void onSuccess(Object object) {
                perfils.add((OAUser) object);
                if (recyclerView != null)
                    recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(DatabaseError databaseError) {

            }
        });
    }
}
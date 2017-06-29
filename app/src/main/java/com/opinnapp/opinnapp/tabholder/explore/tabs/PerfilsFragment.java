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

import com.google.firebase.database.DatabaseError;
import com.opinnapp.opinnapp.R;
import com.opinnapp.opinnapp.models.OADatabase;
import com.opinnapp.opinnapp.models.OAFirebaseCallback;
import com.opinnapp.opinnapp.models.OAUser;
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
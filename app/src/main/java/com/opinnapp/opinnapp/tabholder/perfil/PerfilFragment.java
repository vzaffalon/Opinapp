package com.opinnapp.opinnapp.tabholder.perfil;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.opinnapp.opinnapp.R;
import com.opinnapp.opinnapp.models.OADatabase;
import com.opinnapp.opinnapp.models.OAFirebaseCallback;
import com.opinnapp.opinnapp.models.OAUser;
import com.opinnapp.opinnapp.tabholder.home.HomeFragmentPagerAdapter;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by vzaffalon on 11/05/17.
 */

public class PerfilFragment extends Fragment {

    private CircleImageView perfil_image;
    private TextView perfil_nickname,perfil_name;
    private View view;

    // newInstance constructor for creating fragment with arguments
    public static PerfilFragment newInstance() {
        PerfilFragment fragment = new PerfilFragment();
        return fragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
        setPerfilLayout();
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_perfil, container, false);
        setUpTabBar();
        //TODO: ARRUMAR GAMBIARRA USAR UM ID REAL
        getUserWithID("-Km-7lXAsUf_M7neaD_2");
        return view;
    }

        public void getUserWithID(String id) {
            OADatabase.getUserWithID(id, new OAFirebaseCallback() {
                @Override
                public void onSuccess(Object object) {
                    OAUser user = (OAUser) object;
                    System.out.println("User " + user.getUrl() +  " founded with success");
                    Picasso.with(getContext()).load(user.getImagePath()).into(perfil_image);
                    perfil_nickname.setText(user.getUrl());
                    perfil_name.setText(user.getName());
                }

                @Override
                public void onFailure(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }

        private void setPerfilLayout(){
            perfil_image = (CircleImageView) view.findViewById(R.id.cell_perfil_picture);
            perfil_nickname = (TextView) view.findViewById(R.id.cell_perfil_nickname);
            perfil_name = (TextView) view.findViewById(R.id.cell_perfil_name);
        }

    private void setUpTabBar(){
        PerfilFragmentPagerAdapter adapter = new PerfilFragmentPagerAdapter(getChildFragmentManager());
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(getResources().getDrawable(R.drawable.ic_list_white));
        tabLayout.getTabAt(1).setIcon(getResources().getDrawable(R.drawable.ic_bookmark_white));
    }
}

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
import android.widget.TextView;

import com.opinnapp.opinnapp.R;
import com.opinnapp.opinnapp.models.OAUser;
import com.opinnapp.opinnapp.tabholder.OAApplication;
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
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_perfil, container, false);
        setUpTabBar();
        setPerfilLayout();

        OAUser loggedUser = OAApplication.getUser();
        if (loggedUser != null) {
            Picasso.with(getContext()).load(loggedUser.getImagePath()).into(perfil_image);
            perfil_nickname.setText(loggedUser.getEmail());
            perfil_name.setText(loggedUser.getfName() + " " + loggedUser.getlName());
        }
        else {
            perfil_name.setText("Você não está logado");
            perfil_nickname.setText("Faça login para ter acesso a todas as funcionalidades.");
        }
        return view;
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

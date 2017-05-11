package com.opinnapp.opinnapp.tabholder;

import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import com.opinnapp.opinnapp.R;
import com.opinnapp.opinnapp.tabholder.explore.ExploreFragment;
import com.opinnapp.opinnapp.tabholder.home.HomeFragment;
import com.opinnapp.opinnapp.tabholder.myquestions.MyQuestionsFragment;
import com.opinnapp.opinnapp.tabholder.newquestion.NewPostFragment;
import com.opinnapp.opinnapp.tabholder.perfil.PerfilFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolBar();
        setUpBottomBar();
    }

    private void setFragmentContainer(){
        if (findViewById(R.id.fragment_container) != null) {
            HomeFragment firstFragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
    }


    private void setUpBottomBar(){
        setFragmentContainer();

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_home) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, HomeFragment.newInstance());
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                if(tabId == R.id.tab_explore){
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, ExploreFragment.newInstance());
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                if(tabId == R.id.tab_add_question){
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, NewPostFragment.newInstance());
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                if(tabId == R.id.tab_questions){
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, MyQuestionsFragment.newInstance());
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                if(tabId == R.id.tab_perfil){
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, PerfilFragment.newInstance());
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });
    }

    private void setUpToolBar(){
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Opinapp");
        myToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(myToolbar);
    }
}

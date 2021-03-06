package com.opinnapp.opinnapp.tabholder;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.opinnapp.opinnapp.R;
import com.opinnapp.opinnapp.login.LoginActivity;
import com.opinnapp.opinnapp.tabholder.explore.ExploreFragment;
import com.opinnapp.opinnapp.tabholder.home.HomeFragment;
import com.opinnapp.opinnapp.tabholder.newquestion.NewQuestionActivity;
import com.opinnapp.opinnapp.tabholder.notifications.NotificationsFragment;
import com.opinnapp.opinnapp.tabholder.perfil.PerfilFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setUpToolBar();
        setUpBottomBar();
        setAddStoryButton();
    }

    private void setAddStoryButton(){
        ImageButton  new_story_button = (ImageButton) findViewById(R.id.new_story_button);
        new_story_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),NewQuestionActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        //moveTaskToBack(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout:
                // Red item was selected
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                OAApplication.setUser(null);
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

        final BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);

        //SET ICONS SIZE
        bottomBar.getTabWithId(R.id.tab_home).findViewById(R.id.bb_bottom_bar_icon).setScaleX(0.85f);
        bottomBar.getTabWithId(R.id.tab_home).findViewById(R.id.bb_bottom_bar_icon).setScaleY(0.85f);

        bottomBar.getTabWithId(R.id.tab_explore).findViewById(R.id.bb_bottom_bar_icon).setScaleX(0.85f);
        bottomBar.getTabWithId(R.id.tab_explore).findViewById(R.id.bb_bottom_bar_icon).setScaleY(0.85f);

        bottomBar.getTabWithId(R.id.tab_add_question).findViewById(R.id.bb_bottom_bar_icon).setScaleX(1.0f);
        bottomBar.getTabWithId(R.id.tab_add_question).findViewById(R.id.bb_bottom_bar_icon).setScaleY(1.0f);

        bottomBar.getTabWithId(R.id.tab_notifications).findViewById(R.id.bb_bottom_bar_icon).setScaleX(0.85f);
        bottomBar.getTabWithId(R.id.tab_notifications).findViewById(R.id.bb_bottom_bar_icon).setScaleY(0.85f);

        bottomBar.getTabWithId(R.id.tab_perfil).findViewById(R.id.bb_bottom_bar_icon).setScaleX(0.85f);
        bottomBar.getTabWithId(R.id.tab_perfil).findViewById(R.id.bb_bottom_bar_icon).setScaleY(0.85f);

        //SET TAB FUNCTIONALITIES
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (tabId == R.id.tab_home) {

                    bottomBar.getTabWithId(R.id.tab_home).findViewById(R.id.bb_bottom_bar_icon).setBackgroundResource(R.drawable.ic_home_filled);
                    bottomBar.getTabWithId(R.id.tab_explore).findViewById(R.id.bb_bottom_bar_icon).setBackgroundResource(R.drawable.ic_explore);
                    bottomBar.getTabWithId(R.id.tab_notifications).findViewById(R.id.bb_bottom_bar_icon).setBackgroundResource(R.drawable.ic_bell);
                    bottomBar.getTabWithId(R.id.tab_perfil).findViewById(R.id.bb_bottom_bar_icon).setBackgroundResource(R.drawable.ic_perfil);

                    transaction.replace(R.id.fragment_container, HomeFragment.newInstance());
                    transaction.commit();
                }
                if(tabId == R.id.tab_explore){


                    bottomBar.getTabWithId(R.id.tab_home).findViewById(R.id.bb_bottom_bar_icon).setBackgroundResource(R.drawable.ic_home);
                    bottomBar.getTabWithId(R.id.tab_explore).findViewById(R.id.bb_bottom_bar_icon).setBackgroundResource(R.drawable.ic_explore_filled);
                    bottomBar.getTabWithId(R.id.tab_notifications).findViewById(R.id.bb_bottom_bar_icon).setBackgroundResource(R.drawable.ic_bell);
                    bottomBar.getTabWithId(R.id.tab_perfil).findViewById(R.id.bb_bottom_bar_icon).setBackgroundResource(R.drawable.ic_perfil);

                    transaction.replace(R.id.fragment_container, ExploreFragment.newInstance());
                    transaction.commit();
                }
                if(tabId == R.id.tab_add_question){
                    //do nothing this is implemented as a button
                }
                if(tabId == R.id.tab_notifications){

                    bottomBar.getTabWithId(R.id.tab_home).findViewById(R.id.bb_bottom_bar_icon).setBackgroundResource(R.drawable.ic_home);
                    bottomBar.getTabWithId(R.id.tab_explore).findViewById(R.id.bb_bottom_bar_icon).setBackgroundResource(R.drawable.ic_explore);
                    bottomBar.getTabWithId(R.id.tab_notifications).findViewById(R.id.bb_bottom_bar_icon).setBackgroundResource(R.drawable.ic_bell_filled);
                    bottomBar.getTabWithId(R.id.tab_perfil).findViewById(R.id.bb_bottom_bar_icon).setBackgroundResource(R.drawable.ic_perfil);

                    transaction.replace(R.id.fragment_container, NotificationsFragment.newInstance());
                    transaction.commit();
                }
                if(tabId == R.id.tab_perfil){

                    bottomBar.getTabWithId(R.id.tab_home).findViewById(R.id.bb_bottom_bar_icon).setBackgroundResource(R.drawable.ic_home);
                    bottomBar.getTabWithId(R.id.tab_explore).findViewById(R.id.bb_bottom_bar_icon).setBackgroundResource(R.drawable.ic_explore);
                    bottomBar.getTabWithId(R.id.tab_notifications).findViewById(R.id.bb_bottom_bar_icon).setBackgroundResource(R.drawable.ic_bell);
                    bottomBar.getTabWithId(R.id.tab_perfil).findViewById(R.id.bb_bottom_bar_icon).setBackgroundResource(R.drawable.ic_perfil_filled);

                    transaction.replace(R.id.fragment_container, PerfilFragment.newInstance());
                    transaction.commit();
                }
            }
        });

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (tabId == R.id.tab_home) {

                    transaction.replace(R.id.fragment_container, HomeFragment.newInstance());
                    transaction.commit();
                }
                if(tabId == R.id.tab_explore){
                    transaction.replace(R.id.fragment_container, ExploreFragment.newInstance());
                    transaction.commit();
                }
                if(tabId == R.id.tab_add_question){
                   //do nothing implemented as button
                }
                if(tabId == R.id.tab_notifications){
                    transaction.replace(R.id.fragment_container, NotificationsFragment.newInstance());
                    transaction.commit();
                }
                if(tabId == R.id.tab_perfil){
                    transaction.replace(R.id.fragment_container, PerfilFragment.newInstance());
                    transaction.commit();
                }
            }
        });
    }

    //for aplying font
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void setUpToolBar(){
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("");
        myToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(myToolbar);
    }
}

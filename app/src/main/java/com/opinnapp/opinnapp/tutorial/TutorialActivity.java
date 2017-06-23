package com.opinnapp.opinnapp.tutorial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.opinnapp.opinnapp.R;
import com.opinnapp.opinnapp.tabholder.MainActivity;
import com.rd.PageIndicatorView;

/**
 * Created by vzaffalon on 22/06/17.
 */

public class TutorialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        setUpViewPager();
        setUpButtons();
    }

    private void setUpViewPager(){
        PageIndicatorView cell_page_indicator = (PageIndicatorView) findViewById(R.id.cell_page_indicator);
        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        TutorialPageAdapter adapterViewPager = new TutorialPageAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        cell_page_indicator.setViewPager(vpPager);
    }

    private void setUpButtons(){
        TextView skipButton = (TextView) findViewById(R.id.skip_button);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        ImageButton nextButton = (ImageButton) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}

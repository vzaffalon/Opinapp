package com.opinnapp.opinnapp.tabholder.newquestion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;
import com.opinnapp.opinnapp.R;

/**
 * Created by vzaffalon on 03/06/17.
 */

public class NewQuestionActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_question);
        setDragEdge(SwipeBackLayout.DragEdge.BOTTOM);
        showHideOptions();
        setUpConfirmButton();
        setUpCancelButton();
    }

    private void setUpConfirmButton(){
        TextView cell_confirm_button  = (TextView) findViewById(R.id.cell_confirm_button);
        cell_confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ConfirmQuestionActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setUpCancelButton(){
       ImageButton cancel_button = (ImageButton) findViewById(R.id.cell_cancel_button);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void showHideOptions(){
        LinearLayout cell_alternatives_option = (LinearLayout) findViewById(R.id.cell_alternatives_option);
        LinearLayout cell_time_option = (LinearLayout) findViewById(R.id.cell_time_option);
        LinearLayout cell_tags_option = (LinearLayout) findViewById(R.id.cell_tags_option);
        final LinearLayout alternative_option = (LinearLayout) findViewById(R.id.alternative_option_layout);
        final LinearLayout time_option = (LinearLayout) findViewById(R.id.time_option_layout);
        final LinearLayout tag_option = (LinearLayout) findViewById(R.id.tag_option_layout);

        alternative_option.setVisibility(View.GONE);
        time_option.setVisibility(View.GONE);
        tag_option.setVisibility(View.GONE);


        cell_alternatives_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(alternative_option.getVisibility() == View.GONE){
                    alternative_option.setVisibility(View.VISIBLE);
                }else{
                    alternative_option.setVisibility(View.GONE);
                }
            }
        });

        cell_time_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(time_option.getVisibility() == View.GONE) {
                    time_option.setVisibility(View.VISIBLE);
                }else{
                    time_option.setVisibility(View.GONE);
                }
            }
        });

        cell_tags_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tag_option.getVisibility() == View.GONE) {
                    tag_option.setVisibility(View.VISIBLE);
                }else{
                    tag_option.setVisibility(View.GONE);
                }
            }
        });
    }

}

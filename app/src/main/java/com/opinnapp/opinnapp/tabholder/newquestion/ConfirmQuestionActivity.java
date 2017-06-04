package com.opinnapp.opinnapp.tabholder.newquestion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;
import com.opinnapp.opinnapp.R;
import com.opinnapp.opinnapp.tabholder.MainActivity;

import java.util.ArrayList;

/**
 * Created by vzaffalon on 03/06/17.
 */

public class ConfirmQuestionActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_question);
        setDragEdge(SwipeBackLayout.DragEdge.BOTTOM);
        setUpCancelButton();
        setUpUploadButtons();
        setUpConfirmButton();
        generateExampleTags();
    }

    private void setUpCancelButton() {
        ImageButton cancel_button = (ImageButton) findViewById(R.id.cell_cancel_button);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setUpConfirmButton(){
        TextView cell_confirm_button  = (TextView) findViewById(R.id.cell_confirm_button);
        cell_confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void generateExampleTags(){
        ArrayList<String> tags = new ArrayList<>();
        tags.add("#pizza");
        tags.add("#moda");
        tags.add("#sextou");
        tags.add("#indie rock");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.confirm_tags_recycler_view);
        ConfirmTagsAdapter confirmTagsAdapter = new ConfirmTagsAdapter(tags,getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(confirmTagsAdapter);
    }

    private void setUpUploadButtons(){
        RelativeLayout uploaded_image_1 = (RelativeLayout) findViewById(R.id.uploaded_image_1);
        RelativeLayout uploaded_image_2 = (RelativeLayout) findViewById(R.id.uploaded_image_2);
        RelativeLayout uploaded_image_3 = (RelativeLayout) findViewById(R.id.uploaded_image_3);
        RelativeLayout uploaded_image_4 = (RelativeLayout) findViewById(R.id.uploaded_image_4);

        uploaded_image_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPictureFromGallery();
            }
        });

        uploaded_image_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPictureFromGallery();
            }
        });

        uploaded_image_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPictureFromGallery();
            }
        });

        uploaded_image_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPictureFromGallery();
            }
        });


    }

    private void getPictureFromGallery(){

    }

    private void uploadImagesToFirebase(){

    }

    private void saveStoryObjectOnFirebase(){

    }
}

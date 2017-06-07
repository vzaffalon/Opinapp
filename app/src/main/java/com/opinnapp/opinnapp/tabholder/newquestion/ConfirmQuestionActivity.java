package com.opinnapp.opinnapp.tabholder.newquestion;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;
import com.opinnapp.opinnapp.R;
import com.opinnapp.opinnapp.models.OAStub;
import com.opinnapp.opinnapp.models.OAUser;
import com.opinnapp.opinnapp.tabholder.MainActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by vzaffalon on 03/06/17.
 */

public class ConfirmQuestionActivity extends SwipeBackActivity {

    private TextView cell_question_input;
    private TextView cell_time_text;

    //verify if the question has options or timer or tags
    private boolean optionsMode = false;
    private boolean timeMode = false;
    private boolean tagMode = false;

    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String[] tags;
    private int hour;
    private int minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_question);
        setDragEdge(SwipeBackLayout.DragEdge.BOTTOM);

        //dont change this order
        getInformationFromIntent();
        setUpInformation();
        setUpCancelButton();
        setUpConfirmButton();
    }

    private void getInformationFromIntent(){

        optionsMode = getIntent().getExtras().getBoolean("optionsMode");
        tagMode = getIntent().getExtras().getBoolean("tagMode");
        timeMode = getIntent().getExtras().getBoolean("timeMode");


        question = getIntent().getExtras().getString("question");


        if (optionsMode) {
            option1 = getIntent().getExtras().getString("option1");
            option2 = getIntent().getExtras().getString("option2");
            option3 = getIntent().getExtras().getString("option3");
            option4 = getIntent().getExtras().getString("option4");
        }
        if(tagMode) {
            tags = getIntent().getExtras().getStringArray("tags");
        }
        if(timeMode) {
            hour = getIntent().getExtras().getInt("hour");
            minute = getIntent().getExtras().getInt("minute");
        }

    }

    private void setUpInformation(){
        cell_question_input = (TextView) findViewById(R.id.cell_question_input);
        cell_question_input.setText(question);

        if(timeMode) {
            cell_time_text = (TextView) findViewById(R.id.cell_time_text);
            cell_time_text.setText(hour + " h " + minute + " min");
        }else{
            LinearLayout cell_time_option = (LinearLayout) findViewById(R.id.cell_time_option);
            cell_time_option.setVisibility(View.GONE);
        }

        if(tagMode){
            generateTagList();
        }else{
            LinearLayout cell_tags_option = (LinearLayout) findViewById(R.id.cell_tags_option);
            cell_tags_option.setVisibility(View.GONE);
        }

        if(optionsMode){
            setUpUploadButtons();
        }else{
            LinearLayout cell_alternatives_option = (LinearLayout) findViewById(R.id.cell_alternatives_option);
            cell_alternatives_option.setVisibility(View.GONE);
        }
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
                saveStoryObjectOnFirebase();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void generateTagList(){
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

        if(option1 != null) {
            uploaded_image_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getPictureFromGallery();
                }
            });
        }else{
            uploaded_image_1.setVisibility(View.GONE);
        }

        if(option2 != null) {
            uploaded_image_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getPictureFromGallery();
                }
            });
        }else{
            uploaded_image_2.setVisibility(View.GONE);
        }

        if(option3 != null) {
            uploaded_image_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getPictureFromGallery();
                }
            });
        }else{
            uploaded_image_3.setVisibility(View.GONE);
        }

        if(option4 != null) {
            uploaded_image_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getPictureFromGallery();
                }
            });
        }else{
            uploaded_image_4.setVisibility(View.GONE);
        }

    }

    private void getPictureFromGallery(){

    }

    private void uploadImagesToFirebase(){

    }

    private Date getExpirationDate(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hour);
        calendar.add(Calendar.MINUTE, minute);
        date = calendar.getTime();
        return date;
    }

    //TODO: METODO GET USER GERAL PARA TODAS AS FUNCOES DE RECEBER USUARIO LOGADO"
    //TODO: SALVAR OS TIPOS COM IMAGEM
    private void saveStoryObjectOnFirebase(){
        if(!optionsMode){
            List<String> tagsArray = Arrays.asList(tags);
            OAUser oaUser = new OAUser();
            //TODO: CHANGE THIS FIXED ID
            oaUser.setId("-Km-CASNbmQUzrBy1U0k");
            OAStub oaStub = new OAStub();
            oaStub.createStory(question,tagsArray,oaUser,getExpirationDate(),null);
            Toast.makeText(getApplicationContext(),"Pergunta Salva",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),"Pergunta não salva",Toast.LENGTH_SHORT).show();
        }
    }
}

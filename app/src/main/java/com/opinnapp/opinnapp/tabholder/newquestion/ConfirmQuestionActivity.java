package com.opinnapp.opinnapp.tabholder.newquestion;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;
import com.opinnapp.opinnapp.R;
import com.opinnapp.opinnapp.login.LoginActivity;
import com.opinnapp.opinnapp.models.OADatabase;
import com.opinnapp.opinnapp.models.OAImageOption;
import com.opinnapp.opinnapp.models.OAStory;
import com.opinnapp.opinnapp.models.OAStoryMultiChoiceImages;
import com.opinnapp.opinnapp.models.OAStoryTextOnly;
import com.opinnapp.opinnapp.models.OAUser;
import com.opinnapp.opinnapp.tabholder.OAApplication;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by vzaffalon on 03/06/17.
 */

public class ConfirmQuestionActivity extends SwipeBackActivity {

    private TextView cell_question_input;
    private TextView cell_time_text;

    private int REQUEST_CODE_PICKER = 1;

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

    RelativeLayout uploaded_image_1;
    RelativeLayout uploaded_image_2;
    RelativeLayout uploaded_image_3;
    RelativeLayout uploaded_image_4;

    private SweetAlertDialog pDialog;

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

        ImageView imageView = (ImageView) findViewById(R.id.cell_perfil_picture);
        Picasso.with(this).load(OAApplication.getUser().getImagePath()).into(imageView);
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
        ImageButton cell_confirm_button  = (ImageButton) findViewById(R.id.cell_confirm_button);
        cell_confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLoadingDialog();
                saveStoryObjectOnFirebase();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        finish();
                        pDialog.dismiss();
                    }
                }, 4000);
            }
        });
    }

    private void setLoadingDialog(){
        pDialog = new SweetAlertDialog(ConfirmQuestionActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Enviando Pegunta");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private void generateTagList(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.confirm_tags_recycler_view);
        ConfirmTagsAdapter confirmTagsAdapter = new ConfirmTagsAdapter(tags,getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(confirmTagsAdapter);
    }

    private void setUpUploadButtons(){
        uploaded_image_1 = (RelativeLayout) findViewById(R.id.uploaded_image_1);
        uploaded_image_2 = (RelativeLayout) findViewById(R.id.uploaded_image_2);
        uploaded_image_3 = (RelativeLayout) findViewById(R.id.uploaded_image_3);
        uploaded_image_4 = (RelativeLayout) findViewById(R.id.uploaded_image_4);

        if(option1 != null) {
            ImageView image_1 = (ImageView) findViewById(R.id.image_1);
            image_1.setImageDrawable(resize(Drawable.createFromPath(option1)));
        }else{
            uploaded_image_1.setVisibility(View.GONE);
        }

        if(option2 != null) {
            ImageView image_2 = (ImageView) findViewById(R.id.image_2);
            image_2.setImageDrawable(resize(Drawable.createFromPath(option2)));
        }else{
            uploaded_image_2.setVisibility(View.GONE);
        }

        if(option3 != null) {
            ImageView image_3 = (ImageView) findViewById(R.id.image_3);
            image_3.setImageDrawable(resize(Drawable.createFromPath(option3)));
        }else{
            uploaded_image_3.setVisibility(View.GONE);
        }

        if(option4 != null) {
            ImageView image_4 = (ImageView) findViewById(R.id.image_4);
            image_4.setImageDrawable(resize(Drawable.createFromPath(option4)));
        }else{
            uploaded_image_4.setVisibility(View.GONE);
        }
    }

    //for aplying font
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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

    private void saveStoryObjectOnFirebase(){
        List<OAImageOption> images = null;
        if(optionsMode) {
            images = new ArrayList<>();

            if (option1 != null) {
                OAImageOption imageOption = new OAImageOption();
                imageOption.setImagePath(option1);
                images.add(imageOption);
            }
            if (option2 != null) {
                OAImageOption imageOption = new OAImageOption();
                imageOption.setImagePath(option2);
                images.add(imageOption);
            }
            if (option3 != null) {
                OAImageOption imageOption = new OAImageOption();
                imageOption.setImagePath(option3);
                images.add(imageOption);
            }
            if (option4 != null) {
                OAImageOption imageOption = new OAImageOption();
                imageOption.setImagePath(option4);
                images.add(imageOption);
            }
        }

        List<String> tagsArray;
        if(tags !=  null) {
            tagsArray = Arrays.asList(tags);
        }else{
            tagsArray = new ArrayList<>();
        }

        OAUser oaUser = OAApplication.getUser();
        createStory(question,tagsArray,oaUser,getExpirationDate(),images);
        Toast.makeText(getApplicationContext(),"Pergunta Salva",Toast.LENGTH_SHORT).show();
    }

    private OAStory createStory(String description, List<String>tags, OAUser owner, Date expirationDate, List<OAImageOption> images){
        if (images != null) {
            OAStoryMultiChoiceImages storyMultiChoiceImages = new OAStoryMultiChoiceImages();
            storyMultiChoiceImages.setDescription(description);
            storyMultiChoiceImages.setTags(tags);
            storyMultiChoiceImages.setOwner(owner);
            storyMultiChoiceImages.setExpirationDate(expirationDate);
            storyMultiChoiceImages.setCreationDate(new Date());

            storyMultiChoiceImages.setImages(images);
            if (OADatabase.createStory(storyMultiChoiceImages))
                return storyMultiChoiceImages;
            else
                return null;
        }
        else {
            OAStoryTextOnly storyTextOnly = new OAStoryTextOnly();
            storyTextOnly.setDescription(description);
            storyTextOnly.setTags(tags);
            storyTextOnly.setOwner(owner);
            storyTextOnly.setExpirationDate(expirationDate);
            storyTextOnly.setCreationDate(new Date());

            if (OADatabase.createStory(storyTextOnly))
                return storyTextOnly;
            else
                return null;
        }
    }

    private Drawable resize(Drawable image) {
        Bitmap b = ((BitmapDrawable)image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 100, 100, false);
        return new BitmapDrawable(getResources(), bitmapResized);
    }
}

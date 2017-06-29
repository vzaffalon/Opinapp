package com.opinnapp.opinnapp.tabholder.newquestion;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;
import com.nguyenhoanglam.imagepicker.activity.ImagePickerActivity;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.opinnapp.opinnapp.R;
import com.opinnapp.opinnapp.tabholder.OAApplication;
import com.shawnlin.numberpicker.NumberPicker;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by vzaffalon on 03/06/17.
 */

public class NewQuestionActivity extends SwipeBackActivity {
    private EditText cell_question_input;
    private EditText editText_option_1;
    private EditText editText_option_2;
    private EditText editText_option_3;
    private EditText editText_option_4;
    private EditText editText_tags;
    private NumberPicker numberPicker_hour;
    private NumberPicker numberPicker_minute;

    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String[] tags;
    private int hour;
    private int minute;

    private RelativeLayout uploaded_image_1;
    private RelativeLayout uploaded_image_2;
    private RelativeLayout uploaded_image_3;
    private RelativeLayout uploaded_image_4;

    //image picker intent code
    private int REQUEST_CODE_PICKER = 1;

    //verify if the question has options or timer or tags
    private boolean optionsMode = false;
    private boolean timeMode = false;
    private boolean tagMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_question);
        setDragEdge(SwipeBackLayout.DragEdge.BOTTOM);
        showHideOptions();
        setUpEditTexts();
        setUpNumberPickers();
        setUpConfirmButton();
        setUpCancelButton();
        setUpUploadButtons();

        ImageView imageView = (ImageView) findViewById(R.id.cell_perfil_picture);
        Picasso.with(this).load(OAApplication.getUser().getImagePath()).into(imageView);
    }

    private void setUpNumberPickers(){
        numberPicker_hour = (NumberPicker) findViewById(R.id.number_picker_hour);
        numberPicker_minute = (NumberPicker) findViewById(R.id.number_picker_minute);
    }

    private void setUpEditTexts(){
        cell_question_input = (EditText) findViewById(R.id.cell_question_input);
        /*editText_option_1 = (EditText) findViewById(R.id.editText_option_1);
        editText_option_2 = (EditText) findViewById(R.id.editText_option_2);
        editText_option_3 = (EditText) findViewById(R.id.editText_option_3);
        editText_option_4 = (EditText) findViewById(R.id.editText_option_4);*/
        editText_tags = (EditText) findViewById(R.id.editText_tags);
    }

    private void getStoryData(){
        question = cell_question_input.getText().toString();

        String tagString = editText_tags.getText().toString();
        if(!tagString.isEmpty()) {
            if (tagString.contains("#")) {
                tags = tagString.replaceFirst("^#", "").split("#");
            } else {
                Toast.makeText(getApplicationContext(), "Tags devem ser escritas no formato #tag", Toast.LENGTH_SHORT).show();
            }
        }

        hour = numberPicker_hour.getValue();
        minute = numberPicker_minute.getValue();

    }

    private void setUpConfirmButton(){
        ImageButton cell_confirm_button  = (ImageButton) findViewById(R.id.cell_confirm_button);
        cell_confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getStoryData();
                if(verifyData()) {
                    changeActivityAndPassData();
                }
            }
        });
    }

    private boolean verifyData(){
        boolean correctData = true;

        if(question.isEmpty()){
            Toast.makeText(getApplicationContext(),"Uma pergunta deve ser escrita",Toast.LENGTH_SHORT).show();
            correctData = false;
        }

        if(optionsMode){
            if(option1 == null && option2 == null && option3 == null && option4 == null){
                Toast.makeText(getApplicationContext(),"Selecione alguma imagem",Toast.LENGTH_SHORT).show();
                correctData = false;
            }
        }

        if(tagMode){
            if(tags == null){
                Toast.makeText(getApplicationContext(),"Adicione alguma tag",Toast.LENGTH_SHORT).show();
                correctData = false;
            }
        }


        return correctData;
    }

    private void changeActivityAndPassData(){
        Intent intent = new Intent(getApplicationContext(),ConfirmQuestionActivity.class);

        intent.putExtra("question",question);
        if(option1 != null) {
            if (!option1.isEmpty()) {
                intent.putExtra("option1", option1);
            }
        }
        if(option2 != null) {
            if (!option2.isEmpty()) {
                intent.putExtra("option2", option2);
            }
        }
        if(option3 != null) {
            if (!option3.isEmpty()) {
                intent.putExtra("option3", option3);
            }
        }
        if(option4 != null) {
            if (!option4.isEmpty()) {
                intent.putExtra("option4", option4);
            }
        }

        if(timeMode){
            intent.putExtra("hour",hour);
            intent.putExtra("minute",minute);
        }

        if(tagMode){
            intent.putExtra("tags",tags);
        }

        intent.putExtra("timeMode",timeMode);
        intent.putExtra("optionsMode",optionsMode);
        intent.putExtra("tagMode",tagMode);


        startActivity(intent);
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
                    optionsMode = true;
                }else{
                    optionsMode = false;
                    /*editText_option_1.getText().clear();
                    editText_option_2.getText().clear();
                    editText_option_3.getText().clear();
                    editText_option_4.getText().clear();*/
                    alternative_option.setVisibility(View.GONE);
                }
            }
        });

        cell_time_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(time_option.getVisibility() == View.GONE) {
                    timeMode = true;
                    time_option.setVisibility(View.VISIBLE);
                }else{
                    timeMode = false;
                    time_option.setVisibility(View.GONE);
                }
            }
        });

        cell_tags_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tag_option.getVisibility() == View.GONE) {
                    tagMode = true;
                    tag_option.setVisibility(View.VISIBLE);
                }else{
                    tagMode = false;
                    editText_tags.getText().clear();
                    tag_option.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setUpUploadButtons(){
        uploaded_image_1 = (RelativeLayout) findViewById(R.id.uploaded_image_1);
        uploaded_image_2 = (RelativeLayout) findViewById(R.id.uploaded_image_2);
        uploaded_image_3 = (RelativeLayout) findViewById(R.id.uploaded_image_3);
        uploaded_image_4 = (RelativeLayout) findViewById(R.id.uploaded_image_4);


            uploaded_image_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    REQUEST_CODE_PICKER = 1;
                    getPictureFromGallery();
                }
            });

            uploaded_image_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    REQUEST_CODE_PICKER = 2;
                    getPictureFromGallery();
                }
            });

            uploaded_image_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    REQUEST_CODE_PICKER = 3;
                    getPictureFromGallery();
                }
            });

            uploaded_image_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    REQUEST_CODE_PICKER = 4;
                    getPictureFromGallery();
                }
            });

    }

    private void getPictureFromGallery(){
        Intent intent = new Intent(this, ImagePickerActivity.class);

        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_FOLDER_MODE, true);
        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_MODE, ImagePickerActivity.MODE_SINGLE);
        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_LIMIT, 1);
        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_SHOW_CAMERA, true);
        //intent.putExtra(ImagePickerActivity.INTENT_EXTRA_SELECTED_IMAGES, images);
        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_FOLDER_TITLE, "Album");
        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_IMAGE_TITLE, "Clique na imagem para selecionar");
        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_IMAGE_DIRECTORY, "Camera");

        startActivityForResult(intent, REQUEST_CODE_PICKER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICKER && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = data.getParcelableArrayListExtra(ImagePickerActivity.INTENT_EXTRA_SELECTED_IMAGES);

            switch (REQUEST_CODE_PICKER){
                case 1:
                    Image image = images.get(0);
                    ImageView image_1 = (ImageView) findViewById(R.id.image_1);
                    image_1.setImageDrawable(Drawable.createFromPath(image.getPath()));
                    option1 = image.getPath();
                    break;
                case 2:
                    image = images.get(0);
                    ImageView image_2 = (ImageView) findViewById(R.id.image_2);
                    image_2.setImageDrawable(Drawable.createFromPath(image.getPath()));
                    option2 = image.getPath();
                    break;
                case 3:
                    image = images.get(0);
                    ImageView image_3 = (ImageView) findViewById(R.id.image_3);
                    image_3.setImageDrawable(Drawable.createFromPath(image.getPath()));
                    option3 = image.getPath();
                    break;
                case 4:
                    image = images.get(0);
                    ImageView image_4 = (ImageView) findViewById(R.id.image_4);
                    image_4.setImageDrawable(Drawable.createFromPath(image.getPath()));
                    option4 = image.getPath();
                    break;

            }
        }
    }

}

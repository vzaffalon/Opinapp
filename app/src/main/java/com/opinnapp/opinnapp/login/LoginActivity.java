package com.opinnapp.opinnapp.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.opinnapp.opinnapp.R;
import com.opinnapp.opinnapp.tabholder.MainActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by vzaffalon on 11/05/17.
 */


public class LoginActivity extends AppCompatActivity {

    private static int TIME_OUT = 3000;
    SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setUpLayout();

    }

    @Override
    public void onBackPressed()
    {
        moveTaskToBack(true);
    }

    private void setUpLayout(){
        Button button_facebook_login = (Button) findViewById(R.id.button_facebook_login);
        button_facebook_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity();
            }
        });

        TextView button_guest_login = (TextView) findViewById(R.id.button_guest_login);
        button_guest_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity();
            }
        });
    }

    private void changeActivity(){
        setLoadingDialog();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pDialog.cancel();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }, TIME_OUT);
    }


    private void setLoadingDialog(){
        pDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Autenticando Usuário");
        pDialog.setCancelable(false);
        pDialog.show();
    }
}


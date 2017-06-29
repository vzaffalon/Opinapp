package com.opinnapp.opinnapp.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.database.DatabaseError;
import com.opinnapp.opinnapp.R;
import com.opinnapp.opinnapp.models.OADatabase;
import com.opinnapp.opinnapp.models.OAFirebaseCallback;
import com.opinnapp.opinnapp.models.OAUser;
import com.opinnapp.opinnapp.tabholder.MainActivity;
import com.opinnapp.opinnapp.tabholder.OAApplication;
import com.opinnapp.opinnapp.tutorial.TutorialActivity;

import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by vzaffalon on 11/05/17.
 */


public class LoginActivity extends AppCompatActivity {
    private SweetAlertDialog pDialog;
    private CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (OAApplication.getUser() == null) {
            setUpLayout();
            setUpFacebookLogin();
        }
        else {
            goToMainApp();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed()
    {
        moveTaskToBack(true);
    }

    private void setUpLayout(){
        ImageButton button_guest_login = (ImageButton) findViewById(R.id.button_guest_login);
        button_guest_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTutorial();
            }
        });
    }

    private void goToTutorial(){
        Intent intent = new Intent(getApplicationContext(), TutorialActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToMainApp() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void setUpFacebookLogin(){
        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton button_facebook_login = (LoginButton) findViewById(R.id.button_facebook_login);
        button_facebook_login.setBackgroundResource(R.drawable.facebook_button);
        button_facebook_login.setText("");
        button_facebook_login.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        button_facebook_login.setReadPermissions("email", "public_profile");
        button_facebook_login.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                setLoadingDialog();

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                OAUser user = new OAUser();
                                String fbID = null;
                                try {
                                    fbID = object.getString("id");
                                    user.setEmail(object.getString("email"));
                                    user.setfName(object.getString("first_name"));
                                    user.setlName(object.getString("last_name"));
                                    user.setImagePath(object.getJSONObject("picture").getJSONObject("data").getString("url"));
                                    user.setFacebookID(fbID);

                                    logUser(user);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    showLoginError();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,email,first_name,last_name,picture.type(large)");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                showLoginError();
            }

            @Override
            public void onError(FacebookException exception) {
                showLoginError();
            }
        });
    }

    private void setLoadingDialog(){
        pDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Entrando");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private void showLoginError() {
        if (pDialog != null)
            pDialog.dismiss();
        Toast.makeText(this, "Erro ao fazer login, tente novamente mais tarde.", Toast.LENGTH_LONG).show();
    }

    private void logUser(final OAUser user) {
        LoginManager.getInstance().logOut();

        OADatabase.getUserWithFacebookID(user.getFacebookID(), new OAFirebaseCallback() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    OAUser user = (OAUser) object;
                    OAApplication.setUser(user);
                    user.saveUser();

                    goToMainApp();
                }
                else {
                    registerUser(user);
                }
            }

            @Override
            public void onFailure(DatabaseError databaseError) {
                registerUser(user);
            }
        });
    }

    private void registerUser(final OAUser user) {
        if(OADatabase.createUser(user)) {
            OAApplication.setUser(user);
            user.saveUser();

            goToTutorial();
        }
        else {
            showLoginError();
        }
    }
}
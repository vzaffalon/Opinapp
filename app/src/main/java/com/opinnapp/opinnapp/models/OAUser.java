package com.opinnapp.opinnapp.models;

import android.content.SharedPreferences;

import com.opinnapp.opinnapp.tabholder.OAApplication;

/**
 * Created by cayke on 09/05/17.
 */

public class OAUser implements OAFirebaseModel{
    private static final String USER_PREFS = "br.com.opinapp.UserPrefsFile";

    private String email;
    private String facebookID;
    private String fName, lName;
    private String id;
    private String imagePath;

    public OAUser() {}

    public OAUser(String id, String fName, String lName, String facebookID, String imagePath, String email) {
        this.id = id;
        this.email = email;
        this.fName = fName;
        this.lName = lName;
        this.facebookID = facebookID;
        this.imagePath = imagePath;
    }

    @Override
    public Object firebaseRepresentation() {
        return this;
    }

    @Override
    public void setObjectsValuesWithFirebaseIds() {

    }

    public void saveUser() {
        SharedPreferences userPrefs = OAApplication.getContext().getSharedPreferences(USER_PREFS, 0);
        SharedPreferences.Editor editor = userPrefs.edit();
        editor.putString("email", email);
        editor.putString("fName", fName);
        editor.putString("imagePath", imagePath);
        editor.putString("lName", lName);
        editor.putString("facebookID", facebookID);
        editor.putString("id", id);
        editor.apply();

        OAApplication.setUser(this);
    }

    public static OAUser loadUserFromDevice() {
        SharedPreferences userPrefs = OAApplication.getContext().getSharedPreferences(USER_PREFS, 0);
        String email = userPrefs.getString("email", null);
        String name = userPrefs.getString("fName", null);
        String imagePath = userPrefs.getString("imagePath", null);
        String lName = userPrefs.getString("lName", null);
        String facebookID = userPrefs.getString("facebookID", null);
        String id = userPrefs.getString("id", null);

        if (email == null || name == null) {
            return null;
        } else {
            return new OAUser(id, name, lName, facebookID, imagePath, email);
        }
    }

    public static void removeUserFromDevice()
    {
        SharedPreferences userPrefs = OAApplication.getContext().getSharedPreferences(USER_PREFS, 0);
        SharedPreferences.Editor editor = userPrefs.edit();
        editor.remove("email");
        editor.remove("fName");
        editor.remove("imagePath");
        editor.remove("lName");
        editor.remove("facebookID");
        editor.remove("id");
        editor.apply();
    }

    public String getEmail() {
        return email;
    }

    public String getFacebookID() {
        return facebookID;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getId() {
        return id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFacebookID(String facebookID) {
        this.facebookID = facebookID;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
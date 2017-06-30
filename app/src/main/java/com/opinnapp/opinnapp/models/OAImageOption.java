package com.opinnapp.opinnapp.models;

import com.google.firebase.database.DatabaseError;
import com.opinnapp.opinnapp.tabholder.OAApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cayke on 09/05/17.
 */

public class OAImageOption implements OAFirebaseModel{
    private String id;
    private String imagePath;
    private String storyID;
    private List<String> usersIdThatLiked;

    //from current user
    public boolean isLiked;

    @Override
    public Object firebaseRepresentation() {
        return this;
    }

    @Override
    public void setObjectsValuesWithFirebaseIds() {
        OADatabase.getLikesForImageOption(this, new OAFirebaseCallback() {
            @Override
            public void onSuccess(Object object) {
                usersIdThatLiked = (List<String>) object;
                if (OAUtil.contains(usersIdThatLiked, OAApplication.getUser().getId()))
                    isLiked = true;
            }

            @Override
            public void onFailure(DatabaseError databaseError) {
                usersIdThatLiked = new ArrayList<>();
            }
        });
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getStoryID() {
        return storyID;
    }

    public void setStoryID(String storyID) {
        this.storyID = storyID;
    }

    public List<String> getUsersIdThatLiked() {
        return usersIdThatLiked;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}

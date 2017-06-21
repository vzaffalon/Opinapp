package com.opinnapp.opinnapp.models;

import java.util.List;

/**
 * Created by cayke on 09/05/17.
 */

public class OAImageOption implements OAFirebaseModel{
    private String id;
    private String imagePath;
    private String storyID;
    private List<String> usersIdThatLiked;

    @Override
    public Object firebaseRepresentation() {
        return this;
    }

    @Override
    public void setObjectsValuesWithFirebaseIds() {

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

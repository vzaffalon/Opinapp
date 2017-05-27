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
}

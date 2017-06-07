package com.opinnapp.opinnapp.models;

import java.util.List;

/**
 * Created by cayke on 09/05/17.
 */

public class OAStoryTextOnly extends OAStory {
    private List<String> usersIdThatLiked;
    private List<String> usersIdThatDisliked;

    @Override
    public Object firebaseRepresentation() {
        OAStoryTextOnly firebaseInstance = new OAStoryTextOnly();

        //superclass
        firebaseInstance.id = this.id;
        firebaseInstance.ownerID = this.ownerID;
        firebaseInstance.created_at = this.creationDate.getTime();
        firebaseInstance.expirate_at = this.expirationDate.getTime();
        firebaseInstance.description = this.description;
        firebaseInstance.tags = this.tags;

        //this class
        firebaseInstance.usersIdThatLiked = this.usersIdThatLiked;
        firebaseInstance.usersIdThatDisliked = this.usersIdThatDisliked;

        return firebaseInstance;
    }

    @Override
    public void setObjectsValuesWithFirebaseIds() {
        super.setObjectsValuesWithFirebaseIds();
    }

    public OAStoryTextOnly() {
        type = "OAStoryTextOnly";
    }

    public List<String> getUsersIdThatLiked() {
        return usersIdThatLiked;
    }

    public List<String> getUsersIdThatDisliked() {
        return usersIdThatDisliked;
    }
}

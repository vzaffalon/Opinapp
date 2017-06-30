package com.opinnapp.opinnapp.models;

/**
 * Created by cayke on 09/05/17.
 */

public class OAStoryTextOnly extends OAStory {
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

        return firebaseInstance;
    }

    @Override
    public void setObjectsValuesWithFirebaseIds() {
        super.setObjectsValuesWithFirebaseIds();
    }

    public OAStoryTextOnly() {
        type = "OAStoryTextOnly";
    }
}

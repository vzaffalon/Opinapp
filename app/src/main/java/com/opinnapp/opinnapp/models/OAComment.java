package com.opinnapp.opinnapp.models;

import com.google.firebase.database.DatabaseError;

/**
 * Created by cayke on 09/05/17.
 */

public class OAComment implements OAFirebaseModel {
    private String id;
    private String text;
    private String ownerID;
    private String storyID;

    private OAUser owner;

    public OAComment(){}

    public OAComment(String text, String storyID, OAUser owner) {
        this.text = text;
        this.storyID = storyID;
        this.owner = owner;
        this.ownerID = owner.getId();
    }

    @Override
    public Object firebaseRepresentation() {
        OAComment comment = new OAComment();
        comment.id = this.id;
        comment.text = this.text;
        comment.ownerID = this.ownerID;
        comment.storyID = this.storyID;
        return comment;
    }

    @Override
    public void setObjectsValuesWithFirebaseIds() {
        OADatabase.getUserWithID(ownerID, new OAFirebaseCallback() {
            @Override
            public void onSuccess(Object object) {
                owner = (OAUser) object;
                System.out.println("User set with success");
            }

            @Override
            public void onFailure(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public String getStoryID() {
        return storyID;
    }

    public OAUser getOwner() {
        return owner;
    }
}

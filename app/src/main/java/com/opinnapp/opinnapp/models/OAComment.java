package com.opinnapp.opinnapp.models;

/**
 * Created by cayke on 09/05/17.
 */

public class OAComment implements OAFirebaseModel {
    private String id;
    private String text;
    private String userID;
    private String storyID;

    private OAUser owner;

    public OAComment(){}

    @Override
    public Object firebaseRepresentation() {
        OAComment comment = new OAComment();
        comment.id = this.id;
        comment.text = this.text;
        comment.userID = this.userID;
        comment.storyID = this.storyID;
        return comment;
    }

    @Override
    public void setObjectsValuesWithFirebaseIds() {
        //todo set owner
    }

    public String getUserID() {
        return userID;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getStoryID() {
        return storyID;
    }

    public OAUser getOwner() {
        return owner;
    }

    public void setId(String id) {
        this.id = id;
    }

    //todo apagar
    public void setDefaultValues() {
        text = "Isso é um comentario 2.";
        userID = "Ahsushau2389835udshs";
        storyID = "-Kl6aXvpNxn7xHQe-1-o";
    }
}

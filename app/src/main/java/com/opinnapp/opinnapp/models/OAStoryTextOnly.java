package com.opinnapp.opinnapp.models;

import java.util.List;

/**
 * Created by cayke on 09/05/17.
 */

public class OAStoryTextOnly extends OAStory implements OAFirebaseModel{
    private List<String> usersIdThatLiked;
    private List<String> usersIdThatDisliked;

    private List<OAUser> likes;
    private List<OAUser> dislikes;

    @Override
    public Object firebaseRepresentation() {
        OAStoryTextOnly storyTextOnly = (OAStoryTextOnly) super.firebaseRepresentation();
        storyTextOnly.usersIdThatLiked = this.usersIdThatLiked;
        storyTextOnly.usersIdThatDisliked = this.usersIdThatDisliked;
        storyTextOnly.type = this.type;

        return storyTextOnly;
    }

    @Override
    public void setObjectsValuesWithFirebaseIds() {
        super.setObjectsValuesWithFirebaseIds();

        //todo likes, dislikes
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

    public List<OAUser> getLikes() {
        return likes;
    }

    public List<OAUser> getDislikes() {
        return dislikes;
    }
}

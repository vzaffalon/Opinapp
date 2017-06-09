package com.opinnapp.opinnapp.models;

import android.media.Image;

import com.google.firebase.database.DatabaseError;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Created by cayke on 06/06/17.
 */

public class OAStub {
    //REGISTRO DE USUARIO
    public static OAUser createUser(String name, String url, String facebookID, String imagePath) {
        OAUser user = new OAUser(name, url, facebookID, imagePath);
        if (OADatabase.createUser(user))
            return user;
        else
            return null;
    }

    //LOGIN DE USUARIO
    public static void getUserWithFacebookID(String id) {
        OADatabase.getUserWithFacebookID(id, new OAFirebaseCallback() {
            @Override
            public void onSuccess(Object object) {
                OAUser user = (OAUser) object;
                System.out.println("User " + user.getUrl() +  " founded with success");
            }

            @Override
            public void onFailure(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public static void getUserWithID(String id) {
        OADatabase.getUserWithID(id, new OAFirebaseCallback() {
            @Override
            public void onSuccess(Object object) {
                OAUser user = (OAUser) object;
                System.out.println("User " + user.getUrl() +  " founded with success");
            }

            @Override
            public void onFailure(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    //CRIAR "CARD" DE DUVIDA
    public static OAStory createStory(String description, List<String>tags, OAUser owner, Date expirationDate, List<Image> images) {
        if (images != null) {
            OAStoryMultiChoiceImages storyMultiChoiceImages = new OAStoryMultiChoiceImages();
            storyMultiChoiceImages.setDescription(description);
            storyMultiChoiceImages.setTags(tags);
            storyMultiChoiceImages.setOwner(owner);
            storyMultiChoiceImages.setExpirationDate(expirationDate);
            storyMultiChoiceImages.setCreationDate(new Date());

            //todo como vai enviar as imagens?
            //storyMultiChoiceImages.setImages(images);
            if (OADatabase.createStory(storyMultiChoiceImages))
                return storyMultiChoiceImages;
            else
                return null;
        }
        else {
            OAStoryTextOnly storyTextOnly = new OAStoryTextOnly();
            storyTextOnly.setDescription(description);
            storyTextOnly.setTags(tags);
            storyTextOnly.setOwner(owner);
            storyTextOnly.setExpirationDate(expirationDate);
            storyTextOnly.setCreationDate(new Date());

            if (OADatabase.createStory(storyTextOnly))
                return storyTextOnly;
            else
                return null;
        }
    }

    //PEGAR TODOS OS "CARDS"
    public static void getStories() {
        OADatabase.getAllStories(new OAFirebaseCallback() {
            @Override
            public void onSuccess(Object object) {
                List<OAStory> stories = (List<OAStory>) object;
                System.out.println("Stories founded with success");
            }

            @Override
            public void onFailure(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    //CRIAR UM COMENTARIO EM UM "CARD"
    public static OAComment createComment(String text, String storyID, OAUser owner) {
        OAComment comment = new OAComment(text, storyID, owner);
        if (OADatabase.createComment(comment))
            return comment;
        else
            return null;
    }

    //UPLOAD DE IMAGENS
    public static void uploadImage(File file) {
        OADatabase.uploadImage(file);
    }
}
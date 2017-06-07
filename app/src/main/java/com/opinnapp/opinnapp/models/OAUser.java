package com.opinnapp.opinnapp.models;

/**
 * Created by cayke on 09/05/17.
 */

public class OAUser implements OAFirebaseModel{
    private String facebookID;
    private String name;
    private String url; //@username
    private String id;
    private String imagePath;

    public OAUser() {}

    public OAUser(String name, String url, String facebookID, String imagePath) {
        this.name = name;
        this.facebookID = facebookID;
        this.imagePath = imagePath;
        this.url = url;
    }

    @Override
    public Object firebaseRepresentation() {
        return this;
    }

    @Override
    public void setObjectsValuesWithFirebaseIds() {

    }

    public String getFacebookID() {
        return facebookID;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
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
}
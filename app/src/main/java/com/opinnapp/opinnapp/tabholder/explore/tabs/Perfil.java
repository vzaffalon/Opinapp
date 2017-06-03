package com.opinnapp.opinnapp.tabholder.explore.tabs;

/**
 * Created by vzaffalon on 03/06/17.
 */

public class Perfil{

    private String name;
    private String facebookImage;

    public Perfil(String name, String facebookImage) {
        this.name = name;
        this.facebookImage = facebookImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFacebookImage() {
        return facebookImage;
    }

    public void setFacebookImage(String facebookImage) {
        this.facebookImage = facebookImage;
    }
}

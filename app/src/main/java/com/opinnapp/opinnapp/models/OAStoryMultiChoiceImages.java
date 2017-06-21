package com.opinnapp.opinnapp.models;

import java.util.List;

/**
 * Created by cayke on 09/05/17.
 */

public class OAStoryMultiChoiceImages extends OAStory {
    private List<String> imagesIds;

    private List<OAImageOption> images;

    @Override
    public Object firebaseRepresentation() {
        OAStoryMultiChoiceImages firebaseInstance = new OAStoryMultiChoiceImages();
        //superclass
        firebaseInstance.id = this.id;
        firebaseInstance.ownerID = this.ownerID;
        firebaseInstance.created_at = this.creationDate.getTime();
        firebaseInstance.expirate_at = this.expirationDate.getTime();
        firebaseInstance.description = this.description;
        firebaseInstance.tags = this.tags;

        //this class
        firebaseInstance.imagesIds = this.imagesIds;

        return firebaseInstance;
    }

    @Override
    public void setObjectsValuesWithFirebaseIds() {
        super.setObjectsValuesWithFirebaseIds();

        //todo images;
    }

    public OAStoryMultiChoiceImages () {
        this.type = "OAStoryMultiChoiceImages";
    }

    public List<String> getImagesIds() {
        return imagesIds;
    }

    public List<OAImageOption> getImages() {
        return images;
    }

    public void setImages(List<OAImageOption> images) {
        this.images = images;
    }

    public void setImagesIds(List<String> imagesIds) {
        this.imagesIds = imagesIds;
    }
}

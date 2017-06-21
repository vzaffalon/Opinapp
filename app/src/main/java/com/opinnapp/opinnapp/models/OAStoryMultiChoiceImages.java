package com.opinnapp.opinnapp.models;

import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
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

        for (final String imageID : imagesIds) {
            OADatabase.getImageOptionWithID(imageID, new OAFirebaseCallback() {
                @Override
                public void onSuccess(Object object) {
                    if (images == null) {
                        images = new ArrayList<OAImageOption>();
                    }

                    images.add((OAImageOption) object);
                }

                @Override
                public void onFailure(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }
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

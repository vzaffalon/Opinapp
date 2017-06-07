package com.opinnapp.opinnapp.models;

import com.google.firebase.database.DatabaseError;

import java.util.Date;
import java.util.List;

/**
 * Created by cayke on 09/05/17.
 */

//Classe que representa a "duvida" / "pergunta"
public class OAStory implements OAFirebaseModel {
    //firebase attributes
    protected String type;
    protected String id;
    protected String ownerID;
    protected Long created_at;
    protected Long expirate_at;
    protected String description;
    protected List<String> tags;

    //needs relationship
    protected OAUser owner;
    protected List<OAComment> comments;
    protected Date creationDate;
    protected Date expirationDate;

    @Override
    public Object firebaseRepresentation() {
        OAStory firebaseInstance = new OAStory();
        firebaseInstance.id = this.id;
        firebaseInstance.ownerID = this.ownerID;
        firebaseInstance.created_at = this.creationDate.getTime();
        firebaseInstance.expirate_at = this.expirationDate.getTime();
        firebaseInstance.description = this.description;
        firebaseInstance.tags = this.tags;

        return firebaseInstance;
    }

    @Override
    public void setObjectsValuesWithFirebaseIds() {
        creationDate = new Date(created_at);
        expirationDate = new Date(expirate_at);

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

        OADatabase.getCommentsWithStoryID(this.id, new OAFirebaseCallback() {
            @Override
            public void onSuccess(Object object) {
                comments = (List<OAComment>) object;
                System.out.println("Comments set with success");
            }

            @Override
            public void onFailure(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public OAStory() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public Long getCreated_at() {
        return created_at;
    }

    public Long getExpirate_at() {
        return expirate_at;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getTags() {
        return tags;
    }


    public OAUser getOwner() {
        return owner;
    }

    public List<OAComment> getComments() {
        return comments;
    }


    public Date getCreationDate() {
        return creationDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public String getType() {
        return type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }


    public void setOwner(OAUser owner) {
        this.owner = owner;
        this.ownerID = owner.getId();
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        this.created_at = creationDate.getTime();
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
        this.expirate_at = expirationDate.getTime();
    }
}
package com.opinnapp.opinnapp.models;

import java.util.ArrayList;
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
    protected List<String> commentsIds;

    //needs relationship
    protected OAUser owner;
    protected List<OAComment> comments;
    protected boolean favorited;
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

        updateCommentsIds();
        firebaseInstance.commentsIds = this.commentsIds;

        return firebaseInstance;
    }

    @Override
    public void setObjectsValuesWithFirebaseIds() {
        //todo pegar owner, comments
        creationDate = new Date(created_at);
        expirationDate = new Date(expirate_at);
    }

    public void updateCommentsIds() {
        if (commentsIds == null || (commentsIds.size() != comments.size())) {
            commentsIds = new ArrayList<>();
            for (OAComment comment: comments) {
                commentsIds.add(comment.getId());
            }
        }
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

    public List<String> getCommentsIds() {
        return commentsIds;
    }

    public OAUser getOwner() {
        return owner;
    }

    public List<OAComment> getComments() {
        return comments;
    }

    public boolean isFavorited() {
        return favorited;
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

    //todo apagar
    public void setDefaultValue(String description) {
        ownerID = "Ahsushau2389835udshs";
        creationDate = new Date();
        expirationDate = new Date();
        expirationDate.setMonth(6);
        this.description = description;
        tags = new ArrayList<>();
        tags.add("moda");
        tags.add("fashion");
        tags.add("help");
    }

}

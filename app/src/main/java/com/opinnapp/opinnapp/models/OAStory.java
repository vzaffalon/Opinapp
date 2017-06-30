package com.opinnapp.opinnapp.models;

import com.google.firebase.database.DatabaseError;
import com.opinnapp.opinnapp.tabholder.OAApplication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by cayke on 09/05/17.
 */

//Classe que representa a "duvida" / "pergunta"
public class OAStory implements OAFirebaseModel {
    //firebase attributes
    String type;
    protected String id;
    String ownerID;
    Long created_at;
    Long expirate_at;
    String description;
    protected List<String> tags;

    //needs relationship
    private OAUser owner;
    protected List<OAComment> comments;
    Date creationDate;
    Date expirationDate;

    private List<String> usersIdThatLiked;
    private List<String> usersIdThatDisliked;

    //from current user
    public boolean isBookmarked;
    public boolean isLiked;
    public boolean isDisliked;

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

        OADatabase.getIfStoryIsBookmarked(this, OAApplication.getUser());

        OADatabase.getLikesForStory(this, new OAFirebaseCallback() {
            @Override
            public void onSuccess(Object object) {
                usersIdThatLiked = (List<String>) object;
                if (OAUtil.contains(usersIdThatLiked, OAApplication.getUser().getId()))
                    isLiked = true;
            }

            @Override
            public void onFailure(DatabaseError databaseError) {
                usersIdThatLiked = new ArrayList<>();
            }
        });

        OADatabase.getDislikesForStory(this, new OAFirebaseCallback() {
            @Override
            public void onSuccess(Object object) {
                usersIdThatDisliked = (List<String>) object;
                if (OAUtil.contains(usersIdThatDisliked, OAApplication.getUser().getId()))
                    isDisliked = true;
            }

            @Override
            public void onFailure(DatabaseError databaseError) {
                usersIdThatDisliked = new ArrayList<>();
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

    public String getTagsString() {
        String string = "";
        if (tags != null) {
            for (String tag : tags) {
                if (string.equals(""))
                    string = "#" + tag;
                else
                    string = string + " #" + tag;
            }
        }
        return string;
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

    public List<String> getUsersIdThatLiked() {
        return usersIdThatLiked;
    }

    public List<String> getUsersIdThatDisliked() {
        return usersIdThatDisliked;
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
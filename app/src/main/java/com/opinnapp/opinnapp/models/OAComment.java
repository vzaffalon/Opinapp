package com.opinnapp.opinnapp.models;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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
        OAUser.getUserWithID(userID, new OAFirebaseCallback() {
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

    public static void getCommentsWithStoryID(String id, final OAFirebaseCallback callback) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("comment");
        Query queryRef = usersRef.orderByChild("storyID").equalTo(id);

        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<OAComment> comments = new ArrayList<OAComment>();
                for (DataSnapshot userSnap : dataSnapshot.getChildren()) {
                    OAComment comment  = userSnap.getValue(OAComment.class);
                    comment.setObjectsValuesWithFirebaseIds();
                    comments.add(comment);
                }
                callback.onSuccess(comments);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError);
            }
        });
    }

    //todo apagar
    public void setDefaultValues() {
        text = "Isso é um comentario 2.";
        userID = "Ahsushau2389835udshs";
        storyID = "-Kl6aXvpNxn7xHQe-1-o";
    }
}

package com.opinnapp.opinnapp.models;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cayke on 26/05/17.
 */

public class OADatabase {
    //region USER
    public static boolean createUser (OAUser user) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("user");
        DatabaseReference newUserRef = usersRef.push();

        user.setId(newUserRef.getKey());
        newUserRef.setValue(user.firebaseRepresentation());
        return true;
    }

    //for login
    public static void getUserWithFacebookID(String id, final OAFirebaseCallback callback) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("user");
        Query queryRef = usersRef.orderByChild("facebookID").equalTo(id);

        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnap : dataSnapshot.getChildren()) {
                    OAUser user = userSnap.getValue(OAUser.class);
                    callback.onSuccess(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError);
            }
        });
    }

    public static void getUserWithID(String id, final OAFirebaseCallback callback) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("user/" + id);

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                OAUser user = dataSnapshot.getValue(OAUser.class);
                callback.onSuccess(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError);
            }
        });
    }
    //endregion


    //region Story
    public static boolean createStory(OAStory story) {
        DatabaseReference storyRef = FirebaseDatabase.getInstance().getReference("story");
        DatabaseReference newStoryRef = storyRef.push();

        story.setId(newStoryRef.getKey());
        newStoryRef.setValue(story.firebaseRepresentation());
        return true;
    }

    public static void getAllStories(final OAFirebaseCallback callback) {
        DatabaseReference storyRef = FirebaseDatabase.getInstance().getReference("story");
        storyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<OAStory> stories = new ArrayList<OAStory>();

                for (DataSnapshot userSnap : dataSnapshot.getChildren()) {
                    String type = (String) userSnap.child("type").getValue();

                    if (type != null && type.equals("OAStoryMultiChoiceImages")) {
                        OAStoryMultiChoiceImages story = userSnap.getValue(OAStoryMultiChoiceImages.class);
                        story.setObjectsValuesWithFirebaseIds();
                        stories.add(story);
                    }
                    else if (type != null && type.equals("OAStoryTextOnly")) {
                        OAStoryTextOnly story = userSnap.getValue(OAStoryTextOnly.class);
                        story.setObjectsValuesWithFirebaseIds();
                        stories.add(story);
                    }
                    else {
                        OAStory story = userSnap.getValue(OAStory.class);
                        story.setObjectsValuesWithFirebaseIds();
                        stories.add(story);
                    }
                }

                callback.onSuccess(stories);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError);
            }
        });
    }
    //endregion


    //region Comment
    public static boolean createComment(OAComment comment) {
        DatabaseReference storyRef = FirebaseDatabase.getInstance().getReference("comment");
        DatabaseReference newStoryRef = storyRef.push();

        comment.setId(newStoryRef.getKey());
        newStoryRef.setValue(comment.firebaseRepresentation());

        return true;
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
    //endregion

    //region Images
    public static void uploadImage(File image) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://opinapp-c6381.appspot.com");
        StorageReference imagesRef = storageRef.child("images/test.jpg");

        Uri uri = Uri.fromFile(image);
        UploadTask uploadTask = imagesRef.putFile(uri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                System.out.print("erro");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
            }
        });
    }


    //endregion

}
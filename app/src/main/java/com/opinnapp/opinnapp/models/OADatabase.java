package com.opinnapp.opinnapp.models;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by cayke on 26/05/17.
 */

public class OADatabase {

    public static void teste () {
//        FirebaseAuth mFirebaseAuth;
//        FirebaseUser mFirebaseUser;
//        DatabaseReference mDatabase;

        // Initialize Firebase Auth and Database Reference
//        mFirebaseAuth = FirebaseAuth.getInstance();
//        mFirebaseUser = mFirebaseAuth.getCurrentUser();
//        mDatabase = FirebaseDatabase.getInstance().getReference();



        // createUser();
        // loadUser();
        // createStory();
        // loadStories();
        // createComment();
        //getComments();
    }

    private static void getComments() {
        DatabaseReference storyRef = FirebaseDatabase.getInstance().getReference("comment");
        storyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnap : dataSnapshot.getChildren()) {
                    OAComment comment  = userSnap.getValue(OAComment.class);
                    comment.setObjectsValuesWithFirebaseIds();
                    System.out.println(comment.getText());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private static void createComment() {
        OAComment comment = new OAComment();
        comment.setDefaultValues();

        DatabaseReference storyRef = FirebaseDatabase.getInstance().getReference("comment");
        DatabaseReference newStoryRef = storyRef.push();
        comment.setId(newStoryRef.getKey());
        newStoryRef.setValue(comment.firebaseRepresentation());

        updateStory(comment);
    }

    private static void updateStory(final OAComment comment) {
        final DatabaseReference storyRef = FirebaseDatabase.getInstance().getReference("story/" + comment.getStoryID());
        storyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String type = (String) dataSnapshot.child("type").getValue();
                OAStory story = null;

                if (type != null && type.equals("OAStoryMultiChoiceImages")) {
                    story = dataSnapshot.getValue(OAStoryMultiChoiceImages.class);
                    System.out.println(story.getDescription());
                }
                else if (type != null && type.equals("OAStoryTextOnly")) {
                    story = dataSnapshot.getValue(OAStoryTextOnly.class);
                    System.out.println(story.getDescription());
                }
                else {
                    story = dataSnapshot.getValue(OAStory.class);
                    System.out.println(story.getDescription());
                }

                story.setObjectsValuesWithFirebaseIds();

                if (story.comments == null)
                    story.comments = new ArrayList<OAComment>();

                story.comments.add(comment);
                storyRef.setValue(story.firebaseRepresentation());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private static void getStory(final String id) {
        DatabaseReference storyRef = FirebaseDatabase.getInstance().getReference("story/"+id);
        storyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    String type = (String) dataSnapshot.child("type").getValue();

                    if (type != null && type.equals("OAStoryMultiChoiceImages")) {
                        OAStoryMultiChoiceImages story = dataSnapshot.getValue(OAStoryMultiChoiceImages.class);
                        System.out.println(story.getDescription());
                    }
                    else if (type != null && type.equals("OAStoryTextOnly")) {
                        OAStoryTextOnly story = dataSnapshot.getValue(OAStoryTextOnly.class);
                        System.out.println(story.getDescription());
                    }
                    else {
                        OAStory story = dataSnapshot.getValue(OAStory.class);
                        System.out.println(story.getDescription());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private static void loadStories() {
        DatabaseReference storyRef = FirebaseDatabase.getInstance().getReference("story");
        storyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnap : dataSnapshot.getChildren()) {
                    String type = (String) userSnap.child("type").getValue();

                    if (type != null && type.equals("OAStoryMultiChoiceImages")) {
                        OAStoryMultiChoiceImages story = userSnap.getValue(OAStoryMultiChoiceImages.class);
                        System.out.println(story.getDescription());
                    }
                    else if (type != null && type.equals("OAStoryTextOnly")) {
                        OAStoryTextOnly story = userSnap.getValue(OAStoryTextOnly.class);
                        System.out.println(story.getDescription());
                    }
                    else {
                        OAStory story = userSnap.getValue(OAStory.class);
                        System.out.println(story.getDescription());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
    private static void createStory() {
        OAStoryTextOnly story = new OAStoryTextOnly();
        story.setDefaultValue("Gente essa historia aqui so tem texto mesmo.");

        DatabaseReference storyRef = FirebaseDatabase.getInstance().getReference("story");
        DatabaseReference newStoryRef = storyRef.push();
        story.setId(newStoryRef.getKey());
        newStoryRef.setValue(story.firebaseRepresentation());
    }

    private static void createUser () {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("user");
        DatabaseReference newUserRef = usersRef.push();

        OAUser user = new OAUser();
        user.setDefaultValues3();
        newUserRef.setValue(user);
    }

    private static void loadUser() {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("user");

        // Attach a listener to read the data at our posts reference
//        usersRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                OAUser user = dataSnapshot.getValue(OAUser.class);
//                System.out.println(user.getName());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                System.out.println("The read failed: " + databaseError.getCode());
//            }
//        });

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnap : dataSnapshot.getChildren()) {
                    OAUser user = userSnap.getValue(OAUser.class);
                    System.out.println(user.getName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

//        usersRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                OAUser user = dataSnapshot.getValue(OAUser.class);
//                System.out.println(user.getName());
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                System.out.println("The read failed: " + databaseError.getCode());
//            }
//        });
    }
}

package com.opinnapp.opinnapp.models;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        //createStory();
    }

    private static void createStory() {
        OAStory story = new OAStory();
        story.setDefaultValue("É pave ou pacume?");

        DatabaseReference storyRef = FirebaseDatabase.getInstance().getReference("story");
        DatabaseReference newStoryRef = storyRef.push();
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

package com.opinnapp.opinnapp.models;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by cayke on 09/05/17.
 */

public class OAUser implements OAFirebaseModel{
    private String name;
    private String url; //@username
    private String id;
    private String imagePath;

    public OAUser() {}

    @Override
    public Object firebaseRepresentation() {
        return this;
    }

    @Override
    public void setObjectsValuesWithFirebaseIds() {

    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getId() {
        return id;
    }

    public String getImagePath() {
        return imagePath;
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

    //todo apagar
    public void setDefaultValues () {
        name = "Cayke";
        url = "caykegsp";
        id = "Ahsushau2389835udshs";
        imagePath = "https://aws.unb.com/ashuiash.jpg";
    }

    public void setDefaultValues2 () {
        name = "Murilo";
        url = "muriloZaffa";
        id = "asdhfg456";
        imagePath = "https://aws.unb.com/muriol.jpg";
    }

    public void setDefaultValues3 () {
        name = "Murilin";
        url = "vZaffalon";
        id = "as2534";
        imagePath = "https://aws.unb.com/indiano.jpg";
    }
}

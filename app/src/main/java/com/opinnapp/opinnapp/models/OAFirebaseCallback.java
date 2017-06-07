package com.opinnapp.opinnapp.models;

import com.google.firebase.database.DatabaseError;

/**
 * Created by cayke on 06/06/17.
 */

public interface OAFirebaseCallback {
        void onSuccess(Object object);
        void onFailure(DatabaseError databaseError);
}

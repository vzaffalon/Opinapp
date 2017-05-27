package com.opinnapp.opinnapp.models;

/**
 * Created by cayke on 26/05/17.
 */

public interface OAFirebaseModel {
    public Object firebaseRepresentation();
    void setObjectsValuesWithFirebaseIds();
}

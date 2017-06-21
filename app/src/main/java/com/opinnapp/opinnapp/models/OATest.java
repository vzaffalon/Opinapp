package com.opinnapp.opinnapp.models;

import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by cayke on 06/06/17.
 */

public class OATest {
    static boolean shouldDoTests = false;

    public void initTests() {
        if (shouldDoTests) {

        }
    }

    private void createUser() {
        OAStub.createUser("Cayke", "caykegsp", "facebookID_teste", "https://scontent.fbsb4-1.fna.fbcdn.net/v/t1.0-9/17308748_1279495765466119_5902661537556998459_n.jpg?oh=91cd66e6173a2312be22ac33d3ed4445&oe=59A3777D");
    }

    private void getUser() {
        OAStub.getUserWithFacebookID("facebookID_teste");
        OAStub.getUserWithID("-Km-7lXAsUf_M7neaD_2");
    }

    private void createStory() {
        OADatabase.getUserWithFacebookID("facebookID_teste", new OAFirebaseCallback() {
            @Override
            public void onSuccess(Object object) {
                OAUser user = (OAUser) object;

                List<String> tags = new ArrayList<>();
                tags.add("unb");
                tags.add("formatura");
                tags.add("help");

                Date expirationDate = new Date();
                expirationDate.setMonth(8);

                OAStub.createStory("Galera como faco para achar orientador para TCC?", tags, user, expirationDate, null);
            }

            @Override
            public void onFailure(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private void createComment() {
        OADatabase.getUserWithFacebookID("facebookID_teste", new OAFirebaseCallback() {
            @Override
            public void onSuccess(Object object) {
                OAUser user = (OAUser) object;

                OAStub.createComment("voce pode tambem entrar no site do departamento e analisar os professores pelas areas deles.", "-Km-CASNbmQUzrBy1U0k", user);
            }

            @Override
            public void onFailure(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private void getStories() {
        OAStub.getStories();
    }
}

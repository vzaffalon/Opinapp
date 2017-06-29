package com.opinnapp.opinnapp.tabholder;

import android.app.Application;
import android.content.Context;

import com.opinnapp.opinnapp.models.OAUser;

/**
 * Created by cayke on 28/06/17.
 */

public class OAApplication extends Application {
    private static Context context;
    private static OAUser user;

    @Override
    public void onCreate() {
        super.onCreate();

        OAApplication.context = getApplicationContext();

        user = OAUser.loadUserFromDevice();
    }

    public static Context getContext() {
        return context;
    }

    public static OAUser getUser() {
        return user;
    }

    public static void setUser(OAUser user) {
        OAApplication.user = user;
    }
}

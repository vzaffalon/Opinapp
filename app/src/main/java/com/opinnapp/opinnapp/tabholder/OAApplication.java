package com.opinnapp.opinnapp.tabholder;

import android.app.Application;
import android.content.Context;

import com.opinnapp.opinnapp.R;
import com.opinnapp.opinnapp.models.OAUser;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

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

        //aply font to the app
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/programme.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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

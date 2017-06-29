package com.opinnapp.opinnapp.models;

import java.util.Date;
import java.util.Locale;

/**
 * Created by cayke on 29/06/17.
 */

public class OADateUtil {
    public static String exparationString(Date endDate) {
        Date now = new Date();

        long diff = endDate.getTime() - now.getTime();
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        if (hours > 1)
            return String.format(Locale.getDefault(), "%d:%d horas", hours, minutes%60);
        else
            return String.format(Locale.getDefault(), "%d:%d min", minutes, seconds);
    }
}

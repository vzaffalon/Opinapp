package com.opinnapp.opinnapp.models;

import java.util.Date;
import java.util.Locale;

/**
 * Created by cayke on 29/06/17.
 */

public class OADateUtil {
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;


    public static String getTimeAgo(long time) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = new Date().getTime();
        if (time > now || time <= 0) {
            return null;
        }

        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "Agora mesmo.";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "Um minuto atrás.";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutos atrás";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "Uma hora atrás";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " horas atrás";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "Ontem.";
        } else {
            return diff / DAY_MILLIS + " dias atrás.";
        }
    }

    public static String exparationString(Date endDate) {
        Date now = new Date();

        long diff = endDate.getTime() - now.getTime();
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        if (hours > 0)
            return String.format(Locale.getDefault(), "%02d:%02d horas", hours, minutes%60);
        else
            return String.format(Locale.getDefault(), "%02d minutos", minutes);
    }
}

package com.opinnapp.opinnapp.tabholder.notifications;

/**
 * Created by vzaffalon on 07/06/17.
 */

public class Notification {

    private String notification;
    private String time;
    private String userImage;
    private int notificationType;

    public Notification(String notification, String time, String userImage, int notificationType) {
        this.notification = notification;
        this.time = time;
        this.userImage = userImage;
        this.notificationType = notificationType;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public int getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(int notificationType) {
        this.notificationType = notificationType;
    }

}

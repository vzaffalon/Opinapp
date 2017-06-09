package com.opinnapp.opinnapp.tabholder.comments;

/**
 * Created by vzaffalon on 09/06/17.
 */

public class Comment {

    private String comment;
    private String time;
    private String userImage;

    public Comment(String comment, String time, String userImage) {
        this.comment = comment;
        this.time = time;
        this.userImage = userImage;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
}

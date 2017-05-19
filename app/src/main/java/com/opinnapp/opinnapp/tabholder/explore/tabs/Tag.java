package com.opinnapp.opinnapp.tabholder.explore.tabs;

import android.widget.TextView;

/**
 * Created by vzaffalon on 19/05/17.
 */

public class Tag {

    private String tagName;
    private String numberOfPosts;
    private String postRank;

    public Tag(String tagName, String numberOfPosts, String postRank) {
        this.tagName = tagName;
        this.numberOfPosts = numberOfPosts;
        this.postRank = postRank;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getNumberOfPosts() {
        return numberOfPosts;
    }

    public void setNumberOfPosts(String numberOfPosts) {
        this.numberOfPosts = numberOfPosts;
    }

    public String getPostRank() {
        return postRank;
    }

    public void setPostRank(String postRank) {
        this.postRank = postRank;
    }


}

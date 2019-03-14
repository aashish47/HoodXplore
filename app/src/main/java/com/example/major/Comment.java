package com.example.major;

public class Comment {
    private String comment;
    private String picture;

    public Comment() {
    }

    public Comment(String comment, String picture) {
        this.comment = comment;
        this.picture = picture;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

}

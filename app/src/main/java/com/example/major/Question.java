package com.example.major;

import android.net.Uri;

public class Question {
    String questionID;
    String question;
    String location;
    Double latitude;
    Double longitude;
    String name;
    String picture;

    public Question() {
    }

    public Question(String questionID, String question, String location, Double latitude, Double longitude, String name, String picture) {
        this.questionID = questionID;
        this.question = question;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.picture = picture;
    }

    public String getQuestionID() {
        return questionID;
    }

    public String getQuestion() {
        return question;
    }

    public String getLocation() {
        return location;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

//    public String getPicUri() {
//        return picture;
//    }
    public String getPicture(){
        return picture;
    }
}

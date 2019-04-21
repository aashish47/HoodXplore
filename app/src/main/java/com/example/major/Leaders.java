package com.example.major;

public class Leaders {
    String name;
    int score;
    String picture;

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public String getPicture() {
        return picture;
    }

    public Leaders(String name, int score, String picture) {
        this.name = name;
        this.score = score;
        this.picture = picture;
    }
}

package com.example.major;

public class LeaderBoard {
    String name;
    String picture;

    public LeaderBoard() {
    }

    public LeaderBoard(String name, String picture) {
        this.name = name;
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }
}

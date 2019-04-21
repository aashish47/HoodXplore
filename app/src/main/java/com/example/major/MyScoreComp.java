package com.example.major;

import java.util.Comparator;

public class MyScoreComp implements Comparator<Leaders> {

    @Override
    public int compare(Leaders e1, Leaders e2) {
        if(e1.getScore() < e2.getScore()){
            return 1;
        } else {
            return -1;
        }
    }

}

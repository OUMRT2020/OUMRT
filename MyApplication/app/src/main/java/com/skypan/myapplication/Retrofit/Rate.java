package com.skypan.myapplication.Retrofit;

public class Rate {
    private double score;
    private int times;

    public Rate(double score, int times) {
        this.score = score;
        this.times = times;
    }

    public double getScore() {
        return score;
    }

    public int getTimes() {
        return times;
    }
}

package com.skypan.myapplication.Retrofit;

public class Rate {
    private double score;
    private int times;

    public Rate() {
    }

    public Rate(double score, int times) {
        this.score = score;
        this.times = times;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }
}

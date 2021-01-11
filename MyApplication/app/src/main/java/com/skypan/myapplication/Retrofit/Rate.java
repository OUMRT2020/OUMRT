package com.skypan.myapplication.Retrofit;

import java.text.DecimalFormat;

public class Rate {
    private double score;
    private int times;

    public Rate(double score, int times) {
        this.score = score;
        this.times = times;
    }

    public double getScore() {
        DecimalFormat df = new DecimalFormat("0.00");
        return Double.parseDouble(df.format(score));
    }

    public int getTimes() {
        return times;
    }
}

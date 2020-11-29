package com.skypan.myapplication.ui;


import android.text.Editable;


public class Setting {
    private int count=0;
    private Editable name,start,end,starttime,endtime;
    private String gneder;
    private String helmet;
    private String Fee;

    public void setName(Editable string) {
        this.name = string;
    }
    public Editable getName() {
        return this.name;
    }
    public void setStart(Editable string) {
        this.start = string;
    }
    public Editable getStart() {
        return this.start;
    }
    public void setEnd(Editable string) {
        this.end = string;
    }
    public Editable getEnd() {
        return this.end;
    }

    public void setStartTime(Editable text) {
        this.starttime = text;
    }
    public Editable getStarttime() {
        return this.starttime;
    }
    public void setEndTime(Editable text) {
        this.endtime = text;
    }
    public Editable getEndtime() {
        return this.endtime;
    }
    public void setGender(String s) {
        this.gneder = s;
    }
    public String getGneder(){
        return this.gneder;
    }
    public void setHalmet(String s) {
        this.helmet=s;
    }
    public String getHelmet(){
        return this.helmet;
    }

    public void setFee(String s) {
        this.Fee=s;
    }
    public String getFee(){
        return this.Fee;
    }
    @Override
    public String toString(){
        String result =  getName() +" " + getStart()+" " + getEnd()+" " + getStarttime()+" "
                + getEndtime()+" " + getGneder()+" " + getHelmet() +" " + getFee();

        return result;
    }
}

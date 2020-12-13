package com.skypan.myapplication.driver_model;


import android.text.Editable;


public class Setting {
    private int count = 0;
    private Editable name;
    private String[] start = new String[10];
    private String[] place = new String[3];
    private String[] day = new String[7];
    private Editable end;
    private Editable money;
    private Editable starttime;
    private Editable endtime;
    private String gneder;
    private String helmet;

    public Editable getName() {
        return this.name;
    }

    public void setName(Editable string) {
        this.name = string;
    }

    public void setStart(String string, int i) {
        this.start[i] = string;
    }

    public String getStart1(int i ) {
        return this.start[i];
    }
    public void setPlace(String string, int i) {
        this.place[i] = string;
    }
    public String getPlace() {
        return this.place[0] +"\n"+ this.place[1] +"\n"+ this.place[2];
    }

    public void setDay(String string, int i) {
        this.day[i] = string;
    }
    public void deleteDay( int i) {
        this.day[i] = null;
    }
    public String getDay(int i ) {
        return this.day[i];
    }


    public Editable getEnd() {
        return this.end;
    }

    public void setEnd(Editable string) {
        this.end = string;
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

    public String getGneder() {
        return this.gneder;
    }

    public void setHalmet(String s) {
        this.helmet = s;
    }

    public String getHelmet() {
        return this.helmet;
    }

    public void setMoney(Editable text) {
        this.money=text;
    }
    public Editable getMoney() {
        return this.money;
    }

    @Override
    public String toString() {
        String result = getName() + " " + getPlace() + " " + getEnd() + " " + getStarttime() + " "
                + getEndtime() + " " + getGneder() + " " + getHelmet() + " " + getMoney();

        return result;
    }

}
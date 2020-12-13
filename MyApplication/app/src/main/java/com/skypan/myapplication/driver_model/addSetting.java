package com.skypan.myapplication.driver_model;

import com.skypan.myapplication.Retrofit.Event;

import java.util.ArrayList;

public class addSetting {

    public static ArrayList<Event> Set = new ArrayList<>();

    public static void addSetting(Event s) {
        Set.add(s);
        int i =Set.size();
//        System.out.println(Set.get(i).getAcceptable_sex());
    }

}

package com.skypan.myapplication.driver_model;

import com.skypan.myapplication.driver_model.ui.Setting;

import java.util.ArrayList;

public class addSetting {

    public static ArrayList<Setting> Set = new ArrayList<>();
    public static void addSetting(Setting s){
        Set.add(s);
        System.out.println(Set.size());
    }
    public static String getSetting(int i){
        return Set.get(i).toString();
    }
}

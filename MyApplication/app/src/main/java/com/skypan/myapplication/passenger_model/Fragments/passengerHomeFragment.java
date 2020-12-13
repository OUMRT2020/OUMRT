package com.skypan.myapplication.passenger_model.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skypan.myapplication.R;
import com.skypan.myapplication.Retrofit.Event;
import com.skypan.myapplication.Retrofit.Rate;
import com.skypan.myapplication.Retrofit.User;
import com.skypan.myapplication.passenger_model.Adapters.MainEventAdapter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class passengerHomeFragment extends Fragment {
    private RecyclerView mainRecycler;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_passenger_home, container, false);
        // todo :連線API
        //這是測試資料
        //new出一堆物件假裝拿回json了
        Date start = new Date();
        Date end = new Date();
        Log.d("TAG", start.toString());
        ArrayList<Event> es = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Rate rate = new Rate(i, 5);
            URL url = null;
            try {
                url = new URL("http://example.com/");
            } catch (MalformedURLException malformedURLException) {
                malformedURLException.printStackTrace();
            }
            User user = new User("AAA", "token", "峻峻", "48763", true, 87, url, rate);
            Event e = new Event("AAA", "金瓜石特快車" + i, "white", "BBB", "CCC", new ArrayList<Date>(Arrays.asList(start, end)), new ArrayList<String>(Arrays.asList("地點一", "地點二")),
                    new ArrayList<String>(Arrays.asList("地點三", "地點四")), 0, 87, 48763 + i, true, new ArrayList<Boolean>(Arrays.asList(true, true, true, true, true, true, true)), user);
            Event e2 = new Event("AAA", "金瓜石特快車" + i, "green", "BBB", "CCC", new ArrayList<Date>(Arrays.asList(start, end)), new ArrayList<String>(Arrays.asList("地點一", "地點二")),
                    new ArrayList<String>(Arrays.asList("地點三", "地點四")), 0, 87, 48763 + i, true, new ArrayList<Boolean>(Arrays.asList(true, true, true, true, true, true, true)), user);
            Event e3 = new Event("AAA", "金瓜石特快車" + i, "red", "BBB", "CCC", new ArrayList<Date>(Arrays.asList(start, end)), new ArrayList<String>(Arrays.asList("地點一", "地點二")),
                    new ArrayList<String>(Arrays.asList("地點三", "地點四")), 0, 87, 48763 + i, true, new ArrayList<Boolean>(Arrays.asList(true, true, true, true, true, true, true)), user);
            es.add(e);
            es.add(e2);
            es.add(e3);
        }
        //測試資料結束

        mainRecycler = view.findViewById(R.id.mainRecycler);
        mainRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mainRecycler.setAdapter(new MainEventAdapter(getActivity(), es));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // todo :連線API
        //這是測試資料
        //new出一堆物件假裝拿回json了
        Date start = new Date();
        Date end = new Date();
        ArrayList<Event> es = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Rate rate = new Rate(i, 5);
            URL url = null;
            try {
                url = new URL("http://example.com/");
            } catch (MalformedURLException malformedURLException) {
                malformedURLException.printStackTrace();
            }
            User user = new User("AAA", "token", "峻峻", "48763", true, 87, url, rate);
            Event e = new Event("AAA", "金瓜石特快車" + i, "white", "BBB", "CCC", new ArrayList<Date>(Arrays.asList(start, end)), new ArrayList<String>(Arrays.asList("地點一", "地點二")),
                    new ArrayList<String>(Arrays.asList("地點三", "地點四")), 0, 87, 48763 + i, true, new ArrayList<Boolean>(Arrays.asList(true, true, true, true, true, true, true)), user);
            Event e2 = new Event("AAA", "金瓜石特快車" + i, "green", "BBB", "CCC", new ArrayList<Date>(Arrays.asList(start, end)), new ArrayList<String>(Arrays.asList("地點一", "地點二")),
                    new ArrayList<String>(Arrays.asList("地點三", "地點四")), 0, 87, 48763 + i, true, new ArrayList<Boolean>(Arrays.asList(true, true, true, true, true, true, true)), user);
            Event e3 = new Event("AAA", "金瓜石特快車" + i, "red", "BBB", "CCC", new ArrayList<Date>(Arrays.asList(start, end)), new ArrayList<String>(Arrays.asList("地點一", "地點二")),
                    new ArrayList<String>(Arrays.asList("地點三", "地點四")), 0, 87, 48763 + i, true, new ArrayList<Boolean>(Arrays.asList(true, true, true, true, true, true, true)), user);
            es.add(e);
            es.add(e2);
            es.add(e3);
        }
        mainRecycler = view.findViewById(R.id.mainRecycler);
        mainRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mainRecycler.setAdapter(new MainEventAdapter(getActivity(), es));
    }
}
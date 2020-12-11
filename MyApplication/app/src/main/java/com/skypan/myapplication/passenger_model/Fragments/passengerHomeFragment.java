package com.skypan.myapplication.passenger_model.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.skypan.myapplication.R;
import com.skypan.myapplication.Retrofit.Event;
import com.skypan.myapplication.Retrofit.RetrofitManagerAPI;
import com.skypan.myapplication.passenger_model.Adapters.SearchedEventAdapter;
import com.skypan.myapplication.passenger_model.SearchEventsActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class passengerHomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_passenger_home, container, false);
//        Retrofit retrofit = new Retrofit.Builder()
//                        .baseUrl("https://heroku/")//todo :峻峻的API
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build();
//        RetrofitManagerAPI retrofitManagerAPI = retrofit.create(RetrofitManagerAPI.class);
//                Call<List<Event>> call = retrofitManagerAPI.getMain(new String("userID"),"passenger");
//                call.enqueue(new Callback<List<Event>>() {
//                    @Override
//                    public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
//                        if (!response.isSuccessful()) {
//                            Log.d("404 error", String.valueOf(response.code()));
//                        }
//                        List<Event> events = response.body();
//                        recyclerView = findViewById(R.id.rv_searched_events);
//                        recyclerView.setLayoutManager(new LinearLayoutManager(SearchEventsActivity.this));
//                        recyclerView.setAdapter(new SearchedEventAdapter(SearchEventsActivity.this, events));
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<Event>> call, Throwable t) {
//                        Log.d(TAG, t.getMessage());
//                    }
//                });
        return view;
    }
}
package com.skypan.myapplication.passenger_model.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.skypan.myapplication.R;
import com.skypan.myapplication.Retrofit.Event;
import com.skypan.myapplication.Retrofit.RetrofitManagerAPI;
import com.skypan.myapplication.passenger_model.Adapters.MainEventAdapter;
import com.skypan.myapplication.passenger_model.PassengerMainActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class passengerHomeFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mainRecycler;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_passenger_home, container, false);
        swipeRefreshLayout = view.findViewById(R.id.passenger_main_refreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {//refresh
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://140.121.197.130:5601/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RetrofitManagerAPI retrofitManagerAPI = retrofit.create(RetrofitManagerAPI.class);
                Call<List<Event>> call = retrofitManagerAPI.getPassengerMain(((PassengerMainActivity) getActivity()).user_id);

                call.enqueue(new Callback<List<Event>>() {
                    @Override
                    public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                        if (!response.isSuccessful()) {
                            Log.d("TAG1", String.valueOf(response.code()));
                        }
                        try {
                            List<Event> events = response.body();
                            if (events == null) {
                                throw new NullPointerException("沒有回傳值");
                            }
                            mainRecycler = view.findViewById(R.id.mainRecycler);
                            mainRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                            mainRecycler.setAdapter(new MainEventAdapter(getActivity(), events));
                            swipeRefreshLayout.setRefreshing(false);

                        } catch (Exception e) {
                            Log.d("error", e.getMessage());
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Event>> call, Throwable t) {
                        Log.d("TAG2", t.getMessage());
                        swipeRefreshLayout.setRefreshing(false);
                    }

                });

            }
        });
        //第一次加載時呼叫
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://140.121.197.130:5601/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitManagerAPI retrofitManagerAPI = retrofit.create(RetrofitManagerAPI.class);
        Log.d("fragment user_id", ((PassengerMainActivity) getActivity()).user_id);
        Call<List<Event>> call = retrofitManagerAPI.getPassengerMain(((PassengerMainActivity) getActivity()).user_id);
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (!response.isSuccessful()) {
                    Log.d("passengerHomeFragment", String.valueOf(response.code()));
                }
                try {
                    List<Event> events = response.body();
                    if (events == null) {
                        throw new NullPointerException("沒有回傳值");
                    }
                    mainRecycler = view.findViewById(R.id.mainRecycler);
                    mainRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mainRecycler.setAdapter(new MainEventAdapter(getActivity(), events));
                } catch (Exception e) {
                    Log.d("passengerHomeFragment", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Log.d("TAG2", t.getMessage());
            }
        });
        return view;
    }

    @Override
    public void onResume() {//返回時呼叫
        super.onResume();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://database87.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitManagerAPI retrofitManagerAPI = retrofit.create(RetrofitManagerAPI.class);
        Call<List<Event>> call = retrofitManagerAPI.getPassengerMain(((PassengerMainActivity) getActivity()).user_id);
//        Call<List<Event>> call = retrofitManagerAPI.getPassengerMain("JIU");//todo :修改user_id = uuid

        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (!response.isSuccessful()) {
                    Log.d("TAG1", String.valueOf(response.code()));
                }
                try {
                    List<Event> events = response.body();
                    if (events == null) {
                        throw new NullPointerException("沒有回傳值");
                    }
                    mainRecycler = view.findViewById(R.id.mainRecycler);
                    mainRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mainRecycler.setAdapter(new MainEventAdapter(getActivity(), events));
                } catch (Exception e) {
                    Log.d("error", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Log.d("TAG2", t.getMessage());
            }
        });
    }
}
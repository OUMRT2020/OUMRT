package com.skypan.myapplication.driver_model.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.skypan.myapplication.R;
import com.skypan.myapplication.Retrofit.Event;
import com.skypan.myapplication.Retrofit.RetrofitManagerAPI;
import com.skypan.myapplication.driver_model.DriverMainActivity;
import com.skypan.myapplication.passenger_model.Adapters.MainEventAdapter;
import com.skypan.myapplication.passenger_model.PassengerMainActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.skypan.myapplication.driver_model.addSetting.Set;

public class HomeFragment extends Fragment {


    private HomeViewModel homeViewModel;
    private TextView Ed_1;
    private Button refresh;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        //這邊可直接插入一般教學中的activity，不用使用fragment
        View root = inflater.inflate(R.layout.activity_text, container, false);

        swipeRefreshLayout = root.findViewById(R.id.driver_main_refreshLayout);
        String user_id = ((DriverMainActivity) getActivity()).user_id;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://140.121.197.130:5601/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitManagerAPI retrofitManagerAPI = retrofit.create(RetrofitManagerAPI.class);
        Call<List<Event>> call = retrofitManagerAPI.getDriverMain(user_id);
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (!response.isSuccessful()) {
                    Log.d("add", "new driver main error");

                }
                List<Event> events = response.body();

                recyclerView = root.findViewById(R.id.rv_searched_events);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(new SearchedDriveEventAdapter(getContext(), events));
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Log.d("add", "new driver main server error");
            }
        });



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {//refresh
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://140.121.197.130:5601/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RetrofitManagerAPI retrofitManagerAPI = retrofit.create(RetrofitManagerAPI.class);
                Call<List<Event>> call = retrofitManagerAPI.getDriverMain(user_id);
                call.enqueue(new Callback<List<Event>>() {
                    @Override
                    public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                        if (!response.isSuccessful()) {
                            Log.d("add", "new driver main error");
                            swipeRefreshLayout.setRefreshing(false);

                        }
                        List<Event> events = response.body();

                        recyclerView = root.findViewById(R.id.rv_searched_events);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                        recyclerView.setAdapter(new SearchedDriveEventAdapter(getContext(), events));
                        swipeRefreshLayout.setRefreshing(false);

                    }

                    @Override
                    public void onFailure(Call<List<Event>> call, Throwable t) {
                        Log.d("add", "new driver main server error");
                        swipeRefreshLayout.setRefreshing(false);

                    }
                });
            }


        });
//
//
//        float_refresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Retrofit retrofit = new Retrofit.Builder()
//                        .baseUrl("https://database87.herokuapp.com/")
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build();
//                RetrofitManagerAPI retrofitManagerAPI = retrofit.create(RetrofitManagerAPI.class);
//                Call<List<Event>> call = retrofitManagerAPI.getDriverMain(user_id);
//                call.enqueue(new Callback<List<Event>>() {
//                    @Override
//                    public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
//                        if (!response.isSuccessful()) {
//                            Log.d("add", "new driver main error");
//
//                        }
//                        List<Event> events = response.body();
//
//                        recyclerView = root.findViewById(R.id.rv_searched_events);
//                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//                        recyclerView.setAdapter(new SearchedDriveEventAdapter(getContext(), events));
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<Event>> call, Throwable t) {
//                        Log.d("add", "new driver main server error");
//                    }
//                });
//            }
//        });
        return root;
    }

}
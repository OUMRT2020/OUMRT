package com.skypan.myapplication.driver_model.ui.history;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.skypan.myapplication.R;
import com.skypan.myapplication.Retrofit.Event;
import com.skypan.myapplication.Retrofit.Past_Event;
import com.skypan.myapplication.Retrofit.RetrofitManagerAPI;
import com.skypan.myapplication.driver_model.DriverMainActivity;
import com.skypan.myapplication.driver_model.ui.home.SearchedDriveEventAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HistoryFragment extends Fragment {

    private HistoryViewModel slideshowViewModel;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(HistoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView = root.findViewById(R.id.rv_histroy_event);

        swipeRefreshLayout = root.findViewById(R.id.history_refreshLayout);
        String user_id = ((DriverMainActivity) getActivity()).user_id;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://140.121.197.130:5602/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitManagerAPI retrofitManagerAPI = retrofit.create(RetrofitManagerAPI.class);
        Call<List<Past_Event>> call = retrofitManagerAPI.searchPast(user_id);
        call.enqueue(new Callback<List<Past_Event>>() {
            @Override
            public void onResponse(Call<List<Past_Event>> call, Response<List<Past_Event>> response) {
                if (!response.isSuccessful()) {
                    Log.d("add", "new driver main error");

                }
                List<Past_Event> events = response.body();

                recyclerView = root.findViewById(R.id.rv_histroy_event);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(new SearchedHistoryAdapter(getContext(),events));
            }

            @Override
            public void onFailure(Call<List<Past_Event>> call, Throwable t) {
                Log.d("add", "new driver main server error");
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {//refresh
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://140.121.197.130:5602/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RetrofitManagerAPI retrofitManagerAPI = retrofit.create(RetrofitManagerAPI.class);
                Call<List<Past_Event>> call = retrofitManagerAPI.searchPast(user_id);
                call.enqueue(new Callback<List<Past_Event>>() {
                    @Override
                    public void onResponse(Call<List<Past_Event>> call, Response<List<Past_Event>> response) {
                        if (!response.isSuccessful()) {
                            Log.d("add", "new driver main error");

                        }
                        List<Past_Event> events = response.body();

                        recyclerView = root.findViewById(R.id.rv_histroy_event);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(new SearchedHistoryAdapter(getContext(),events));
                    }

                    @Override
                    public void onFailure(Call<List<Past_Event>> call, Throwable t) {
                        Log.d("add", "new driver main server error");
                    }
                });
            }
        });


        return root;
    }
}
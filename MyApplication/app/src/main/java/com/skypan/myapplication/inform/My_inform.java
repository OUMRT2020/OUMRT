package com.skypan.myapplication.inform;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toolbar;

import com.skypan.myapplication.R;
import com.skypan.myapplication.Retrofit.Inform;
import com.skypan.myapplication.Retrofit.RetrofitManagerAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class My_inform extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private String mUser_id;
    private Inform mInform;
    private RecyclerView inform_driver, inform_passenger;
    private androidx.appcompat.app.ActionBar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_inform);
        inform_driver = findViewById(R.id.inform_driver);
        inform_passenger = findViewById(R.id.inform_passenger);
        sharedPreferences=getSharedPreferences("isOUMRTLogin", Context.MODE_PRIVATE);
        toolbar = getSupportActionBar();
        toolbar.setDisplayHomeAsUpEnabled(true);
        mUser_id = sharedPreferences.getString("user_id","");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://140.121.197.130:5602/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitManagerAPI retrofitManagerAPI = retrofit.create(RetrofitManagerAPI.class);
        Call<Inform> call = retrofitManagerAPI.getInforms(mUser_id);
        call.enqueue(new Callback<Inform>() {
            @Override
            public void onResponse(Call<Inform> call, Response<Inform> response) {
                if (!response.isSuccessful()) {
                    Log.d("get_inform", "Bad request");
                }else{
                    mInform = response.body();
                    inform_driver.setLayoutManager(new LinearLayoutManager(My_inform.this));
                    inform_driver.setAdapter(new informAdapter(My_inform.this,mInform.getDriver_context()));
                    inform_passenger.setLayoutManager(new LinearLayoutManager(My_inform.this));
                    inform_passenger.setAdapter(new informAdapter(My_inform.this,mInform.getPassenger_context()));
                }
            }

            @Override
            public void onFailure(Call<Inform> call, Throwable t) {
                Log.d("get_inform", "Server error");
            }
        });

    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
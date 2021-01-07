package com.skypan.myapplication.login_model;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skypan.myapplication.R;
import com.skypan.myapplication.Retrofit.RetrofitManagerAPI;
import com.skypan.myapplication.Retrofit.User;
import com.skypan.myapplication.driver_model.DriverMainActivity;
import com.skypan.myapplication.passenger_model.PassengerMainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class select_identityActivity extends AppCompatActivity {
    private String user_id;
    private boolean isLogin;
    private Button driver_button;
    private Button passenger_button;
    private ProgressBar progressCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_identity);
        progressCircle = findViewById(R.id.progressBar_cyclic);
        driver_button = findViewById(R.id.driver_button);
        passenger_button = findViewById(R.id.passenger_button);
//        Intent intent = getIntent();
//        user_id = intent.getStringExtra("user_id");
//        Log.d("user_id", user_id);
        if (!getSharedPreferences("isOUMRTLogin", MODE_PRIVATE).getBoolean("isLogin", false)) {//如果沒有登入就去登入
            Intent intent = new Intent(select_identityActivity.this, loginActivity.class);
            startActivity(intent);
            finish();
        } else {
            //refresh profile, fake login
            //freeze window
            progressCircle.setVisibility(View.VISIBLE);
            driver_button.setEnabled(false);
            passenger_button.setEnabled(false);
            Toast.makeText(select_identityActivity.this, "正在確認資訊，請稍後", Toast.LENGTH_SHORT).show();
//            String tkn = FirebaseInstanceId.getInstance().getToken();
//            Toast.makeText(select_identityActivity.this, "register mTkn" + tkn, Toast.LENGTH_SHORT).show();
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            String email = getSharedPreferences("isOUMRTLogin", MODE_PRIVATE).getString("email", "");
            String password = getSharedPreferences("isOUMRTLogin", MODE_PRIVATE).getString("password", "");
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://140.121.197.130:5602/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            RetrofitManagerAPI retrofitManagerAPI = retrofit.create(RetrofitManagerAPI.class);
            Call<User> call = retrofitManagerAPI.login(password, email);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (!response.isSuccessful()) {//login failed=>密碼已經改過了
                        Toast.makeText(select_identityActivity.this, "password has changed", Toast.LENGTH_SHORT).show();
                        SharedPreferences preferences = getSharedPreferences("isOUMRTLogin", MODE_PRIVATE);//創建一個isLogin.xml
                        preferences.edit()
                                .clear()
                                .apply();
                        Intent intent = new Intent(select_identityActivity.this, loginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        SharedPreferences preferences = getSharedPreferences("isOUMRTLogin", MODE_PRIVATE);//創建一個isLogin.xml
                        preferences.edit()
                                .clear()
                                .putBoolean("isLogin", true)
                                .putString("email", email)
                                .putString("password", password)
                                .putString("user_id", response.body().getUser_id())
                                .putString("name", response.body().getName())
                                .putString("phone_num", response.body().getPhone_num())
                                .putString("sex", response.body().isSex() ? "男" : "女")
                                .putInt("weight", response.body().getWeight())
                                .putFloat("rate", (float) response.body().getRate().getScore())
                                .putString("car_pic_url", response.body().getPicture_url())
                                .apply();
                        user_id = response.body().getUser_id();
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        progressCircle.setVisibility(View.GONE);
                        driver_button.setEnabled(true);
                        passenger_button.setEnabled(true);
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(select_identityActivity.this, "server error", Toast.LENGTH_SHORT).show();
                    select_identityActivity.this.recreate();
                }
            });
        }

        driver_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(select_identityActivity.this, DriverMainActivity.class);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
                finish();
            }
        });

        passenger_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(select_identityActivity.this, PassengerMainActivity.class);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
                finish();
            }
        });
    }
}
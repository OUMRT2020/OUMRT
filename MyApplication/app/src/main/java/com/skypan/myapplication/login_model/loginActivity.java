package com.skypan.myapplication.login_model;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skypan.myapplication.R;
import com.skypan.myapplication.Retrofit.RetrofitManagerAPI;
import com.skypan.myapplication.Retrofit.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class loginActivity extends AppCompatActivity {
    private Button login_button;            // 宣告login_button
    private Button forget_password_button;  // 宣告forget_password_button
    private Button register_button;         // 宣告register_button

    // 以下要傳到後端的
    private TextView email;
    private TextView password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // find id
        login_button = findViewById(R.id.login_button);
        forget_password_button = findViewById(R.id.forget_password_button);
        register_button = findViewById(R.id.register_button);

        //以下要傳到後端的
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        //監聽器
        setListeners();
    }

    // 監聽器
    private void setListeners() {
        OnClick onClick = new OnClick();
        login_button.setOnClickListener(onClick);
        forget_password_button.setOnClickListener(onClick);
        register_button.setOnClickListener(onClick);
    }

    // when click ...
    private class OnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login_button:

                    if (email.getText().toString().equals("") || password.getText().toString().equals("")) {
                        Toast.makeText(loginActivity.this, "帳號密碼不得為空", Toast.LENGTH_SHORT).show();
                    } else {
                        Gson gson = new GsonBuilder()
                                .setLenient()
                                .create();
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://140.121.197.130:5602/")
                                .addConverterFactory(GsonConverterFactory.create(gson))
                                .build();
                        RetrofitManagerAPI retrofitManagerAPI = retrofit.create(RetrofitManagerAPI.class);
                        String temp_password = password.getText().toString();
                        String temp_mail = email.getText().toString();
                        Call<User> call = retrofitManagerAPI.login(temp_password, temp_mail);
                        call.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if (!response.isSuccessful()) {
                                    Log.d("login failed: ", response.message());
                                }
                                if (response.body().getUser_id().equals("Fail")) {
                                    Toast.makeText(loginActivity.this, "password has changed", Toast.LENGTH_SHORT).show();
                                    SharedPreferences preferences = getSharedPreferences("isOUMRTLogin", MODE_PRIVATE);//創建一個isLogin.xml
                                    preferences.edit()
                                            .clear()
                                            .apply();
                                    Intent intent = new Intent(loginActivity.this, loginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    SharedPreferences preferences = getSharedPreferences("isOUMRTLogin", MODE_PRIVATE);//創建一個isLogin.xml
                                    preferences.edit()
                                            .putBoolean("isLogin", true)
                                            .putString("email", temp_mail)
                                            .putString("password", temp_password)
                                            .putString("user_id", response.body().getUser_id())
                                            .putString("name", response.body().getName())
                                            .putString("phone_num", response.body().getPhone_num())
                                            .putString("sex", response.body().isSex() ? "男" : "女")
                                            .putInt("weight", response.body().getWeight())
                                            .putFloat("rate", (float) response.body().getRate().getScore())
                                            .putString("car_pic_url", response.body().getPicture_url())
                                            .apply();
                                    Intent intent = new Intent(loginActivity.this, select_identityActivity.class);
//                                    intent.putExtra("user_id", response.body());
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                Toast.makeText(loginActivity.this, "server error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    break;
                case R.id.forget_password_button:

                    // 跳轉到忘記密碼的頁面
                    Intent intent1 = new Intent(loginActivity.this, forget_password.class);
                    startActivity(intent1);
                    break;
                case R.id.register_button:

                    // 跳轉到register介面
                    Intent intent2 = new Intent(loginActivity.this, registerActivity.class);
                    startActivity(intent2);
                    break;
            }
        }
    }
}
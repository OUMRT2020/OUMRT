package com.skypan.myapplication.login_model;

import android.content.Intent;
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
                                .baseUrl("https://nmsl666.herokuapp.com/")
                                .addConverterFactory(GsonConverterFactory.create(gson))
                                .build();
                        RetrofitManagerAPI retrofitManagerAPI = retrofit.create(RetrofitManagerAPI.class);
                        Call<String> call = retrofitManagerAPI.login(password.getText().toString(), email.getText().toString());
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if (!response.isSuccessful()) {
                                    Log.d("error0", response.message());
                                }
                                if (response.body().equals("Fail")) {
                                    Toast.makeText(loginActivity.this, "login failed", Toast.LENGTH_SHORT).show();
                                } else {
                                    // 跳轉到登入後的頁面
                                    Intent intent = new Intent(loginActivity.this, select_identityActivity.class);
                                    intent.putExtra("user_id", response.body());
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
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
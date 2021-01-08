package com.skypan.myapplication.login_model;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.skypan.myapplication.R;
import com.skypan.myapplication.Retrofit.Ack;
import com.skypan.myapplication.Retrofit.RetrofitManagerAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class set_new_passwordActivity extends AppCompatActivity {
    private Button cancel_new_password;
    private Button verify_new_password;
    private EditText new_password;
    private EditText new_password_again;
    private String mail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);
        Intent intent = getIntent();
        mail = intent.getStringExtra("mail");
        // find id
        cancel_new_password = findViewById(R.id.cancel_new_password);
        verify_new_password = findViewById(R.id.verify_new_password);

        //監聽器
        setListeners();
    }

    // 監聽器
    private void setListeners() {
        OnClick onClick = new OnClick();
        cancel_new_password.setOnClickListener(onClick);
        verify_new_password.setOnClickListener(onClick);
    }

    // when click ...
    private class OnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cancel_new_password:

                    // 跳轉到輸入驗證信箱(忘記密碼的)畫面
                    Intent intent = null;
                    intent = new Intent(set_new_passwordActivity.this, verification_forgetActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.verify_new_password:

                    // 比較密碼
                    new_password = findViewById(R.id.new_password);
                    new_password_again = findViewById(R.id.new_password_again);
                    String password1 = new_password.getText().toString();
                    String password2 = new_password_again.getText().toString();
                    Log.d("alterpass_mail:" , mail);
                    Log.d("new pass:" , new_password_again.getText().toString());
                    if (password1.equals(password2)) {
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://140.121.197.130:5602/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        RetrofitManagerAPI retrofitManagerAPI = retrofit.create(RetrofitManagerAPI.class);


                        Call<Ack> call = retrofitManagerAPI.alter_password(mail, new_password_again.getText().toString());
                        call.enqueue(new Callback<Ack>() {
                            @Override
                            public void onResponse(Call<Ack> call, Response<Ack> response) {

                                if (!response.isSuccessful()) {

                                }
                                if (response.body().isSuccess()) {
// 跳轉到login介面
                                    System.out.println("密碼相同訊號 : 1");
                                    Intent intent = null;
                                    intent = new Intent(set_new_passwordActivity.this, loginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {

                                }

                            }

                            @Override
                            public void onFailure(Call<Ack> call, Throwable t) {

                            }
                        });

                    } else {
                        // 跳出警告視窗
                        // 不跳轉頁面
                        System.out.println("密碼不相同訊號 : 0");
                        openDialog();
                    }
                    break;
            }
        }
    }

    // 跳出警告視窗
    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(set_new_passwordActivity.this);
        builder.setTitle("密碼輸入錯誤");
        builder.setMessage("請重新輸入密碼!!");
        builder.create().show();
    }
}
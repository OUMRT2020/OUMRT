package com.skypan.myapplication.login_model;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skypan.myapplication.R;
import com.skypan.myapplication.Retrofit.Ack;
import com.skypan.myapplication.Retrofit.Auth;
import com.skypan.myapplication.Retrofit.Custom_register;
import com.skypan.myapplication.Retrofit.RetrofitManagerAPI;
import com.skypan.myapplication.Retrofit.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class verification_register extends AppCompatActivity {
    private Button cancel_verification_register;
    private Button verify_verification_register;
    private EditText verification_register;             // user輸入的認證碼
    private String message = registerActivity.message;  // 從registerActivity製造出來的認證碼

    // 以下為要傳到後端的資料
    private String mail = registerActivity.email_register.getText().toString().trim();
    private String password = registerActivity.password_register.getText().toString().trim();
    private int weight = Integer.parseInt(registerActivity.weight_register.getText().toString().trim());
    private String phone = registerActivity.phone_register.getText().toString().trim();
    private String nickname = registerActivity.nickname_register.getText().toString().trim();
    private boolean sex = registerActivity.sex.equals("男");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_register);

        // find id
        cancel_verification_register = findViewById(R.id.cancel_verification_register);
        verify_verification_register = findViewById(R.id.verify_verification_register);
        verification_register = findViewById(R.id.verification_register);

        // 監聽器
        setListeners();
    }

    //監聽器
    private void setListeners() {
        OnClick onClick = new OnClick();
        cancel_verification_register.setOnClickListener(onClick);
        verify_verification_register.setOnClickListener(onClick);
    }

    // when click ...
    private class OnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.cancel_verification_register:

                    // 跳轉到register頁面
                    intent = new Intent(verification_register.this, registerActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    break;
                case R.id.verify_verification_register:

                    // 比較驗證碼是否相符
                    boolean isEqual;
                    isEqual = compare();
                    if (isEqual) {

                        // 跳轉到login頁面
                        System.out.println("驗證碼相同訊號 : 1");
                        String tkn = FirebaseInstanceId.getInstance().getToken();
                        System.out.println("register mTkn"+tkn);
                        Custom_register custom_register = new Custom_register(new User(tkn, nickname, phone, sex, weight), new Auth(password, mail));

                        Gson gson = new GsonBuilder()
                                .setLenient()
                                .create();
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://140.121.197.130:5602/")
                                .addConverterFactory(GsonConverterFactory.create(gson))
                                .build();
                        RetrofitManagerAPI retrofitManagerAPI = retrofit.create(RetrofitManagerAPI.class);
                        Call<Ack> call = retrofitManagerAPI.register(custom_register);
                        call.enqueue(new Callback<Ack>() {
                            @Override
                            public void onResponse(Call<Ack> call, Response<Ack> response) {
                                if (!response.isSuccessful()) {
                                    Log.d("gginin1", response.message());
                                }
                                if(response.body().isSuccess()){

                                }else{
                                    Toast.makeText(verification_register.this, "註冊失敗: "+response.body().getReason(), Toast.LENGTH_SHORT);
                                }

                            }

                            @Override
                            public void onFailure(Call<Ack> call, Throwable t) {
                                Log.d("gginin2", t.getMessage());
                            }
                        });

                        intent = new Intent(verification_register.this, loginActivity.class);
                        startActivity(intent);
                    } else {

                        // 跳出警告視窗
                        // 不跳轉頁面
                        System.out.println("驗證碼不相同訊號 : 0");
                        openDialog();
                    }
                    break;
            }
            System.out.println("email : " + mail + "\n");
            System.out.println("password : " + password + "\n");
            System.out.println("weight : " + weight + "\n");
            System.out.println("phone : " + phone + "\n");
            System.out.println("nickname : " + nickname + "\n");
            System.out.println("sex : " + sex + "\n");
        }
    }

    // 跳出警告視窗
    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(verification_register.this);
        builder.setTitle("驗證碼輸入錯誤");
        builder.setMessage("請重新輸入驗證碼!!");
        builder.create().show();
    }

    // 驗證碼比較
    private boolean compare() {
        String user_input = verification_register.getText().toString().trim();
        return message.equals(user_input);
    }
}
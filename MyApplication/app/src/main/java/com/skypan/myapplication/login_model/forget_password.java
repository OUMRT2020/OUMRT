package com.skypan.myapplication.login_model;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skypan.myapplication.R;
import com.skypan.myapplication.Retrofit.RetrofitManagerAPI;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class forget_password extends AppCompatActivity {
    private Button cancel_forget_password;  // 宣告cancel_forget_password
    private Button send_forget_password;    // 宣告send_forget_password

    // message存驗證碼
    public static String message;

    //mail要給後端的, set_new_password會用到
    public static EditText email_forget;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        // find id
        cancel_forget_password = findViewById(R.id.cancel_forget_password);
        send_forget_password = findViewById(R.id.send_forget_password);
        email_forget = findViewById(R.id.email_forget);

        // 監聽器
        setListeners();
    }

    // 監聽器
    private void setListeners() {
        OnClick onClick = new OnClick();
        cancel_forget_password.setOnClickListener(onClick);
        send_forget_password.setOnClickListener(onClick);
    }

    // when click ...
    private class OnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cancel_forget_password:

                    // 跳轉到login介面
                    Intent intent0 = new Intent(forget_password.this, loginActivity.class);
                    startActivity(intent0);
                    break;
                case R.id.send_forget_password:
                    System.out.println("成功按下send_btn信號 : 1");
                    if (email_forget.getText().toString().matches("")) {
                        Toast.makeText(forget_password.this, "欄位不得為空", Toast.LENGTH_SHORT).show();
                    } else {
                        //確認帳號是否已存在
                        Gson gson = new GsonBuilder()
                                .setLenient()
                                .create();
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("https://nmsl666.herokuapp.com/")
                                .addConverterFactory(GsonConverterFactory.create(gson))
                                .build();
                        RetrofitManagerAPI check_email_in_registerAPI = retrofit.create(RetrofitManagerAPI.class);
                        Call<String> call = check_email_in_registerAPI.accountExist(email_forget.getText().toString());
                        call.enqueue(new Callback<String>() {

                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                System.out.println("response.body : " + response.body());
                                if (!response.isSuccessful()) {
                                    Log.d("error0", response.message());
                                }
                                if (response.body().equals("true")) {
                                    System.out.println("帳號存在信號 : 1");
                                    // 跳轉到輸入驗證碼畫面
                                    // 寄出認證碼
                                    sendMail();
                                    Intent intent1 = new Intent(forget_password.this, verification_forgetActivity.class);
                                    startActivity(intent1);
                                } else {
                                    System.out.println("帳號不存在信號 : 0");
                                    Toast.makeText(forget_password.this, "account no exist", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(forget_password.this, "server error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    break;
            }

        }
    }

    private void sendMail() {
        // declare userMail && mailSubject
        String mail = email_forget.getText().toString().trim();
        String subject = "OUMRT忘記密碼通知";

        // produce verify number
        String str = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();

        // 設定字串長度為 4
        int strLength = 4;
        for (int j = 0; j < strLength; j++) {
            int index = random.nextInt(str.length());
            char c = str.charAt(index);
            stringBuffer.append(c);
        }

        // 將StringBuffer轉換為String型別的字串
        // message是單純的認證碼
        // send_message是包含認證碼的信中內容訊息
        message = stringBuffer.toString();
        String send_message;
        send_message = "您好, 為了安全己見, 請於APP中輸入您的驗證碼, 您的驗證碼為 : " + message;

        // send mail
        JavaMailAPI javaMailAPI=new JavaMailAPI(this,mail,subject,send_message);
        javaMailAPI.execute();
    }
}
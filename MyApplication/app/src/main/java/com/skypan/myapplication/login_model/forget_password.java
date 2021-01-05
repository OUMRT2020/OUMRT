package com.skypan.myapplication.login_model;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.skypan.myapplication.R;
import com.skypan.myapplication.Retrofit.Ack;
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
    private EditText email_forget;

    // message存驗證碼
    public static String message;

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
                    Intent intent = null;
                    intent = new Intent(forget_password.this, loginActivity.class);
                    startActivity(intent);
                    break;
                case R.id.send_forget_password:

                    // 跳轉到輸入驗證碼畫面
                    // 寄出認證碼
                    Retrofit retrofit = new Retrofit.Builder()
                            .addConverterFactory(GsonConverterFactory.create())
                            .baseUrl("http://140.121.197.130:5602/")
                            .build();
                    RetrofitManagerAPI retrofitManagerAPI = retrofit.create(RetrofitManagerAPI.class);
                    Call<Ack> call = retrofitManagerAPI.checkEmail(email_forget.getText().toString());
                    call.enqueue(new Callback<Ack>() {
                        @Override
                        public void onResponse(Call<Ack> call, Response<Ack> response) {
                            if (!response.isSuccessful()) {

                            } else {
                                if (response.body().isSuccess()) {
                                    sendMail();
                                    Intent intent = null;
                                    intent = new Intent(forget_password.this, verification_forgetActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(forget_password.this, "帳號不存在", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Ack> call, Throwable t) {

                        }
                    });
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
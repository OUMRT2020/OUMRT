package com.skypan.myapplication.login_model;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
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

public class registerActivity extends AppCompatActivity {
    private Button cancel_register;
    private Button verify_register;

    // message存驗證碼
    public static String message;

    //以下為要傳到後端的資料
    public static EditText email_register;
    public static EditText password_register;
    public static EditText weight_register;
    public static EditText phone_register;
    public static EditText nickname_register;
    public static String sex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // find id
        cancel_register = findViewById(R.id.cancel_register);
        verify_register = findViewById(R.id.verify_register);

        //以下為要傳到後端的資料
        email_register = findViewById(R.id.email_register);
        password_register = findViewById(R.id.password_register);
        weight_register = findViewById(R.id.weight_register);
        phone_register = findViewById(R.id.phone_register);
        nickname_register = findViewById(R.id.nickname_register);

        // 監聽器
        setListeners();
    }

    // 監聽器
    private void setListeners() {
        OnClick onClick = new OnClick();
        cancel_register.setOnClickListener(onClick);
        verify_register.setOnClickListener(onClick);
    }

    // when click ...
    private class OnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.cancel_register:

                    // 跳轉到login介面
                    Intent intent = null;
                    intent = new Intent(registerActivity.this, loginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    break;
                case R.id.verify_register:

                    if (email_register.getText().toString().matches("") || password_register.getText().toString().matches("") || weight_register.getText().toString().matches("") || phone_register.getText().toString().matches("") || nickname_register.getText().toString().matches("")) {
                        Toast.makeText(registerActivity.this, "欄位不得為空", Toast.LENGTH_SHORT).show();
                    } else {
                        // 跳轉到認證信箱(註冊的)頁面
                        // 寄出認證碼
                        Retrofit retrofit = new Retrofit.Builder()
                                .addConverterFactory(GsonConverterFactory.create())
                                .baseUrl("http://140.121.197.130:5602/")
                                .build();
                        RetrofitManagerAPI retrofitManagerAPI = retrofit.create(RetrofitManagerAPI.class);
                        Call<Ack> call = retrofitManagerAPI.checkEmail(email_register.getText().toString());
                        call.enqueue(new Callback<Ack>() {
                            @Override
                            public void onResponse(Call<Ack> call, Response<Ack> response) {
                                if (!response.isSuccessful()) {

                                } else {
                                    if (!response.body().isSuccess()) {
                                        sendMail();
                                        check_gender();
                                        Intent intent = null;
                                        intent = new Intent(registerActivity.this, verification_register.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(registerActivity.this, "帳號已存在", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Ack> call, Throwable t) {

                            }
                        });

                    }
                    break;
            }
        }
    }

    private void check_gender() {
        RadioGroup gender = (RadioGroup) findViewById(R.id.gender);
        switch (gender.getCheckedRadioButtonId()) {
            case R.id.gender_man:
                sex = "男";
                break;
            case R.id.gender_woman:
                sex = "女";
                break;
        }
    }

    private void sendMail() {

        // declare userMail && mailSubject
        String mail = email_register.getText().toString().trim();
        String subject = "OUMRT註冊認證碼";

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
        send_message = "歡迎註冊OUMRT, 這是您的驗證碼 : " + message;

        // send mail
        JavaMailAPI javaMailAPI = new JavaMailAPI(this, mail, subject, send_message);
        javaMailAPI.execute();
    }
}
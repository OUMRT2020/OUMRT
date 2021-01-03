package com.skypan.myapplication.login_model;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
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
                    System.out.println("跳轉回login頁面信號 : 1");
                    // 跳轉到login介面
                    Intent intent0 = new Intent(registerActivity.this, loginActivity.class);
                    intent0.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent0);
                    break;
                case R.id.verify_register:
                    System.out.println("成功按下verify_btn信號 : 1");
                    if (email_register.getText().toString().matches("") || password_register.getText().toString().matches("") || weight_register.getText().toString().matches("") || phone_register.getText().toString().matches("") || nickname_register.getText().toString().matches("")) {
                        Toast.makeText(registerActivity.this, "欄位不得為空", Toast.LENGTH_SHORT).show();
                    } else {
                        //確認帳號是否已存在
                        Gson gson = new GsonBuilder()
                                .setLenient()
                                .create();
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://140.121.197.130:5602/")
                                .addConverterFactory(GsonConverterFactory.create(gson))
                                .build();
                        RetrofitManagerAPI check_email_in_registerAPI = retrofit.create(RetrofitManagerAPI.class);
                        Call<String> call = check_email_in_registerAPI.accountExist(email_register.getText().toString());
                        call.enqueue(new Callback<String>() {

                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                System.out.println("response.body : " + response.body());
                                if (!response.isSuccessful()) {
                                    Log.d("error0", response.message());
                                }
                                if (response.body().equals("true")) {
                                    System.out.println("帳號存在信號 : 1");
                                    Toast.makeText(registerActivity.this, "account exist", Toast.LENGTH_SHORT).show();
                                } else {
                                    System.out.println("帳號不存在信號 : 0");
                                    // 跳轉到認證信箱(註冊的)頁面
                                    // 寄出認證碼
                                    sendMail();
                                    check_gender();
                                    Intent intent1 = new Intent(registerActivity.this, verification_register.class);
                                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent1);
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(registerActivity.this, "server error", Toast.LENGTH_SHORT).show();
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
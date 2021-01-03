package com.skypan.myapplication.login_model;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.skypan.myapplication.R;
import java.util.Random;

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
            Intent intent = null;
            switch (v.getId()) {
                case R.id.cancel_forget_password:

                    // 跳轉到login介面
                    Intent intent0 = new Intent(forget_password.this, loginActivity.class);
                    intent0.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
                                .baseUrl("http://140.121.197.130:5602/")
                                .addConverterFactory(GsonConverterFactory.create(gson))
                                .build();
                        RetrofitManagerAPI check_email_in_registerAPI = retrofit.create(RetrofitManagerAPI.class);
                        Call<String> call = check_email_in_registerAPI.accountExist(email_forget.getText().toString());
                        call.enqueue(new Callback<String>() {
=======
                    intent = new Intent(forget_password.this, loginActivity.class);
                    break;
                case R.id.send_forget_password:
            }
            startActivity(intent);
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
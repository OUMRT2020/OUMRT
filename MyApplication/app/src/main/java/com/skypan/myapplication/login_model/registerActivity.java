package com.skypan.myapplication.login_model;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.skypan.myapplication.R;
import java.util.Random;

public class registerActivity extends AppCompatActivity {
    private Button cancel_register;
    private Button verify_register;
    public EditText email_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // find id
        cancel_register = findViewById(R.id.cancel_register);
        verify_register = findViewById(R.id.verify_register);
        email_register = findViewById(R.id.email_register);

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
            Intent intent = null;
            switch (v.getId()) {
                case R.id.cancel_register:
                    // 跳轉到login介面
                    intent = new Intent(registerActivity.this, loginActivity.class);
                    break;
                case R.id.verify_register:
                    // 跳轉到認證信箱(註冊的)頁面
                    sendMail();
                    intent = new Intent(registerActivity.this, verification_register.class);
                    break;
            }
            startActivity(intent);
        }
    }

    private void sendMail() {
        // System.out.println("XDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");

        // declare userMail && mailSubject
        String mail = email_register.getText().toString().trim();
        String subject = "OUMRT_verify";

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

        //將StringBuffer轉換為String型別的字串
        String message = stringBuffer.toString();

        // send mail
        JavaMailAPI javaMailAPI=new JavaMailAPI(this,mail,subject,message);
        javaMailAPI.execute();
    }
}
package com.skypan.myapplication.login_model;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.skypan.myapplication.R;

public class loginActivity extends AppCompatActivity {
    private Button login_button;            // 宣告login_button
    private Button forget_password_button;  // 宣告forget_password_button
    private Button register_button;         // 宣告register_button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // find id
        login_button = findViewById(R.id.login_button);
        forget_password_button = findViewById(R.id.forget_password_button);
        register_button = findViewById(R.id.register_button);

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
            Intent intent = null;
            switch (v.getId()) {
                case R.id.login_button:
                    // 跳轉到登入後的頁面
                    intent = new Intent(loginActivity.this, select_identityActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    break;
                case R.id.forget_password_button:
                    // 跳轉到忘記密碼的頁面
                    intent = new Intent(loginActivity.this, forget_password.class);
                    break;
                case R.id.register_button:
                    // 跳轉到register介面
                    intent = new Intent(loginActivity.this, registerActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }
}
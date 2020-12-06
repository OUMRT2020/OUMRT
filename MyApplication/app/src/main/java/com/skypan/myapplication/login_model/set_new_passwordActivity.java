package com.skypan.myapplication.login_model;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.skypan.myapplication.R;

public class set_new_passwordActivity extends AppCompatActivity {
    private Button cancel_new_password;
    private Button verify_new_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);

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
            Intent intent = null;
            switch (v.getId()) {
                case R.id.cancel_new_password:
                    // 跳轉到輸入驗證信箱(忘記密碼的)畫面
                    intent = new Intent(set_new_passwordActivity.this, verification_forgetActivity.class);
                    break;
                case R.id.verify_new_password:
                    // 跳轉到login介面
                    intent = new Intent(set_new_passwordActivity.this, loginActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }
}
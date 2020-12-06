package com.skypan.myapplication.login_model;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.skypan.myapplication.R;

public class verification_register extends AppCompatActivity {
    private Button cancel_verification_register;
    private Button verify_verification_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_register);

        // find id
        cancel_verification_register = findViewById(R.id.cancel_verification_register);
        verify_verification_register = findViewById(R.id.verify_verification_register);

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
                    break;
                case R.id.verify_verification_register:
                    // 跳轉到login頁面
                    intent = new Intent(verification_register.this, loginActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }
}
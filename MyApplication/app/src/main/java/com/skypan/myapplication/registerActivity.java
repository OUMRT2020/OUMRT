package com.skypan.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class registerActivity extends AppCompatActivity {
    private TextView cancel_register;
    private TextView verify_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // find id
        cancel_register=findViewById(R.id.cancel_register);
        verify_register=findViewById(R.id.verify_register);

        // 監聽器
        setListeners();
    }

    // 監聽器
    private void setListeners(){
        OnClick onClick=new OnClick();
        cancel_register.setOnClickListener(onClick);
        verify_register.setOnClickListener(onClick);
    }

    // when click ...
    private class OnClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent=null;
            switch (v.getId()){
                case R.id.cancel_register:
                    // 跳轉到login介面
                    intent=new Intent(registerActivity.this, loginActivity.class);
                    break;
                case R.id.verify_register:
                    // 跳轉到認證信箱(註冊的)頁面
                    intent=new Intent(registerActivity.this, verification_register.class);
                    break;
            }
            startActivity(intent);
        }
    }
}
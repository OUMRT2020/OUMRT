package com.skypan.myapplication.login_model;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.skypan.myapplication.R;

public class verification_forgetActivity extends AppCompatActivity {
    private TextView cancel_verification_forget;
    private TextView verify_verification_forget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_forget);

        // find id
        cancel_verification_forget=findViewById(R.id.cancel_verification_forget);
        verify_verification_forget=findViewById(R.id.verify_verification_forget);

        // 監聽器
        setListeners();
    }

    // 監聽器
    private void setListeners(){
        OnClick onClick=new OnClick();
        cancel_verification_forget.setOnClickListener(onClick);
        verify_verification_forget.setOnClickListener(onClick);
    }

    // when click ...
    private class OnClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent=null;
            switch (v.getId()){
                case R.id.cancel_verification_forget:
                    // 跳轉到輸入驗證信箱(忘記密碼的)畫面
                    intent=new Intent(verification_forgetActivity.this, forget_password.class);
                    break;
                case R.id.verify_verification_forget:
                    // 跳轉到設定新密碼頁面
                    intent=new Intent(verification_forgetActivity.this, set_new_passwordActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }
}
package com.skypan.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class forget_password extends AppCompatActivity {
    private TextView cancel_forget_password;  // 宣告cancel_forget_password
    private TextView send_forget_password;    // 宣告send_forget_password

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        // find id
        cancel_forget_password=findViewById(R.id.cancel_forget_password);
        send_forget_password=findViewById(R.id.send_forget_password);

        // 監聽器
        setListeners();
    }

    // 監聽器
    private void setListeners(){
        OnClick onClick=new OnClick();
        cancel_forget_password.setOnClickListener(onClick);
        send_forget_password.setOnClickListener(onClick);
    }

    // when click ...
    private class OnClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent=null;
            switch(v.getId()){
                case R.id.cancel_forget_password:
                    // 跳轉到login介面
                    intent=new Intent(forget_password.this, loginActivity.class);
                    break;
                case R.id.send_forget_password:
                    // 跳轉到輸入驗證碼畫面
                    intent=new Intent(forget_password.this, verification_forgetActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }
}
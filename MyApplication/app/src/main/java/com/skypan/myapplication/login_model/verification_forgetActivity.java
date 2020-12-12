package com.skypan.myapplication.login_model;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.skypan.myapplication.R;

public class verification_forgetActivity extends AppCompatActivity {
    private Button cancel_verification_forget;
    private Button verify_verification_forget;
    private EditText verification_forget;              // user輸入的認證碼
    private String message = forget_password.message;  // 從forget_password製造出來的認證碼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_forget);

        // find id
        cancel_verification_forget = findViewById(R.id.cancel_verification_forget);
        verify_verification_forget = findViewById(R.id.verify_verification_forget);
        verification_forget = findViewById(R.id.verification_forget);

        // 監聽器
        setListeners();
    }

    // 監聽器
    private void setListeners() {
        OnClick onClick = new OnClick();
        cancel_verification_forget.setOnClickListener(onClick);
        verify_verification_forget.setOnClickListener(onClick);
    }

    // when click ...
    private class OnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.cancel_verification_forget:

                    // 跳轉到輸入驗證信箱(忘記密碼的)畫面
                    intent = new Intent(verification_forgetActivity.this, forget_password.class);
                    startActivity(intent);
                    break;
                case R.id.verify_verification_forget:

                    // 比較驗證碼是否相符
                    boolean isEqual;
                    isEqual = compare();
                    if (isEqual) {

                        // 跳轉到輸入新密碼的頁面
                        System.out.println("驗證碼相同訊號 : 1");
                        intent = new Intent(verification_forgetActivity.this, set_new_passwordActivity.class);
                        startActivity(intent);
                    }
                    else {

                        // 跳出警告視窗
                        // 不跳轉頁面
                        System.out.println("驗證碼不相同訊號 : 0");
                        openDialog();
                    }
                    break;
            }

        }
    }

    // 跳出警告視窗
    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(verification_forgetActivity.this);
        builder.setTitle("驗證碼輸入錯誤");
        builder.setMessage("請重新輸入驗證碼!!");
        builder.create().show();
    }

    // 驗證碼比較
    private boolean compare() {
        String user_input = verification_forget.getText().toString().trim();
        return message.equals(user_input);
    }
}
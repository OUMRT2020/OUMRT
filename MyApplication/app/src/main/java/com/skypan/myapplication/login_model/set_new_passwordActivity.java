package com.skypan.myapplication.login_model;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.skypan.myapplication.R;

public class set_new_passwordActivity extends AppCompatActivity {
    private Button cancel_new_password;
    private Button verify_new_password;
    private EditText new_password;
    private EditText new_password_again;

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
                    startActivity(intent);
                    break;
                case R.id.verify_new_password:

                    // 比較密碼
                    new_password = findViewById(R.id.new_password);
                    new_password_again = findViewById(R.id.new_password_again);
                    String password1 = new_password.getText().toString();
                    String password2 = new_password_again.getText().toString();
                    if(password1.equals(password2)){

                        // 跳轉到login介面
                        System.out.println("密碼相同訊號 : 1");
                        intent = new Intent(set_new_passwordActivity.this, loginActivity.class);
                        startActivity(intent);
                    }
                    else {

                        // 跳出警告視窗
                        // 不跳轉頁面
                        System.out.println("密碼不相同訊號 : 0");
                        openDialog();
                    }
                    break;
            }
        }
    }

    // 跳出警告視窗
    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(set_new_passwordActivity.this);
        builder.setTitle("密碼輸入錯誤");
        builder.setMessage("請重新輸入密碼!!");
        builder.create().show();
    }
}
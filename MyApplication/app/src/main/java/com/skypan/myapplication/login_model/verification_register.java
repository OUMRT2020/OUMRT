package com.skypan.myapplication.login_model;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.skypan.myapplication.R;

public class verification_register extends AppCompatActivity {
    private Button cancel_verification_register;
    private Button verify_verification_register;
    private EditText verification_register;             // user輸入的認證碼
    private String message = registerActivity.message;  // 從registerActivity製造出來的認證碼

    // 以下為要傳到後端的資料
    private String mail = registerActivity.email_register.getText().toString().trim();
    private String password = registerActivity.password_register.getText().toString().trim();
    private String weight = registerActivity.weight_register.getText().toString().trim();
    private String phone = registerActivity.phone_register.getText().toString().trim();
    private String nickname = registerActivity.nickname_register.getText().toString().trim();
    private String age = registerActivity.age_register.getText().toString().trim();
    private String sex = registerActivity.sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_register);

        // find id
        cancel_verification_register = findViewById(R.id.cancel_verification_register);
        verify_verification_register = findViewById(R.id.verify_verification_register);
        verification_register = findViewById(R.id.verification_register);

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
                    startActivity(intent);
                    break;
                case R.id.verify_verification_register:

                    // 比較驗證碼是否相符
                    boolean isEqual;
                    isEqual = compare();
                    if (isEqual) {

                        // 跳轉到login頁面
                        System.out.println("驗證碼相同訊號 : 1");
                        intent = new Intent(verification_register.this, loginActivity.class);
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
            System.out.println("email : " + mail + "\n");
            System.out.println("password : " + password + "\n");
            System.out.println("weight : " + weight + "\n");
            System.out.println("phone : " + phone + "\n");
            System.out.println("nickname : " + nickname + "\n");
            System.out.println("age : " + age + "\n");
            System.out.println("sex : " + sex + "\n");
        }
    }

    // 跳出警告視窗
    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(verification_register.this);
        builder.setTitle("驗證碼輸入錯誤");
        builder.setMessage("請重新輸入驗證碼!!");
        builder.create().show();
    }

    // 驗證碼比較
    private boolean compare() {
        String user_input = verification_register.getText().toString().trim();
        return message.equals(user_input);
    }
}
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
                    Intent intent0 = new Intent(set_new_passwordActivity.this, verification_forgetActivity.class);
                    intent0.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent0);
                    break;
                case R.id.verify_new_password:

                    // 比較密碼
                    new_password = findViewById(R.id.new_password);
                    new_password_again = findViewById(R.id.new_password_again);
                    String password1 = new_password.getText().toString();
                    String password2 = new_password_again.getText().toString();

                    // 不得為空
                    if (new_password.getText().toString().equals("") || new_password_again.getText().toString().equals("")) {
                        Toast.makeText(set_new_passwordActivity.this, "密碼不得為空", Toast.LENGTH_SHORT).show();
                    } else if(password1.equals(password2)){

                        //把新密碼傳到後端
                        Gson gson = new GsonBuilder()
                                .setLenient()
                                .create();
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://140.121.197.130:5602/")
                                .addConverterFactory(GsonConverterFactory.create(gson))
                                .build();
                        RetrofitManagerAPI newPassword = retrofit.create(RetrofitManagerAPI.class);
                        Call<String> call = newPassword.newPassword(email, new_password.getText().toString());
                        call.enqueue(new Callback<String>() {

                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                System.out.println("response.body : " + response.body());
                                // 跳轉到login介面
                                System.out.println("密碼相同訊號 : 1");
                                Intent intent1 = new Intent(set_new_passwordActivity.this, loginActivity.class);
                                startActivity(intent1);
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(set_new_passwordActivity.this, "server error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {

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
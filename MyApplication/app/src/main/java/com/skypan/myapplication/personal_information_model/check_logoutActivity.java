package com.skypan.myapplication.personal_information_model;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.skypan.myapplication.R;
import com.skypan.myapplication.login_model.loginActivity;

public class check_logoutActivity extends AppCompatActivity {
    private Button cancel_check_logout;
    private Button verify_check_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_logout);

        // fine id
        cancel_check_logout=findViewById(R.id.cancel_check_logout);
        verify_check_logout=findViewById(R.id.verify_check_logout);

        // 監聽器
        setListeners();
    }

    // 監聽器
    private void setListeners(){
        check_logoutActivity.OnClick onClick=new check_logoutActivity.OnClick();
        cancel_check_logout.setOnClickListener(onClick);
        verify_check_logout.setOnClickListener(onClick);
    }

    // when click ...
    private class OnClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent=null;
            switch(v.getId()){
                //case R.id.cancel_check_logout:
                    // 跳轉回到
                    //intent=new Intent(check_logoutActivity.this, );
                    //break;
                case R.id.verify_check_logout:
                    // 跳轉到login介面
                    intent=new Intent(check_logoutActivity.this, loginActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }
}
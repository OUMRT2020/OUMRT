package com.skypan.myapplication.personal_information_model;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.skypan.myapplication.R;
import com.skypan.myapplication.Retrofit.User;
import com.skypan.myapplication.passenger_model.PassengerMainActivity;


public class personal_informationActivity extends AppCompatActivity {
    private Button edit_information_button;
    private Button return_button;
    private TextView name;
    private TextView phone;
    private TextView weight;
    private TextView sex;
    private TextView point;
    private User user = PassengerMainActivity.user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);

        // find id
        edit_information_button = findViewById(R.id.edit_information_button);
        return_button = findViewById(R.id.return_button);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        weight = findViewById(R.id.weight);
        sex = findViewById(R.id.sex);
        point = findViewById(R.id.point);

        // 變動textview的text
        System.out.println("user name = " + user.getName());

        // 監聽器
        setListeners();
    }

    //監聽器
    private void setListeners() {
        personal_informationActivity.OnClick onClick = new personal_informationActivity.OnClick();
        edit_information_button.setOnClickListener(onClick);
        return_button.setOnClickListener(onClick);
    }

    // when click ...
    private class OnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.edit_information_button:
                    // 跳轉到編輯個人資料頁面
                    Intent intent0 = new Intent(personal_informationActivity.this, edit_personal_informationActivity.class);
                    startActivity(intent0);
                    break;
                case R.id.return_button:
                    // 跳轉到passengerHomeFragment畫面
                    Intent intent1=new Intent(personal_informationActivity.this, PassengerMainActivity.class);
                    startActivity(intent1);
                    break;
            }
        }
    }
}
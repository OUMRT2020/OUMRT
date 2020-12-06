package com.skypan.myapplication.personal_information_model;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.skypan.myapplication.R;

public class personal_informationActivity extends AppCompatActivity {
    private Button edit_information_button;
    private Button return_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);

        // find id
        edit_information_button = findViewById(R.id.edit_information_button);
        return_button = findViewById(R.id.return_button);

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
            Intent intent = null;
            switch (v.getId()) {
                case R.id.edit_information_button:
                    // 跳轉到編輯個人資料頁面
                    intent = new Intent(personal_informationActivity.this, edit_personal_informationActivity.class);
                    break;
                // case R.id.return_button:
                // 跳轉到profile畫面
                // intent=new Intent(personal_informationActivity.this, )
                // break;
            }
            startActivity(intent);
        }
    }
}
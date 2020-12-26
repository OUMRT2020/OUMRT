package com.skypan.myapplication.login_model;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.skypan.myapplication.R;
import com.skypan.myapplication.driver_model.DriverMainActivity;
import com.skypan.myapplication.passenger_model.PassengerMainActivity;

public class select_identityActivity extends AppCompatActivity {
    private String user_id;
    private boolean isLogin;
    private Button driver_button;
    private Button passenger_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_identity);
//        Intent intent = getIntent();
//        user_id = intent.getStringExtra("user_id");
//        Log.d("user_id", user_id);
        if(!getSharedPreferences("isOUMRTLogin", MODE_PRIVATE).getBoolean("isLogin",false)){//如果沒有登入就去登入
            Intent intent = new Intent(select_identityActivity.this, loginActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            user_id = getSharedPreferences("isOUMRTLogin", MODE_PRIVATE).getString("user_id","");
        }
        driver_button = findViewById(R.id.driver_button);
        driver_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(select_identityActivity.this, DriverMainActivity.class);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
                finish();
            }
        });

        passenger_button = findViewById(R.id.passenger_button);
        passenger_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(select_identityActivity.this, PassengerMainActivity.class);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
                finish();
            }
        });
    }
}
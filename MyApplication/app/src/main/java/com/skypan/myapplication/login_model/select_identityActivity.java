package com.skypan.myapplication.login_model;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.skypan.myapplication.R;
import com.skypan.myapplication.driver_model.DriverMainActivity;
import com.skypan.myapplication.passenger_model.PassengerMainActivity;

public class select_identityActivity extends AppCompatActivity {
    private Button driver_button;
    private Button passenger_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_identity);

        driver_button = findViewById(R.id.driver_button);
        driver_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(select_identityActivity.this, DriverMainActivity.class);
                startActivity(intent);
            }
        });

        passenger_button = findViewById(R.id.passenger_button);
        passenger_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(select_identityActivity.this, PassengerMainActivity.class);
                startActivity(intent);
            }
        });
    }
}
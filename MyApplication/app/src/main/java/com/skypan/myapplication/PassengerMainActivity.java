package com.skypan.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PassengerMainActivity extends AppCompatActivity {

    private String userID;
    private FloatingActionButton btn_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_main);
        btn_search = findViewById(R.id.search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PassengerMainActivity.this,SearchEventsActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });
    }
}
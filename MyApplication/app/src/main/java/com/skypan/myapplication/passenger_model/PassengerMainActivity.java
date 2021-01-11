package com.skypan.myapplication.passenger_model;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.skypan.myapplication.R;
import com.skypan.myapplication.driver_model.DriverMainActivity;
import com.skypan.myapplication.inform.My_inform;
import com.skypan.myapplication.login_model.loginActivity;

public class PassengerMainActivity extends AppCompatActivity {

    public String user_id;
    private Button btn_logout;
    private FloatingActionButton btn_search;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private AppBarConfiguration appBarConfiguration;
    private NavController navController;
    private ImageView img_notify;

    //    private Button button;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_main);
        Intent intent = getIntent();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        user_id = intent.getStringExtra("user_id");
        Log.d("user_id", user_id);
        img_notify = findViewById(R.id.img_notify);
        //find views
        btn_search = findViewById(R.id.search);
        btn_logout = findViewById(R.id.btn_logout);
        drawerLayout = findViewById(R.id.passenger_drawer_layout);
        navigationView = findViewById(R.id.navigationView);
//        button = findViewById(R.id.foo);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new FCMNotify("123");
//            }
//        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PassengerMainActivity.this, SearchEventsActivity.class);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("isOUMRTLogin", MODE_PRIVATE);
                preferences.edit()
                        .clear()
                        .apply();
                Intent goBackLogin = new Intent(PassengerMainActivity.this, loginActivity.class);
                startActivity(goBackLogin);
                finish();
            }
        });
        img_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_to_inform = new Intent(PassengerMainActivity.this, My_inform.class);
                startActivity(go_to_inform);
            }
        });
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.passengerHomeFragment)//頂級目的地(漢寶寶)，非頂級就會變箭頭
                .setDrawerLayout(drawerLayout)
                .build();

        //navController宣告在上面
        navController = Navigation.findNavController(PassengerMainActivity.this, R.id.passenger_nav_host_fragment);//hostFragment必須在當前activity內
        NavigationUI.setupActionBarWithNavController(PassengerMainActivity.this, navController, appBarConfiguration);//設定漢寶寶
        NavigationUI.setupWithNavController(navigationView, navController);//不知道是三小
    }

    @Override
    public boolean onSupportNavigateUp() {//讓漢寶寶有作用
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {//點返回鍵可以讓漢寶寶收回去
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();

            } else {
                doubleBackToExitPressedOnce = true;
                Snackbar.make(findViewById(android.R.id.content), "再點擊一次返回鍵以退出", Snackbar.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);
            }
        }
    }
}
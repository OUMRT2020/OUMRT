package com.skypan.myapplication.passenger_model;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.skypan.myapplication.R;
import com.skypan.myapplication.driver_model.DriverMainActivity;

public class PassengerMainActivity extends AppCompatActivity {

    private String userID;
    private FloatingActionButton btn_search;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private AppBarConfiguration appBarConfiguration;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_main);

        //find views
        btn_search = findViewById(R.id.search);
        drawerLayout = findViewById(R.id.passenger_drawer_layout);
        navigationView = findViewById(R.id.navigationView);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PassengerMainActivity.this, SearchEventsActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
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

//        可以跳到其他activity但其他item功能被覆蓋了
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                int id = item.getItemId();
//                if (id == R.id.passengerSwitchToDriver) {
//                    Intent newIntent = new Intent(PassengerMainActivity.this, DriverMainActivity.class);
//                    startActivity(newIntent);
//                }
//                return false;
//            }
//        });
    }

    @Override
    public boolean onSupportNavigateUp() {//讓漢寶寶有作用
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {//點返回鍵可以讓漢寶寶收回去
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
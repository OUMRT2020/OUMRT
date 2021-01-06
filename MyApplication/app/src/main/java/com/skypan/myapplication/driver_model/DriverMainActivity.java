package com.skypan.myapplication.driver_model;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.skypan.myapplication.R;
import com.skypan.myapplication.Retrofit.Ack;
import com.skypan.myapplication.Retrofit.Event;
import com.skypan.myapplication.Retrofit.RetrofitManagerAPI;
import com.skypan.myapplication.login_model.loginActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DriverMainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private EditText et_startTime;
    private EditText et_endTime;
    private Date startTime = new Date();
    private Date endTime = new Date();
    private TimePickerView pvTime;
    public String user_id;
    private String event_name;
    private boolean[] day = new boolean[7];
    private CheckBox cb1, cb2, cb3, cb4, cb5, cb6, cb7;
    private int money, weight, gender;
    private boolean ishamlet = true;
    private Button btn_logout;
    private DrawerLayout driver_drawer;


    private ArrayList<Boolean> repete = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main);
        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        Toolbar toolbar = findViewById(R.id.toolbar);
        btn_logout = findViewById(R.id.btn_logout);
        driver_drawer = findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DriverMainActivity.this.setButtonCustomDialog();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_House, R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_test)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("isOUMRTLogin", MODE_PRIVATE);
                preferences.edit()
                        .clear()
                        .apply();
                Intent goBackLogin = new Intent(DriverMainActivity.this, loginActivity.class);
                startActivity(goBackLogin);
                finish();
            }
        });
    }
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (this.driver_drawer.isDrawerOpen(GravityCompat.START)) {//點返回鍵可以讓漢寶寶收回去
            this.driver_drawer.closeDrawer(GravityCompat.START);
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

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @SuppressLint("WrongViewCast")
    private void setButtonCustomDialog() {

        String[] acceptable_time_interval = new String[2];
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DriverMainActivity.this);
        View v = getLayoutInflater().inflate(R.layout.set_custom_dialog_layout_with_button, null);


        alertDialog.setView(v);
        Button btOK = v.findViewById(R.id.button_ok);
        Button btC = v.findViewById(R.id.buttonCancel);


        final EditText name = v.findViewById(R.id.name);
        final EditText editText_startTime = v.findViewById(R.id.et_startTime);
        final EditText editText_endTime = v.findViewById(R.id.et_endTime);
        final AlertDialog dialog = alertDialog.create();
        dialog.show();

        final RadioButton mRg1 = v.findViewById(R.id.rb_gender_1);
        final RadioButton mRg2 = v.findViewById(R.id.rb_gender_2);
        final RadioButton mRgnull = v.findViewById(R.id.rb_gender_3);
        final RadioButton mRg3 = v.findViewById(R.id.rb_helmet_1);
        final RadioButton mRg4 = v.findViewById(R.id.rb_helmet_2);
        final EditText editText_Money = v.findViewById(R.id.et_money);

        final EditText editText_Weught = v.findViewById(R.id.et_weight);
        //radiobutton
        {
            mRg1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    gender = 0;
                }
            });
            mRg2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    gender = 1;
                }
            });
            mRgnull.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    gender = 2;
                }
            });
            mRg3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    ishamlet = true;
                }
            });
            mRg4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    ishamlet = false;
                }
            });
        }

        {
            cb1 = v.findViewById(R.id.cb_1);
            cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        day[0] = true;
                        System.out.println(day[0]);
                    } else day[0] = false;
                }
            });
            cb2 = v.findViewById(R.id.cb_2);
            ;
            cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        day[1] = true;
                        System.out.println(day[1]);
                    } else day[1] = false;
                }
            });
            cb3 = v.findViewById(R.id.cb_3);
            ;
            cb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        day[2] = true;
                        System.out.println(day[2]);
                    } else day[2] = false;
                }
            });
            cb4 = v.findViewById(R.id.cb_4);
            ;
            cb4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        day[3] = true;
                        System.out.println(day[3]);
                    } else day[3] = false;
                }
            });
            cb5 = v.findViewById(R.id.cb_5);
            ;
            cb5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        day[4] = true;
                        System.out.println(day[4]);
                    } else day[4] = false;
                }
            });
            cb6 = v.findViewById(R.id.cb_6);
            ;
            cb6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        day[5] = true;
                        System.out.println(day[5]);
                    } else day[5] = false;
                }
            });
            cb7 = v.findViewById(R.id.cb_7);
            ;
            cb7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        day[6] = true;
                        System.out.println(day[6]);
                    } else day[6] = false;
                }
            });
        }

        //start
        final CheckBox st1 = v.findViewById(R.id.st_1);
        final CheckBox st2 = v.findViewById(R.id.st_2);
        final CheckBox st3 = v.findViewById(R.id.st_3);
        final CheckBox st4 = v.findViewById(R.id.st_4);
        final CheckBox st5 = v.findViewById(R.id.st_5);
        final CheckBox st6 = v.findViewById(R.id.st_6);
        final CheckBox st7 = v.findViewById(R.id.st_7);
        final CheckBox st8 = v.findViewById(R.id.st_8);
        final CheckBox st9 = v.findViewById(R.id.st_9);
        final CheckBox st10 = v.findViewById(R.id.st_10);
        st1.setOnCheckedChangeListener(st);
        st2.setOnCheckedChangeListener(st);
        st3.setOnCheckedChangeListener(st);
        st4.setOnCheckedChangeListener(st);
        st5.setOnCheckedChangeListener(st);
        st6.setOnCheckedChangeListener(st);
        st7.setOnCheckedChangeListener(st);
        st8.setOnCheckedChangeListener(st);
        st9.setOnCheckedChangeListener(st);
        st10.setOnCheckedChangeListener(st);

        //end
        final CheckBox end1 = v.findViewById(R.id.end_1);
        final CheckBox end2 = v.findViewById(R.id.end_2);
        final CheckBox end3 = v.findViewById(R.id.end_3);
        final CheckBox end4 = v.findViewById(R.id.end_4);
        final CheckBox end5 = v.findViewById(R.id.end_5);
        final CheckBox end6 = v.findViewById(R.id.end_6);
        final CheckBox end7 = v.findViewById(R.id.end_7);
        final CheckBox end8 = v.findViewById(R.id.end_8);
        final CheckBox end9 = v.findViewById(R.id.end_9);
        final CheckBox end10 = v.findViewById(R.id.end_10);
        end1.setOnCheckedChangeListener(end);
        end2.setOnCheckedChangeListener(end);
        end3.setOnCheckedChangeListener(end);
        end4.setOnCheckedChangeListener(end);
        end5.setOnCheckedChangeListener(end);
        end6.setOnCheckedChangeListener(end);
        end7.setOnCheckedChangeListener(end);
        end8.setOnCheckedChangeListener(end);
        end9.setOnCheckedChangeListener(end);
        end10.setOnCheckedChangeListener(end);


        //time
        et_startTime = v.findViewById(R.id.et_startTime);
        et_endTime = v.findViewById(R.id.et_endTime);
        et_startTime.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                if (pvTime != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(startTime);
                    pvTime.setDate(calendar);
                    pvTime.show(v1);

                }
            }
        }));
        et_endTime.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                if (pvTime != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(endTime);
                    pvTime.setDate(calendar);
                    pvTime.show(v1);
                }
            }
        }));

        initTimePicker();

        btOK.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                final Editable Temp;

                event_name = String.valueOf(name.getText());
                String mm = editText_Money.getText().toString();

                ArrayList<String> acc_time_interval = new ArrayList<>();
                ArrayList<String> acc_start_pts = new ArrayList<>();
                ArrayList<String> acc_end_pt = new ArrayList<>();
                acc_time_interval.add(et_startTime.getText().toString());
                acc_time_interval.add(et_endTime.getText().toString());
                if (st1.isChecked()) acc_start_pts.add(st1.getText().toString());
                if (st2.isChecked()) acc_start_pts.add(st2.getText().toString());
                if (st3.isChecked()) acc_start_pts.add(st3.getText().toString());
                if (st4.isChecked()) acc_start_pts.add(st4.getText().toString());
                if (st5.isChecked()) acc_start_pts.add(st5.getText().toString());
                if (st6.isChecked()) acc_start_pts.add(st6.getText().toString());
                if (st7.isChecked()) acc_start_pts.add(st7.getText().toString());
                if (st8.isChecked()) acc_start_pts.add(st8.getText().toString());
                if (st9.isChecked()) acc_start_pts.add(st9.getText().toString());
                if (st10.isChecked()) acc_start_pts.add(st10.getText().toString());

                if (end1.isChecked()) acc_end_pt.add(end1.getText().toString());
                if (end2.isChecked()) acc_end_pt.add(end2.getText().toString());
                if (end3.isChecked()) acc_end_pt.add(end3.getText().toString());
                if (end4.isChecked()) acc_end_pt.add(end4.getText().toString());
                if (end5.isChecked()) acc_end_pt.add(end5.getText().toString());
                if (end6.isChecked()) acc_end_pt.add(end6.getText().toString());
                if (end7.isChecked()) acc_end_pt.add(end7.getText().toString());
                if (end8.isChecked()) acc_end_pt.add(end8.getText().toString());
                if (end9.isChecked()) acc_end_pt.add(end9.getText().toString());
                if (end10.isChecked()) acc_end_pt.add(end10.getText().toString());


                String ww = editText_Weught.getText().toString();

                String gg = String.valueOf(gender);

                if (event_name.equals("") || mm.equals("") || ww.equals("") || acc_start_pts.size() < 1 || acc_end_pt.size() < 1
                        || acc_time_interval.size() < 2 || gg.equals("")) {
                    Toast.makeText(DriverMainActivity.this, "欄位不可為空", Toast.LENGTH_SHORT).show();
                } else {
                    // TODO:
                    AlertDialog.Builder twoDialog = new AlertDialog.Builder(DriverMainActivity.this);
                    twoDialog.setTitle("新增成功");
                    twoDialog.setPositiveButton("瞭解", (new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog1, int which) {
                            System.out.println("aaaabbb" + acc_time_interval.size());
                            money = Integer.parseInt(mm);
                            weight = Integer.parseInt(ww);
                            System.out.println(money + weight + gender);

                            for (boolean b : day) {
                                repete.add(b);
                            }

                            System.out.println(user_id);
                            Event e = new Event(event_name, "white", user_id
                                    , acc_time_interval
                                    , acc_start_pts
                                    , acc_end_pt
                                    , gender, weight, money, ishamlet, repete);
                            System.out.println("event_name" + event_name);
                            System.out.println("status" + "white");
                            System.out.println("user_id" + user_id);
                            System.out.println("acc_time_interval" + acc_time_interval);
                            System.out.println("acc_start_pts" + acc_start_pts);
                            System.out.println("acc_end_pt" + acc_end_pt);
                            System.out.println("gender" + gender);
                            System.out.println("weight" + weight);
                            System.out.println("money" + money);
                            System.out.println("ishamlet" + ishamlet);
                            System.out.println("repete" + repete);


                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl("http://140.121.197.130:5602/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            RetrofitManagerAPI retrofitManagerAPI = retrofit.create(RetrofitManagerAPI.class);
                            Call<Ack> call = retrofitManagerAPI.newEvent(e);
                            call.enqueue(new Callback<Ack>() {
                                @Override
                                public void onResponse(Call<Ack> call, Response<Ack> response) {
                                    if (!response.isSuccessful()) {
                                        Log.d("add", "new enent error");
                                    }
                                    Ack ack = response.body();
                                    Log.d("ACK", ack.isSuccess() ? "true" : "fasle");
                                    Log.d("ACK", ack.getReason());


                                }

                                @Override
                                public void onFailure(Call<Ack> call, Throwable t) {
                                    Log.d("add", "new enent server error");
                                }
                            });
                            dialog.dismiss();
//                        refresh();
                        }
                    }));
                    twoDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    twoDialog.show();
                }
            }
        }));
        btC.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                dialog.dismiss();
            }
        }));
    }

    private final CompoundButton.OnCheckedChangeListener st = new CompoundButton.OnCheckedChangeListener() { //实例化一个cb
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                System.out.println(buttonView.getText());
//                acc_start_pts.add(String.valueOf(buttonView.getText()));
            }
        }
    };
    private final CompoundButton.OnCheckedChangeListener end = new CompoundButton.OnCheckedChangeListener() { //实例化一个cb
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                System.out.println(buttonView.getText());
//                acc_end_pt.add(String.valueOf(buttonView.getText()));
            }
        }
    };

    private void refresh() {
        Intent intent = new Intent(DriverMainActivity.this, DriverMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void initTimePicker() {

        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                //如果是開始時間的EditText
                if (v.getId() == R.id.et_startTime) {
                    startTime = date;
                } else {
                    endTime = date;
                }
                EditText editText = (EditText) v;
                editText.setText(getTime(date));
            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {

                    }
                })
                .setType(new boolean[]{true, true, true, true, true, false})
                .isDialog(true)
                .build();


        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改動畫樣式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部顯示
            }
        }
    }

    private String getTime(Date date) {//可根據需要自行擷取資料顯示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm");
        return format.format(date);
    }


}
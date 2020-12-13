package com.skypan.myapplication.driver_model;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import com.skypan.myapplication.R;
import com.skypan.myapplication.Retrofit.Event;
import com.skypan.myapplication.Retrofit.User;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;


public class DriverMainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private EditText et_startTime;
    private EditText et_endTime;
    private Date startTime = new Date();
    private Date endTime = new Date();
    private TimePickerView pvTime;
    private String event_name;
    private Date[] acceptable_time_interval = new Date[2];
    private boolean[] day = new boolean[7];
    private CheckBox cb1,cb2,cb3,cb4,cb5,cb6,cb7;
    private String[] acceptable_start_point = new String[3];
    private String[] acceptable_end_point = new String[3];
    private int money,weight,gender;
    private boolean ishamlet;

    //private Event temp = new Event();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
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

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @SuppressLint("WrongViewCast")
    private void setButtonCustomDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DriverMainActivity.this);
        View v = getLayoutInflater().inflate(R.layout.set_custom_dialog_layout_with_button, null);
        alertDialog.setView(v);
        Button btOK = v.findViewById(R.id.button_ok);
        Button btC = v.findViewById(R.id.buttonCancel);

        //place
        final Button start = v.findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptable_start_point[0]=placeChoose(start);
            }
        });
        final Button start2 = v.findViewById(R.id.start2);
        start2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptable_start_point[1]=placeChoose(start2);
            }
        });
        final Button start3 = v.findViewById(R.id.start3);
        start3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptable_start_point[2]=placeChoose(start3);
            }
        });
        final Button end = v.findViewById(R.id.end);
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptable_end_point[0]=placeChoose(end);
            }
        });
        final Button end2 = v.findViewById(R.id.end2);
        end2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptable_end_point[1]=placeChoose(end2);
            }
        });
        final Button end3 = v.findViewById(R.id.end3);
        end3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptable_end_point[2]=placeChoose(end3);
            }
        });


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
                    ishamlet=true;
                }
            });
            mRg4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    ishamlet=false;
                }
            });
        }


        cb1 = v.findViewById(R.id.cb_1);
        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
               if(isChecked) {
                   day[0]=true;
                   System.out.println(day[0]);
               }else day[0]=false;
            }
        });
        cb2 = v.findViewById(R.id.cb_2);;
        cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) {
                    day[1]=true;
                    System.out.println(day[1]);
                }else day[1]=false;
            }
        });
        cb3 = v.findViewById(R.id.cb_3);;
        cb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) {
                    day[2]=true;
                    System.out.println(day[2]);
                }else day[2]=false;
            }
        });
        cb4 = v.findViewById(R.id.cb_4);;
        cb4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) {
                    day[3]=true;
                    System.out.println(day[3]);
                }else day[3]=false;
            }
        });
        cb5 = v.findViewById(R.id.cb_5);;
        cb5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) {
                    day[4]=true;
                    System.out.println(day[4]);
                }else day[4]=false;
            }
        });
        cb6 = v.findViewById(R.id.cb_6);;
        cb6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) {
                    day[5]=true;
                    System.out.println(day[5]);
                }else day[5]=false;
            }
        });
        cb7 = v.findViewById(R.id.cb_7);;
        cb7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) {
                    day[6]=true;
                    System.out.println(day[6]);
                }else day[6]=false;
            }
        });

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
                    acceptable_time_interval[0]=startTime;
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
                    acceptable_time_interval[1]=endTime;
                }
            }
        }));

        initTimePicker();

        btOK.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                final Editable Temp;

                event_name=name.toString();
                String value= editText_Money.getText().toString();
                money=Integer.parseInt(value);

               value= editText_Weught.getText().toString();
               weight=Integer.parseInt(value);

                // TODO: 2020/12/13 time
                AlertDialog.Builder twoDialog = new AlertDialog.Builder(DriverMainActivity.this);
                twoDialog.setTitle("新增成功");
                twoDialog.setPositiveButton("瞭解", (new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog1, int which) {

                        System.out.println(money+weight+gender);
                        //addSetting.addSetting(temp);
                        User user = new User("AAA", "token", "峻峻", "48763", true, 87, null, null);
                        Event e = new Event("AAA", event_name , "white", "BBB", "CCC"
                                , new ArrayList<Date>(Arrays.asList(acceptable_time_interval[0],acceptable_time_interval[1]))
                                , new ArrayList<String>(Arrays.asList(acceptable_start_point[0],acceptable_start_point[1],acceptable_start_point[2]))
                                , new ArrayList<String>(Arrays.asList(acceptable_end_point[0],acceptable_end_point[1],acceptable_end_point[2]))
                                , gender, weight, money, ishamlet, new ArrayList<Boolean>(Arrays.asList(day[0],day[1],day[2],day[3],day[4],day[5],day[6])), user);
                        addSetting.addSetting(e);
                        dialog.dismiss();
                        refresh();
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

        }));
        btC.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                dialog.dismiss();
            }
        }));
    }

    private String placeChoose(final Button start) {

        AlertDialog.Builder placealertDialog = new AlertDialog.Builder(DriverMainActivity.this);
        View v = getLayoutInflater().inflate(R.layout.choose_place, null);
        placealertDialog.setView(v);

        final AlertDialog dialog = placealertDialog.create();
        dialog.show();


        final RadioButton mp1 = v.findViewById(R.id.rb_place_1);
        final RadioButton mp2 = v.findViewById(R.id.rb_place_2);
        final RadioButton mp3 = v.findViewById(R.id.rb_place_3);
        final RadioButton mp4 = v.findViewById(R.id.rb_place_4);
        final RadioButton mp5 = v.findViewById(R.id.rb_place_5);
        final RadioButton mp6 = v.findViewById(R.id.rb_place_6);
        final RadioButton mp7 = v.findViewById(R.id.rb_place_7);
        final RadioButton mp8 = v.findViewById(R.id.rb_place_8);
        final RadioButton mp9 = v.findViewById(R.id.rb_place_9);
        final RadioButton mp10 = v.findViewById(R.id.rb_place_10);
        final String[] place = new String[1];
        mp1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                place[0] = String.valueOf(mp1.getText());
                start.setText(mp1.getText());
                dialog.dismiss();
            }
        });
        mp2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                place[0] = String.valueOf(mp2.getText());
                start.setText(mp2.getText());
                dialog.dismiss();
            }
        });
        mp3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                place[0] = String.valueOf(mp3.getText());
                start.setText(mp3.getText());
                dialog.dismiss();
            }
        });
        mp4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                place[0] = String.valueOf(mp4.getText());
                start.setText(mp4.getText());
                dialog.dismiss();
            }
        });
        mp5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                place[0] = String.valueOf(mp5.getText());
                start.setText(mp5.getText());
                dialog.dismiss();
            }
        });
        mp6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                place[0] = String.valueOf(mp6.getText());
                start.setText(mp6.getText());
                dialog.dismiss();
            }
        });
        mp7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                place[0] = String.valueOf(mp7.getText());
                start.setText(mp7.getText());
                dialog.dismiss();
            }
        });
        mp8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                place[0] = String.valueOf(mp8.getText());
                start.setText(mp8.getText());
                dialog.dismiss();
            }
        });
        mp9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                place[0] = String.valueOf(mp9.getText());
                start.setText(mp9.getText());
                dialog.dismiss();
            }
        });
        mp10.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                place[0] = String.valueOf(mp10.getText());
                start.setText(mp10.getText());
                dialog.dismiss();
            }
        });
        return place[0];
    }

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
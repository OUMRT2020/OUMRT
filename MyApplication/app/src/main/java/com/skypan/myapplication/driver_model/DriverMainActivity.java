package com.skypan.myapplication.driver_model;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DriverMainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private EditText et_startTime;
    private EditText et_endTime;
    private Date startTime = new Date();
    private Date endTime = new Date();
    private TimePickerView pvTime;
    private String[] day = new String[7];
    private final CompoundButton.OnCheckedChangeListener checkBoxOnCheckedChange =
            new CompoundButton.OnCheckedChangeListener() {
                int i = 0, j = 0;

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { //buttonView 為目前觸發此事件的 CheckBox, isChecked 為此 CheckBox 目前的選取狀態
                    if (isChecked)//等於 buttonView.isChecked()
                    {
                        Toast.makeText(getApplicationContext(), buttonView.getText() + " 被選取", Toast.LENGTH_LONG).show();
                        System.out.println(buttonView.getId());
                        i = (buttonView.getId()) - 2131230822;
                        day[i-3] = String.valueOf(buttonView.getText());
                        temp.setDay(String.valueOf(buttonView.getText()),i-3);
                    } else {
                        Toast.makeText(getApplicationContext(), buttonView.getText() + " 被取消", Toast.LENGTH_LONG).show();
                        j = (buttonView.getId()) - 2131230822;
                        day[j-3] = null;
                        temp.deleteDay(j-3);
                    }
                }
            };
    private Setting temp = new Setting();

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

    private void setButtonCustomDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DriverMainActivity.this);
        View v = getLayoutInflater().inflate(R.layout.set_custom_dialog_layout_with_button, null);
        alertDialog.setView(v);
        Button btOK = v.findViewById(R.id.button_ok);
        Button btC = v.findViewById(R.id.buttonCancel);
        final Button start = v.findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeChoose(start,0);
            }
        });
        final Button start2 = v.findViewById(R.id.start2);
        start2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeChoose(start2,1);
            }
        });
        final Button start3 = v.findViewById(R.id.start3);
        start3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeChoose(start3,2);
            }
        });

        final EditText editText_name = v.findViewById(R.id.name);
        final EditText editText_end = v.findViewById(R.id.end);
        final EditText editText_startTime = v.findViewById(R.id.et_startTime);
        final EditText editText_endTime = v.findViewById(R.id.et_endTime);
        final AlertDialog dialog = alertDialog.create();
        dialog.show();

        final RadioButton mRg1 = v.findViewById(R.id.rb_gender_1);
        final RadioButton mRg2 = v.findViewById(R.id.rb_gender_2);
        final String[] gender = new String[1];
        final RadioButton mRg3 = v.findViewById(R.id.rb_helmet_1);
        final RadioButton mRg4 = v.findViewById(R.id.rb_helmet_2);
        final String[] helmet = new String[1];
        final EditText editText_Money = v.findViewById(R.id.et_money);
        //radiobutton
        {
            mRg1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    gender[0] = String.valueOf(mRg1.getText());
                }
            });
            mRg2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    gender[0] = String.valueOf(mRg2.getText());
                }
            });
            mRg3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    helmet[0] = String.valueOf(mRg3.getText());
                }
            });
            mRg4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    helmet[0] = String.valueOf(mRg4.getText());
                }
            });
        }

        //checkbox
        {
            final CheckBox mCb1 = v.findViewById(R.id.cb_1);

            final CheckBox mCb2 = v.findViewById(R.id.cb_2);
            final CheckBox mCb3 = v.findViewById(R.id.cb_3);
            final CheckBox mCb4 = v.findViewById(R.id.cb_4);
            final CheckBox mCb5 = v.findViewById(R.id.cb_5);
            final CheckBox mCb6 = v.findViewById(R.id.cb_6);
            final CheckBox mCb7 = v.findViewById(R.id.cb_7);

            mCb1.setOnCheckedChangeListener(checkBoxOnCheckedChange);
            mCb2.setOnCheckedChangeListener(checkBoxOnCheckedChange);
            mCb3.setOnCheckedChangeListener(checkBoxOnCheckedChange);
            mCb4.setOnCheckedChangeListener(checkBoxOnCheckedChange);
            mCb5.setOnCheckedChangeListener(checkBoxOnCheckedChange);
            mCb6.setOnCheckedChangeListener(checkBoxOnCheckedChange);
            mCb7.setOnCheckedChangeListener(checkBoxOnCheckedChange);
        }


        btOK.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                final Editable Temp;
                temp.setName(editText_name.getText());
                //start
                temp.setEnd(editText_end.getText());
                temp.setMoney(editText_Money.getText());
                temp.setStartTime(editText_startTime.getText());
                temp.setEndTime(editText_endTime.getText());
                temp.setGender(gender[0]);
                temp.setHalmet(helmet[0]);


                AlertDialog.Builder twoDialog = new AlertDialog.Builder(DriverMainActivity.this);
                twoDialog.setTitle("新增成功");
                twoDialog.setPositiveButton("瞭解", (new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog1, int which) {

                        addSetting.addSetting(temp);
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
    }

    private String placeChoose(final Button start, final int i) {

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
                temp.setStart(place[0], 0);
                start.setText(temp.getStart1(0));
                temp.setPlace(place[0],i);
                dialog.dismiss();
            }
        });
        mp2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                place[0] = String.valueOf(mp2.getText());
                temp.setStart(place[0], 1);
                start.setText(temp.getStart1(1));
                temp.setPlace(place[0],i);
                dialog.dismiss();
            }
        });
        mp3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                place[0] = String.valueOf(mp3.getText());
                temp.setStart(place[0], 2);
                start.setText(temp.getStart1(2));
                temp.setPlace(place[0],i);
                dialog.dismiss();
            }
        });
        mp4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                place[0] = String.valueOf(mp4.getText());
                temp.setStart(place[0], 3);
                start.setText(temp.getStart1(3));
                temp.setPlace(place[0],i);
                dialog.dismiss();
            }
        });
        mp5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                place[0] = String.valueOf(mp5.getText());
                temp.setStart(place[0], 4);
                start.setText(temp.getStart1(4));
                temp.setPlace(place[0],i);
                dialog.dismiss();
            }
        });
        mp6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                place[0] = String.valueOf(mp6.getText());
                temp.setStart(place[0], 5);
                start.setText(temp.getStart1(5));
                temp.setPlace(place[0],i);
                dialog.dismiss();
            }
        });
        mp7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                place[0] = String.valueOf(mp7.getText());
                temp.setStart(place[0], 6);
                start.setText(temp.getStart1(6));
                temp.setPlace(place[0],i);
                dialog.dismiss();
            }
        });
        mp8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                place[0] = String.valueOf(mp8.getText());
                temp.setStart(place[0], 7);
                start.setText(temp.getStart1(7));
                temp.setPlace(place[0],i);
                dialog.dismiss();
            }
        });
        mp9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                place[0] = String.valueOf(mp9.getText());
                temp.setStart(place[0], 9);
                start.setText(temp.getStart1(9));
                temp.setPlace(place[0],i);
                dialog.dismiss();
            }
        });
        mp10.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                place[0] = String.valueOf(mp10.getText());
                temp.setStart(place[0], 9);
                start.setText(temp.getStart1(9));
                temp.setPlace(place[0],i);
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
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
        return format.format(date);
    }
}
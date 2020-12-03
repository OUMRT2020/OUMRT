package com.skypan.myapplication.passenger_model;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.skypan.myapplication.R;
import com.skypan.myapplication.passenger_model.Adapters.SearchedEventAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchEventsActivity extends AppCompatActivity {


    private String userID;
    private TextView date_and_time;
    private Button choose_date_and_time;
    private ImageButton btn_filter;
    private Switch sw_helmet, sw_isFree;
    private RadioGroup sex_radioGroup;
    private RadioButton sex_male, sex_female, sex_none;
    private TimePickerView pvTime;
    int rgSelected;
    boolean isHelmet, isFree;
    private FloatingActionButton btn_done_all;
    private RecyclerView recyclerView;
    List<event> events;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_events);
        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        date_and_time = findViewById(R.id.date_and_time);
        choose_date_and_time = findViewById(R.id.choose_date_and_time);
        btn_filter = findViewById(R.id.filter);
        btn_done_all = findViewById(R.id.btn_done_all);

        btn_done_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder testDialog = new AlertDialog.Builder(SearchEventsActivity.this);
                testDialog.setTitle("測試點擊");
                testDialog.setMessage("你可以關掉我了");
                testDialog.show();

            }
        });

        events = new ArrayList<>();
        for (int i = 0; i < 87; ++i) {
            event e = new event();
            e.setEvent_name("金瓜石特快車");

            List<Date> temp = new ArrayList<>();

            temp.add(new Date());
            temp.add(new Date());
            e.setAcceptable_time_interval(temp);
            events.add(e);
        }
        recyclerView = findViewById(R.id.rv_searched_events);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SearchedEventAdapter(SearchEventsActivity.this, events));


        choose_date_and_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pvTime != null) {
                    pvTime.show(view);//彈出時間選擇器，傳遞引數過去，回撥的時候則可以繫結此view
                }
            }
        });
        initTimePicker();
//        btn_time.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final Calendar c = Calendar.getInstance();
//                int hour = c.get(Calendar.HOUR_OF_DAY);
//                int minute = c.get(Calendar.MINUTE);
//                new TimePickerDialog(SearchEventsActivity.this, new TimePickerDialog.OnTimeSetListener() {
//
//                    @Override
//                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                        timeText.setText(hourOfDay + ":" + minute);
//                    }
//                }, hour, minute, false).show();
//
//            }
//        });

//        btn_date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Calendar calendar = Calendar.getInstance();
//                int year = calendar.get(Calendar.YEAR);
//                int month = calendar.get(Calendar.MONTH);
//                int day = calendar.get(Calendar.DAY_OF_MONTH);
//                new DatePickerDialog(SearchEventsActivity.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int day) {
//                        dateText.setText(String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(day));
//                    }
//                }, year, month, day).show();
//            }
//        });

        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new dialog
                View filter = getLayoutInflater().inflate(R.layout.popup_window, null);
                AlertDialog.Builder dialog = new AlertDialog.Builder(SearchEventsActivity.this);
                sw_helmet = filter.findViewById(R.id.sw_helmet);
                sw_isFree = filter.findViewById(R.id.sw_isFree);
                sex_radioGroup = filter.findViewById(R.id.sex_radioGroup);
                sex_male = filter.findViewById(R.id.sex_male);
                sex_female = filter.findViewById(R.id.sex_female);
                sex_none = filter.findViewById(R.id.sex_none);
                //紀錄條件的狀態
                sw_helmet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        isHelmet = isHelmet ^ true;
                    }
                });

                sw_isFree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        isFree = isFree ^ true;
                    }
                });

                sex_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        rgSelected = i;
                    }
                });

                //回復篩選條件
                sw_isFree.setChecked(isFree);
                sw_helmet.setChecked(isHelmet);
                if (sex_male.getId() == rgSelected) {
                    sex_male.setChecked(true);
                } else if (sex_female.getId() == rgSelected) {
                    sex_female.setChecked(true);
                } else {
                    sex_none.setChecked(true);
                }

//              rb = (RadioButton) sex_radioGroup.getChildAt(1);
//              rb.setChecked(true);
                dialog.setView(filter);
                dialog.setTitle("篩選器");
                dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.setNegativeButton("clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        isHelmet = false;
                        isFree = false;
                        rgSelected = 2;
                    }
                });
                dialog.show();
            }
        });
    }// end of onCreate

    private void initTimePicker() {//Dialog 模式下，在底部彈出

        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                date_and_time.setText(getTime(date));
            }
        }).setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
            @Override
            public void onTimeSelectChanged(Date date) {
            }
        }).setType(new boolean[]{true, true, true, true, true, false})//不顯示秒
                .setLabel("年", "月", "日", "時", "分", "秒")//修改預設的祖國文字
                .setSubmitText("確定")
                .isDialog(true) //預設設定false ，內部實現將DecorView 作為它的父控制元件。
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }
}
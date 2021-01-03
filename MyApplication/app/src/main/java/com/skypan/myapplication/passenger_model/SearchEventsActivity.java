package com.skypan.myapplication.passenger_model;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.skypan.myapplication.R;
import com.skypan.myapplication.Retrofit.Event;
import com.skypan.myapplication.Retrofit.Request;
import com.skypan.myapplication.Retrofit.RetrofitManagerAPI;
import com.skypan.myapplication.passenger_model.Adapters.SearchedEventAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchEventsActivity extends AppCompatActivity {


    private int rgSelected;
    private boolean isHelmet, isFree;
    private String user_id, TAG = "DEBUG";
    private TextView date_and_time;
    private Button choose_date_and_time;
    private ImageButton btn_filter;
    private Switch sw_helmet, sw_isFree;
    private RadioGroup sex_radioGroup;
    private RadioButton sex_male, sex_female, sex_none;
    private TimePickerView pvTime;
    private FloatingActionButton btn_done_all;
    private RecyclerView recyclerView;
    private EditText et_driver_name;
    private Spinner sp_pt_start, sp_pt_end;
    String[] pts = new String[]{"全聯福利中心 基隆中正店", "正宗永和豆漿", "海大(栙豐校門)", "海大(濱海校門)", "國立台灣海洋大學附屬基隆海事高級中等學院", "貴族世家 海洋大學店", "麥當勞-基隆新豐店", "愛買基隆店", "基隆車站", "姚家清魚湯"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_events);
        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        date_and_time = findViewById(R.id.date_and_time);
        choose_date_and_time = findViewById(R.id.choose_date_and_time);
        btn_filter = findViewById(R.id.filter);
        btn_done_all = findViewById(R.id.btn_done_all);
        et_driver_name = findViewById(R.id.et_driver_name);
        sp_pt_start = findViewById(R.id.sp_pt_start);
        sp_pt_end = findViewById(R.id.sp_pt_end);

        ArrayAdapter<String> adapter_pt = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, pts);
        adapter_pt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_pt_start.setAdapter(adapter_pt);
        sp_pt_end.setAdapter(adapter_pt);

        btn_done_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Request request = new Request(user_id, date_and_time.getText().toString(), sp_pt_start.getSelectedItem().toString(), sp_pt_end.getSelectedItem().toString());
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://140.121.197.130:5602/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RetrofitManagerAPI retrofitManagerAPI = retrofit.create(RetrofitManagerAPI.class);
                Log.d("user_id", user_id);

                Call<List<Event>> call = retrofitManagerAPI.getSearchEvents(user_id, et_driver_name.getText().toString(), sp_pt_start.getSelectedItem().toString(), sp_pt_end.getSelectedItem().toString(), date_and_time.getText().toString(), isHelmet, !isFree, rgSelected);
                //todo 需要修改
//                Call<List<Event>> call = retrofitManagerAPI.getSearchEvents("JIU", "", "海大校門口", "九份金瓜石", date_and_time.getText().toString(), isHelmet, !isFree, rgSelected);
                call.enqueue(new Callback<List<Event>>() {
                    @Override
                    public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                        if (!response.isSuccessful()) {
                            Log.d(TAG, String.valueOf(response.code()));
                        }
                        List<Event> events = response.body();
                        if (events != null) {
                            if (events.size() == 0) {
                                Log.d("debug", "no event");
                                Toast.makeText(SearchEventsActivity.this, "沒有搜尋到相符事件", Toast.LENGTH_SHORT).show();
                            }
                            recyclerView = findViewById(R.id.rv_searched_events);
                            recyclerView.setLayoutManager(new LinearLayoutManager(SearchEventsActivity.this));
                            recyclerView.setAdapter(new SearchedEventAdapter(SearchEventsActivity.this, events, request));
                        } else {
                            Log.d("debug", "null");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Event>> call, Throwable t) {
                        Log.d(TAG, t.getMessage());
                    }
                });
            }
        });

        choose_date_and_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pvTime != null) {
                    pvTime.show(view);//彈出時間選擇器，傳遞引數過去，回撥的時候則可以繫結此view
                }
            }
        });
        initTimePicker();

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
                Log.d(TAG, date.toString());
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
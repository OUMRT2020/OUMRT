package com.skypan.myapplication.driver_model.ui.home;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skypan.myapplication.R;
import com.skypan.myapplication.Retrofit.Ack;
import com.skypan.myapplication.Retrofit.Event;
import com.skypan.myapplication.Retrofit.RetrofitManagerAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchedDriveEventAdapter extends RecyclerView.Adapter<SearchedDriveEventAdapter.ViewHolder> {
    private Context mContext;
    private List<Event> Events;
    private Activity mActivity;
    public SearchedDriveEventAdapter(Activity mActivity ,Context mContext, List<Event> events) {
        this.mContext = mContext;
        this.Events = events;
        this.mActivity = mActivity;
    }

    @NonNull
    @Override
    public SearchedDriveEventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.event_driver_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchedDriveEventAdapter.ViewHolder holder, int position) {

        holder.event_name.setText("" + Events.get(position).getEvent_name());
        holder.event_time.setText("" + Events.get(position).getAcceptable_time_interval().get(0).toString()
                + " 至 " + Events.get(position).getAcceptable_time_interval().get(1).toString());
        holder.event_cost.setText("" + Events.get(position).getPrice());
        if (Events.get(position).getStatus().equals("white")) {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else if (Events.get(position).getStatus().equals("green")) {
            holder.itemView.setBackgroundColor(Color.parseColor("#CFDD8E"));
        }
    }

    @Override
    public int getItemCount() {
        return Events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView event_name, event_time, event_time2, event_cost;
        private Button event_delete;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            event_name = itemView.findViewById(R.id.event_title);
            event_time = itemView.findViewById(R.id.event_time);
//            event_time2 = itemView.findViewById(R.id.event_time2);
            event_cost = itemView.findViewById(R.id.event_cost);
            event_delete = itemView.findViewById(R.id.event_delete);


            event_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int position = getAdapterPosition();
                    final Event e = Events.get(position);
                    if (e.getStatus().equals("white")) {
                        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(mContext);
                        deleteDialog.setTitle("確定刪除?");
                        deleteDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // DO SOMETHING HERE
                                Events.remove(getAdapterPosition());
                                notifyItemRemoved(which);
                                notifyDataSetChanged();
                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl("http://140.121.197.130:5602/")
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();
                                RetrofitManagerAPI retrofitManagerAPI = retrofit.create(RetrofitManagerAPI.class);
                                Call<Ack> call = retrofitManagerAPI.deleteEvent(e.getEvent_id(), "delete");
                                call.enqueue(new Callback<Ack>() {
                                    @Override
                                    public void onResponse(Call<Ack> call, Response<Ack> response) {
                                        if (!response.isSuccessful()) {
                                            Log.d("delete", "delete driver error");
                                        }
                                        if (!response.body().isSuccess()) {
                                            Log.d("delete", response.body().getReason());
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Ack> call, Throwable t) {
                                        Log.d("delete", "delete server error");
                                    }
                                });
                            }
                        });
                        deleteDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        deleteDialog.show();
                    } else if (e.getStatus().equals("green")) {
                        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(mContext);
                        deleteDialog.setTitle("事件結束");
                        deleteDialog.setMessage("Drop: 事件不正常結束並刪除\nFinish: 事件正常結束");

                        deleteDialog.setPositiveButton("Finish", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // DO SOMETHING HERE
                                Events.remove(getAdapterPosition());
                                notifyItemRemoved(which);
                                notifyDataSetChanged();
                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl("http://140.121.197.130:5602/")
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();
                                RetrofitManagerAPI retrofitManagerAPI = retrofit.create(RetrofitManagerAPI.class);
                                Call<Ack> call = retrofitManagerAPI.deleteEvent(e.getEvent_id(), "finish");
                                call.enqueue(new Callback<Ack>() {
                                    @Override
                                    public void onResponse(Call<Ack> call, Response<Ack> response) {
                                        if (!response.isSuccessful()) {
                                            Log.d("Finish", "Finish driver error");
                                            Toast.makeText(mContext, "結束失敗1", Toast.LENGTH_SHORT).show();
                                        }
                                        if (!response.body().isSuccess()) {
                                            Log.d("Finish", response.body().getReason());
                                            Toast.makeText(mContext, "結束失敗2", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Ack> call, Throwable t) {
                                        Log.d("Finish", "Finish server error");
                                        Toast.makeText(mContext, "結束失敗3", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });

                        deleteDialog.setNeutralButton("Drop", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // DO SOMETHING HERE
                                Events.remove(getAdapterPosition());
                                notifyItemRemoved(which);
                                notifyDataSetChanged();
                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl("http://140.121.197.130:5602/")
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();
                                RetrofitManagerAPI retrofitManagerAPI = retrofit.create(RetrofitManagerAPI.class);
                                Call<Ack> call = retrofitManagerAPI.deleteEvent(e.getEvent_id(), "drop");
                                call.enqueue(new Callback<Ack>() {
                                    @Override
                                    public void onResponse(Call<Ack> call, Response<Ack> response) {
                                        if (!response.isSuccessful()) {
                                            Log.d("drop", "drop driver error");
                                        }
                                        if (!response.body().isSuccess()) {
                                            Log.d("drop", response.body().getReason());
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Ack> call, Throwable t) {
                                        Log.d("drop", "drop server error");
                                    }
                                });
                            }
                        });


                        deleteDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        deleteDialog.show();

                    }
                }
            });


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = getAdapterPosition();
                    final Event e = Events.get(position);

                    if (e.getStatus().equals("white")) {
                        if (e.getAll_request().size() == 0) {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

                            final View content_layout = LayoutInflater.from(mContext).inflate(R.layout.change_request, null);
                            EditText name = content_layout.findViewById(R.id.change_name);
                            EditText money = content_layout.findViewById(R.id.change_money);
                            EditText weight = content_layout.findViewById(R.id.change_weight);
                            TextView start = content_layout.findViewById(R.id.change_start);
                            TextView end = content_layout.findViewById(R.id.change_end);
                            TextView startTime = content_layout.findViewById(R.id.change_startTime);
                            TextView endTime = content_layout.findViewById(R.id.change_endTime);
                            RadioGroup gender =content_layout.findViewById(R.id.rg_gender);
                            RadioButton gender_1 = content_layout.findViewById(R.id.rb_gender_1);
                            RadioButton gender_2 = content_layout.findViewById(R.id.rb_gender_2);
                            RadioButton gender_3 = content_layout.findViewById(R.id.rb_gender_3);

                            RadioGroup helmet =content_layout.findViewById(R.id.rg_helmet);
                            RadioButton helmet_1 = content_layout.findViewById(R.id.rb_helmet_1);
                            RadioButton helmet_2 = content_layout.findViewById(R.id.rb_helmet_2);
                            name.setText(e.getEvent_name());
                            money.setText(String.valueOf(e.getPrice()));
                            weight.setText(String.valueOf(e.getMax_weight()));
//                            Toast.makeText(mContext, e.getAcceptable_start_point().toString(), Toast.LENGTH_SHORT).show();
                            String start_pt_string = "";
                            String end_pt_string = "";
                            for (int i = 0; i < e.getAcceptable_start_point().size(); i++) {
                                if (i != 0) {
                                    start_pt_string += ",";
                                }
                                start_pt_string += e.getAcceptable_start_point().get(i);
                            }
                            for (int i = 0; i < e.getAcceptable_end_point().size(); i++) {
                                if (i != 0) {
                                    end_pt_string += ",";
                                }

                                end_pt_string += e.getAcceptable_end_point().get(i);
                            }
                            System.out.println(end_pt_string);
                            System.out.println(start_pt_string);
                            start.setText(start_pt_string);
                            end.setText(end_pt_string);
                            startTime.setText(e.getAcceptable_time_interval().get(0));
                            endTime.setText(e.getAcceptable_time_interval().get(1));
                            if (e.getAcceptable_sex() == 0) gender_1.setChecked(true);
                            if (e.getAcceptable_sex() == 1) gender_2.setChecked(true);
                            if (e.getAcceptable_sex() == 2) gender_3.setChecked(true);
                            if (e.isIs_self_helmet()) helmet_1.setChecked(true);
                            if (!e.isIs_self_helmet()) helmet_2.setChecked(true);

                            alertDialog.setPositiveButton("修改", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    int GENDER=0;
                                    boolean HELMET = false;
                                    System.out.println(gender.getCheckedRadioButtonId());
                                    switch (gender.getCheckedRadioButtonId()){
                                        case R.id.rb_gender_1:
                                            GENDER = 0;
                                            break;
                                        case R.id.rb_gender_2:
                                            GENDER = 1;
                                            break;
                                        case R.id.rb_gender_3:
                                            GENDER = 2;
                                            break;
                                    }
                                    switch (helmet.getCheckedRadioButtonId()){
                                        case R.id.rb_helmet_1:
                                            HELMET = true;
                                            break;
                                        case R.id.rb_helmet_2:
                                            HELMET = false;
                                            break;
                                    }
                                    String W= weight.getText().toString();
                                    int WEIGHT=Integer.parseInt(W);
                                    String M= money.getText().toString();
                                    int MONEY=Integer.parseInt(M);
                                    System.out.println("event_name" + name.getText().toString());
                                    System.out.println("status" + "white");
                                    System.out.println("user_id" + e.getDriver_id());
                                    System.out.println("acc_time_interval" + e.getAcceptable_time_interval());
                                    System.out.println("acc_start_pts" + e.getAcceptable_start_point());
                                    System.out.println("acc_end_pt" +  e.getAcceptable_end_point());
                                    System.out.println("gender" + GENDER);
                                    System.out.println("weight" + WEIGHT);
                                    System.out.println("money" + MONEY);
                                    System.out.println("ishamlet" + HELMET);
                                    System.out.println("repeat" + e.getRepeat());
                                    Event alter = new Event(name.getText().toString(),"white",e.getDriver_id()
                                            ,e.getAcceptable_time_interval(),e.getAcceptable_start_point(),e.getAcceptable_end_point()
                                    ,GENDER,WEIGHT,MONEY,HELMET,e.getRepeat());
                                    alter.setEvent_id(e.getEvent_id());
                                    Retrofit retrofit = new Retrofit.Builder()
                                            .baseUrl("http://140.121.197.130:5602/")
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();
                                    RetrofitManagerAPI retrofitManagerAPI = retrofit.create(RetrofitManagerAPI.class);
                                    Call<Ack> call = retrofitManagerAPI.alterEvent(alter);
                                    call.enqueue(new Callback<Ack>() {
                                        @Override
                                        public void onResponse(Call<Ack> call, Response<Ack> response) {
                                            if (!response.isSuccessful()) {
                                                Log.d("add", "Alter enent error");
                                            }
                                            Ack ack = response.body();
                                            Log.d("ACK", ack.isSuccess() ? "true" : "fasle");


                                        }

                                        @Override
                                        public void onFailure(Call<Ack> call, Throwable t) {
                                            Log.d("add", "new enent server error");
                                        }
                                    });


                                }
                            });
                            alertDialog.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });

                            alertDialog.setView(content_layout);
                            alertDialog.show();
                        } else {//白色，且有請求
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                            alertDialog.setTitle("乘客申請名單\n(有多筆請求，接受將自動拒絕其餘)");
                            //final View content_layout = LayoutInflater.from(mContext).inflate(R.layout.searched_driver_event_detail, null);
                            final View content_layout = LayoutInflater.from(mContext).inflate(R.layout.event_passenger_request, null);
                            RecyclerView recyclerView;
                            recyclerView = content_layout.findViewById(R.id.recyclerView_in_recyclerView);
                            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                            recyclerView.setAdapter(new requests_in_multiRecyclerViewAdapter(mActivity, mContext, e.getAll_request(), e.getAll_request_user()));

                            alertDialog.setView(content_layout);
                            alertDialog.show();
                        }

                    } else if (e.getStatus().equals("green")) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);


                        final View content_layout = LayoutInflater.from(mContext).inflate(R.layout.driver_main_event_detail, null);

                        TextView foo;
                        foo = content_layout.findViewById(R.id.tv_1);
                        foo.setText("乘客姓名");
                        foo = content_layout.findViewById(R.id.driver_name);//直接用乘客主畫面的layout了 id不想改
                        foo.setText(e.getUser().getName());

                        foo = content_layout.findViewById(R.id.tv_2);
                        foo.setText("乘客性別");
                        foo = content_layout.findViewById(R.id.driver_sex);
                        foo.setText(e.getUser().isSex() ? "男" : "女");

                        foo = content_layout.findViewById(R.id.tv_3);//顯示乘客的電話
                        foo.setVisibility(View.VISIBLE);
                        foo = content_layout.findViewById(R.id.driver_phone);
                        foo.setVisibility(View.VISIBLE);

                        foo = content_layout.findViewById(R.id.actual_start_point);
                        foo.setText(e.getFinal_request().getActual_start_point());

                        foo = content_layout.findViewById(R.id.actual_end_point);
                        foo.setText(e.getFinal_request().getActual_end_point());

                        foo = content_layout.findViewById(R.id.actual_time);
                        foo.setText(e.getFinal_request().getActual_time());

//                        //把車照隱藏
//                        foo = content_layout.findViewById(R.id.tv_7);
//                        foo.setVisibility(View.GONE);
//                        ImageView imageView;
//                        imageView = content_layout.findViewById(R.id.picture);
//                        imageView.setVisibility(View.GONE);

                        foo = content_layout.findViewById(R.id.tv_extra_need);
                        foo.setText(e.getFinal_request().getExtra_needed());

                        alertDialog.setPositiveButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });


                        alertDialog.setView(content_layout);
                        alertDialog.show();
                    }

                }
            });
        }
    }
}

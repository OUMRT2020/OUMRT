package com.skypan.myapplication.passenger_model.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skypan.myapplication.R;
import com.skypan.myapplication.Retrofit.Ack;
import com.skypan.myapplication.Retrofit.Event;
import com.skypan.myapplication.Retrofit.Request;
import com.skypan.myapplication.Retrofit.RetrofitManagerAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchedEventAdapter extends RecyclerView.Adapter<SearchedEventAdapter.ViewHolder> {
    private Context mContext;
    private List<Event> Events;
    private Request request;

    public SearchedEventAdapter(Context mContext, List<Event> Events, Request request) {
        this.mContext = mContext;
        this.Events = Events;
        this.request = request;
    }

    @NonNull
    @Override
    public SearchedEventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.event_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchedEventAdapter.ViewHolder holder, int position) {
        holder.event_name.setText("" + Events.get(position).getEvent_name());
        holder.driver_rate.setText("" + Events.get(position).getUser().getRate().getScore());
        holder.event_time.setText("" + Events.get(position).getAcceptable_time_interval().get(0) + "至" + Events.get(position).getAcceptable_time_interval().get(1));
        holder.event_cost.setText("" + Events.get(position).getPrice());

        if (Events.get(position).getStatus().equals("white")) {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else if (Events.get(position).getStatus().equals("green")) {
            holder.itemView.setBackgroundColor(Color.parseColor("#CFDD8E"));
        } else if (Events.get(position).getStatus().equals("red")) {
            holder.itemView.setBackgroundColor(Color.parseColor("#E4BEB3"));
        }
    }

    @Override
    public int getItemCount() {
        return Events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView event_name, driver_rate, event_time, event_cost;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            event_name = itemView.findViewById(R.id.event_title);
            driver_rate = itemView.findViewById(R.id.event_driver_rate);
            event_time = itemView.findViewById(R.id.event_date);
            event_cost = itemView.findViewById(R.id.event_cost);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = getAdapterPosition();
                    final Event e = Events.get(position);
                    final AlertDialog.Builder detailDialog = new AlertDialog.Builder(mContext);
                    final View content_layout = LayoutInflater.from(mContext).inflate(R.layout.searched_event_detail, null);

                    if (e.getStatus().equals("red")) {//若是紅色
                        detailDialog.setTitle("拒絕原因");
                        detailDialog.setMessage(e.getReason());
                    } else if (e.getStatus().equals("white")) {//若是白色
                        TextView foo;
                        foo = content_layout.findViewById(R.id.driver_name);
                        foo.setText(e.getUser().getName());
                        foo = content_layout.findViewById(R.id.driver_sex);
                        foo.setText(e.getUser().isSex() ? "男" : "女");

                        foo = content_layout.findViewById(R.id.acceptable_pt_start);
                        ArrayList<String> acceptable_list = e.getAcceptable_start_point();
                        String temp = "";
                        for (int i = 0; i < acceptable_list.size(); ++i) {
                            if (i != 0) {
                                temp += '、';
                            }
                            temp += acceptable_list.get(i);
                        }
                        foo.setText(temp);

                        foo = content_layout.findViewById(R.id.acceptable_pt_end);
                        acceptable_list = e.getAcceptable_end_point();
                        temp = "";
                        for (int i = 0; i < acceptable_list.size(); ++i) {
                            if (i != 0) {
                                temp += '、';
                            }
                            temp += acceptable_list.get(i);
                        }
                        foo.setText(temp);

                        foo = content_layout.findViewById(R.id.time_interval);
                        acceptable_list = e.getAcceptable_time_interval();
                        temp = "";
                        for (int i = 0; i < acceptable_list.size(); ++i) {
                            if (i != 0) {
                                temp += ' ';
                            }
                            temp += acceptable_list.get(i);
                        }
                        foo.setText(temp);

                        detailDialog.setView(content_layout);
                        detailDialog.setTitle(e.getEvent_name());
                        detailDialog.setPositiveButton("送出請求", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                request.setEvent_id(e.getEvent_id());
                                EditText et = content_layout.findViewById(R.id.et_extra_need);
                                request.setExtra_needed(et.getText().toString());

                                System.out.println(request.getUser_id());
                                System.out.println(request.getActual_end_point());
                                System.out.println(request.getActual_start_point());
                                System.out.println(request.getActual_time());
                                System.out.println(request.getEvent_id());
                                System.out.println(request.getExtra_needed());

                                //送出請求
                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl("http://140.121.197.130:5602/")
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();
                                RetrofitManagerAPI retrofitManagerAPI = retrofit.create(RetrofitManagerAPI.class);
                                Call<Ack> call = retrofitManagerAPI.sendRequest(request);
                                call.enqueue(new Callback<Ack>() {
                                    @Override
                                    public void onResponse(Call<Ack> call, Response<Ack> response) {
                                        if (!response.isSuccessful()) {
                                            Toast.makeText(mContext, "404 Not Found", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Ack ack = response.body();
                                            if (ack.isSuccess()) {
                                                Toast.makeText(mContext, request.getExtra_needed(), Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(mContext, ack.getReason(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Ack> call, Throwable t) {
                                        Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });

                        detailDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                    }

                    detailDialog.show();
                }

            });
        }
    }
}

package com.skypan.myapplication.driver_model.ui.history;


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
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.skypan.myapplication.R;
import com.skypan.myapplication.Retrofit.Ack;
import com.skypan.myapplication.Retrofit.Past_Event;
import com.skypan.myapplication.Retrofit.RetrofitManagerAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchedHistoryAdapter extends RecyclerView.Adapter<SearchedHistoryAdapter.ViewHolder> {
    private Context mContext;
    private List<Past_Event> Events;
    private String mUserid;
    private SwipeRefreshLayout swipeRefreshLayout;
    public SearchedHistoryAdapter(SwipeRefreshLayout swipeRefreshLayout, Context mContext, List<Past_Event> events, String mUserid) {
        this.mContext = mContext;
        this.Events = events;
        this.mUserid = mUserid;
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    @NonNull
    @Override
    public SearchedHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.event_history_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchedHistoryAdapter.ViewHolder holder, int position) {
        Past_Event e = Events.get(position);
        holder.event_name.setText(e.getEvent_name());
        holder.event_time.setText(e.getFinal_request().getActual_time());
        if (e.getStatus().equals("red")) {
            holder.itemView.setBackgroundColor(Color.parseColor("#FF0000"));
        } else if (e.getStatus().equals("gray")) {
            holder.itemView.setBackgroundColor(Color.parseColor("#808080"));
        }
        if ((e.getIs_rated() == 3) || (e.getIs_rated() == 1 && e.getDriver_id().equals(mUserid)) || (e.getIs_rated() == 2 && e.getPassenger_id().equals(mUserid))) {
            holder.event_rate.setEnabled(false);
        }
        holder.event_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder popDialog = new AlertDialog.Builder(mContext);

                LinearLayout linearLayout = new LinearLayout(mContext);
                final RatingBar rating = new RatingBar(mContext);

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                rating.setLayoutParams(lp);
                rating.setNumStars(5);
                rating.setStepSize(1);

                //add ratingBar to linearLayout
                linearLayout.addView(rating);
                linearLayout.setGravity(View.TEXT_ALIGNMENT_CENTER);

                popDialog.setTitle("請輸入您的評分");

                //add linearLayout to dailog
                popDialog.setView(linearLayout);


                rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                        System.out.println("Rated val:" + rating.getRating());
                    }
                });


                // Button OK
                popDialog.setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl("http://140.121.197.130:5602/")
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();
                                RetrofitManagerAPI retrofitManagerAPI = retrofit.create(RetrofitManagerAPI.class);
                                Call<Ack> call = retrofitManagerAPI.Rating(e.getUser().getUser_id(), e.getEvent_id(), "" + Math.round(rating.getRating()));
                                call.enqueue(new Callback<Ack>() {
                                    @Override
                                    public void onResponse(Call<Ack> call, Response<Ack> response) {
                                        if (!response.isSuccessful()) {
                                            Log.d("rate", "rating error");
                                        } else {
                                            Toast.makeText(mContext, "評分成功", Toast.LENGTH_LONG);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Ack> call, Throwable t) {
                                        Log.d("rate", "rating server error");
                                    }
                                });
                            }
                        })

                        // Button Cancel
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                popDialog.create();
                popDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return Events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView event_name, event_time, event_time2, event_cost;
        private Button event_rate;
        ;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            event_name = itemView.findViewById(R.id.event_title);
            event_time = itemView.findViewById(R.id.event_time);
            event_cost = itemView.findViewById(R.id.event_cost);
            event_rate = itemView.findViewById(R.id.event_rate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final int position = getAdapterPosition();
                    final Past_Event e = Events.get(position);
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                    alertDialog.setTitle("歷史資訊");

                    final View content_layout = LayoutInflater.from(mContext).inflate(R.layout.driver_main_event_detail, null);

                    TextView foo;
                    foo = content_layout.findViewById(R.id.tv_1);
                    foo.setText("姓名");
                    foo = content_layout.findViewById(R.id.driver_name);//直接用乘客主畫面的layout了 id不想改
                    foo.setText(e.getUser().getName());

                    foo = content_layout.findViewById(R.id.tv_2);
                    foo.setText("性別");
                    foo = content_layout.findViewById(R.id.driver_sex);
                    foo.setText(e.getUser().isSex() ? "男" : "女");


                    foo = content_layout.findViewById(R.id.actual_start_point);
                    foo.setText(e.getFinal_request().getActual_start_point());

                    foo = content_layout.findViewById(R.id.actual_end_point);
                    foo.setText(e.getFinal_request().getActual_end_point());

                    foo = content_layout.findViewById(R.id.actual_time);
                    foo.setText(e.getFinal_request().getActual_time());


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
            });


        }
    }
}

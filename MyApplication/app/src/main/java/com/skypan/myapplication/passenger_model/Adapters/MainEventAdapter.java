package com.skypan.myapplication.passenger_model.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skypan.myapplication.R;
import com.skypan.myapplication.Retrofit.Event;

import java.util.List;

public class MainEventAdapter extends RecyclerView.Adapter<MainEventAdapter.ViewHolder> {
    private Context mContext;
    private List<Event> Events;

    public MainEventAdapter(Context mContext, List<Event> Events) {
        this.mContext = mContext;
        this.Events = Events;
    }

    @NonNull
    @Override
    public MainEventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.event_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainEventAdapter.ViewHolder holder, int position) {
        holder.event_name.setText("" + Events.get(position).getEvent_name());
        holder.driver_rate.setText("" + Events.get(position).getUser().getRate().getScore());
        holder.event_time.setText("" + Events.get(position).getAcceptable_time_interval().get(0) + " " + Events.get(position).getAcceptable_time_interval().get(1));
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

        public ViewHolder(@NonNull View itemView) {
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
                    final View content_layout = LayoutInflater.from(mContext).inflate(R.layout.passenger_main_event_detail, null);
                    TextView foo;
                    if (e.getStatus().equals("red")) {//////////////////////////////////////若是紅色
                        detailDialog.setTitle("拒絕原因");
                        detailDialog.setMessage(e.getUser().getName() + " : " + e.getReason());
                    } else if (e.getStatus().equals("white")) {/////////////////////////////若是白色
                        foo = content_layout.findViewById(R.id.driver_name);
                        foo.setText(e.getUser().getName());
                        foo = content_layout.findViewById(R.id.driver_sex);
                        foo.setText(e.getUser().isSex() ? "男" : "女");

                        foo = content_layout.findViewById(R.id.actual_start_point);
                        foo.setText(e.getMy_request().getActual_start_point());

                        foo = content_layout.findViewById(R.id.actual_end_point);
                        foo.setText(e.getMy_request().getActual_end_point());

                        foo = content_layout.findViewById(R.id.actual_time);
                        foo.setText(e.getMy_request().getActual_time());

                        foo = content_layout.findViewById(R.id.tv_extra_need);
                        foo.setText(e.getMy_request().getExtra_needed());

                        detailDialog.setView(content_layout);
                        detailDialog.setTitle(e.getEvent_name());
                    } else if (e.getStatus().equals("green")) {////////////////////////////若是綠色
                        foo = content_layout.findViewById(R.id.driver_name);
                        foo.setText(e.getUser().getName());
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

                        foo = content_layout.findViewById(R.id.tv_3);//打電話嘍
                        foo.setVisibility(View.VISIBLE);

                        foo = content_layout.findViewById(R.id.driver_phone);
                        foo.setText(e.getUser().getPhone_num());
                        foo.setVisibility(View.VISIBLE);

                        ImageButton btn_img = content_layout.findViewById(R.id.phone_icon);
                        btn_img.setOnClickListener(new View.OnClickListener() {//打電話嘍
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", e.getUser().getPhone_num(), null));
                                mContext.startActivity(intent);
                            }
                        });
                        btn_img.setVisibility(View.VISIBLE);
                        detailDialog.setView(content_layout);
                        detailDialog.setTitle(e.getEvent_name());
                    }
                    detailDialog.show();
                }

            });
        }
    }
}

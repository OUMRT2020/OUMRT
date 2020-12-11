package com.skypan.myapplication.passenger_model.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skypan.myapplication.R;
import com.skypan.myapplication.Retrofit.Event;

import java.util.List;

public class SearchedEventAdapter extends RecyclerView.Adapter<SearchedEventAdapter.ViewHolder> {
    private Context mContext;
    private List<Event> Events;

    public SearchedEventAdapter(Context mContext, List<Event> Events) {
        this.mContext = mContext;
        this.Events = Events;
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
        holder.event_time.setText("" + Events.get(position).getAcceptable_time_interval().get(0).toString());
        holder.event_cost.setText("" + Events.get(position).getPrice());
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
            itemView.setBackgroundColor(Color.parseColor("#555555"));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final View content_layout = LayoutInflater.from(mContext).inflate(R.layout.searched_event_detail, null);
                    AlertDialog.Builder testDialog = new AlertDialog.Builder(mContext);

                    TextView foo;
                    foo = content_layout.findViewById(R.id.driver_name);
                    foo.setText(Events.get(position).getUser().getName());
                    foo = content_layout.findViewById(R.id.driver_sex);
                    foo.setText(Events.get(position).getUser().isSex() ? "男" : "女");
                    foo = content_layout.findViewById(R.id.acceptable_pt_start);
                    foo = content_layout.findViewById(R.id.acceptable_pt_end);

                    testDialog.setView(content_layout);
                    testDialog.setTitle(Events.get(position).getEvent_name());
                    testDialog.setPositiveButton("送出請求", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //送出請求
                        }
                    });
                    testDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    testDialog.show();
                }
            });
        }
    }
}

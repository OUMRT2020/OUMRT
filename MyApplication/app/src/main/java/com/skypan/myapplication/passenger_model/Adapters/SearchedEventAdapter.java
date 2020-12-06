package com.skypan.myapplication.passenger_model.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skypan.myapplication.R;
import com.skypan.myapplication.passenger_model.user;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class SearchedEventAdapter extends RecyclerView.Adapter<SearchedEventAdapter.ViewHolder> {
    private Context mContext;
    private List<user> users;
    private List<JSONObject> events;

    public SearchedEventAdapter(Context mContext, List<JSONObject> events) {
        this.mContext = mContext;
        this.events = events;
    }

    @NonNull
    @Override
    public SearchedEventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.event_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchedEventAdapter.ViewHolder holder, int position) {
        try {
            holder.event_name.setText(events.get(position).getString("event_name"));
            holder.driver_rate.setText(events.get(position).getJSONObject("rate").getString("score"));
            holder.event_time.setText((CharSequence) events.get(position).getString("acceptble_time_interval"));
            holder.event_cost.setText(events.get(position).getString("price"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView event_name, driver_rate, event_time, event_cost;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            event_name = itemView.findViewById(R.id.event_title);
            driver_rate = itemView.findViewById(R.id.event_driver_rate);
            event_time = itemView.findViewById(R.id.event_date);
            event_cost = itemView.findViewById(R.id.event_cost);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    AlertDialog.Builder testDialog = new AlertDialog.Builder(mContext);
                    try {
                        testDialog.setTitle(events.get(position).getString("event_name"));
                        testDialog.setMessage("司機姓名:" + events.get(position).getString("name") + "\n司機電話:" + events.get(position).getString("phone_num"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    testDialog.show();
                }
            });
        }
    }
}

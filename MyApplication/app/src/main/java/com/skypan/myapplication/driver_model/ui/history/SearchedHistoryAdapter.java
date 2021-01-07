package com.skypan.myapplication.driver_model.ui.history;


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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skypan.myapplication.R;
import com.skypan.myapplication.Retrofit.Ack;
import com.skypan.myapplication.Retrofit.Event;
import com.skypan.myapplication.Retrofit.Past_Event;
import com.skypan.myapplication.Retrofit.RetrofitManagerAPI;
import com.skypan.myapplication.driver_model.ui.home.requests_in_multiRecyclerViewAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchedHistoryAdapter extends RecyclerView.Adapter<SearchedHistoryAdapter.ViewHolder> {
    private Context mContext;
    private List<Past_Event> Events;

    public SearchedHistoryAdapter(Context mContext, List<Past_Event> events) {
        this.mContext = mContext;
        this.Events = events;
    }

    @NonNull
    @Override
    public SearchedHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.event_history_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchedHistoryAdapter.ViewHolder holder, int position) {

        holder.event_name.setText("" + Events.get(position).getEvent_name());
        holder.event_time.setText("" + Events.get(position).getAcceptable_time_interval().get(0).toString()
                + " 至 " + Events.get(position).getAcceptable_time_interval().get(1).toString());
        holder.event_cost.setText("" + Events.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return Events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView event_name, event_time, event_time2, event_cost;
        private Button event_delete;
        ;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            event_name = itemView.findViewById(R.id.event_title);
            event_time = itemView.findViewById(R.id.event_time);
            event_cost = itemView.findViewById(R.id.event_cost);
            event_delete = itemView.findViewById(R.id.event_delete);

        }
    }
}

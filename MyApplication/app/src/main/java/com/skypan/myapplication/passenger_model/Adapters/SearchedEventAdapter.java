package com.skypan.myapplication.passenger_model.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skypan.myapplication.R;
import com.skypan.myapplication.passenger_model.event;
import com.skypan.myapplication.passenger_model.user;

import java.util.List;

public class SearchedEventAdapter  extends RecyclerView.Adapter<SearchedEventAdapter.ViewHolder>{
    private Context mContext;
    private List<user> users;
    private List<event> events;
    public SearchedEventAdapter(Context mContext,List<event>events) {
        this.mContext = mContext;
        this.events = events;
    }

    @NonNull
    @Override
    public SearchedEventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.event_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchedEventAdapter.ViewHolder holder, int position) {
        holder.textView.setText(events.get(position).getEvent_name());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.event_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "被你點到第" + getAdapterPosition() + "個了", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

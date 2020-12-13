package com.skypan.myapplication.driver_model.ui.home;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skypan.myapplication.R;
import com.skypan.myapplication.Retrofit.Event;
import com.skypan.myapplication.driver_model.Setting;

import java.util.ArrayList;

public class SearchedDriveEventAdapter extends RecyclerView.Adapter<SearchedDriveEventAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Event> Events;

    public SearchedDriveEventAdapter(Context mContext, ArrayList<Event> events) {
        this.mContext = mContext;
        this.Events = events;
    }

    @NonNull
    @Override
    public SearchedDriveEventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.event_driver_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchedDriveEventAdapter.ViewHolder holder, int position) {

        holder.event_name.setText("" + Events.get(position).getEvent_name());
        holder.event_time.setText("" + Events.get(position).getAcceptable_time_interval().get(0).toString());
        holder.event_cost.setText("" + Events.get(position).getPrice());
        if(Events.get(position).getStatus()=="green"){}
    }

    @Override
    public int getItemCount() {
        return Events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView event_name, event_time, event_cost;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            event_name = itemView.findViewById(R.id.event_title);
            event_time = itemView.findViewById(R.id.event_time);
            event_cost = itemView.findViewById(R.id.event_cost);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "被你點到第" + getAdapterPosition() + "個了", Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                    alertDialog.setTitle("Your title");
                    alertDialog.setMessage("起始地點" + "\n" );


                    alertDialog.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alertDialog.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            // DO SOMETHING HERE
                            Events.remove(getAdapterPosition());
                            notifyItemRemoved(which);
                            notifyDataSetChanged();
                            System.out.println(Events.size());
                        }
                    });

                    AlertDialog dialog = alertDialog.create();
                    dialog.show();
                }
            });
        }
    }
}

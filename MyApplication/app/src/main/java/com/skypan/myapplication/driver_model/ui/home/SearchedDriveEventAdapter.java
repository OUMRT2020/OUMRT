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
import com.skypan.myapplication.driver_model.ui.Setting;

import java.util.ArrayList;

public class SearchedDriveEventAdapter extends RecyclerView.Adapter<SearchedDriveEventAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Setting> events;

    public SearchedDriveEventAdapter(Context mContext, ArrayList<Setting> events) {
        this.mContext = mContext;
        this.events = events;
    }

    @NonNull
    @Override
    public SearchedDriveEventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.event_driver_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchedDriveEventAdapter.ViewHolder holder, int position) {
        holder.textView.setText(events.get(position).getName());
        holder.time.setText(events.get(position).getStarttime());
        holder.endtime.setText(events.get(position).getEndtime());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView, time, endtime;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.event_title);
            time = itemView.findViewById(R.id.event_time);
            endtime = itemView.findViewById(R.id.event_endtime);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "被你點到第" + getAdapterPosition() + "個了", Toast.LENGTH_SHORT).show();
                    System.out.println(events.size());

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                    alertDialog.setTitle("Your title");
                    alertDialog.setMessage("起始地點" + events.get(getAdapterPosition()).getStart());


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
                            events.remove(getAdapterPosition());
                            notifyItemRemoved(which);
                            notifyDataSetChanged();


                        }
                    });

                    AlertDialog dialog = alertDialog.create();
                    dialog.show();
                }
            });
        }
    }
}

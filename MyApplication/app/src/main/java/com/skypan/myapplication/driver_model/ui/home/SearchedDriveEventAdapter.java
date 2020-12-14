package com.skypan.myapplication.driver_model.ui.home;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

        private TextView event_name, event_time, event_cost;
        private Button event_delete;;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            event_name = itemView.findViewById(R.id.event_title);
            event_time = itemView.findViewById(R.id.event_time);
            event_cost = itemView.findViewById(R.id.event_cost);
            event_delete = itemView.findViewById(R.id.event_delete);
            event_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder deleteDialog = new AlertDialog.Builder(mContext);
                    deleteDialog.setTitle("Your title");
                    deleteDialog.setMessage("起始地點" + "\n");
                    deleteDialog.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    deleteDialog.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // DO SOMETHING HERE
                            Events.remove(getAdapterPosition());
                            notifyItemRemoved(which);
                            notifyDataSetChanged();
                            System.out.println(Events.size());
                        }
                    });

                    deleteDialog.show();
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = getAdapterPosition();
                    final Event e = Events.get(position);
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                    final View content_layout = LayoutInflater.from(mContext).inflate(R.layout.searched_driver_event_detail, null);
                    final View content_passenegr = LayoutInflater.from(mContext).inflate(R.layout.event_passenger_request, null);
                    if(e.getStatus().equals("white")) {
                        TextView tv_1,tv_2,tv_3;
                        tv_1 = content_passenegr.findViewById(R.id.request1);
                        tv_2 = content_passenegr.findViewById(R.id.request2);
                        tv_3 = content_passenegr.findViewById(R.id.request3);
                        tv_1.setText("User1:" );
                        tv_2.setText("User2 " );
                        tv_3.setText("User3 " );
                        tv_1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AlertDialog.Builder requestDialog = new AlertDialog.Builder(mContext);
                                requestDialog.setMessage("1"
//                                        "time: " + e.getAll_request().get(0).getActual_time() + "\n"
//                                        + "start: " + e.getAll_request().get(0).getActual_start_point() +"\n"
//                                        + "end: " + e.getAll_request().get(0).getActual_end_point() +"\n"
//                                        + "another request: " + e.getAll_request().get(0).getExtra_needed() +"\n"
                                );
                                requestDialog.setPositiveButton("拒絕", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                requestDialog.setNegativeButton("確定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // DO SOMETHING HERE
                                        dialog.cancel();
                                    }
                                });
                                requestDialog.show();
                            }
                        });

                        tv_2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AlertDialog.Builder requestDialog = new AlertDialog.Builder(mContext);
                                requestDialog.setMessage("2"
//                                        "time: " + e.getAll_request().get(0).getActual_time() + "\n"
//                                        + "start: " + e.getAll_request().get(0).getActual_start_point() +"\n"
//                                        + "end: " + e.getAll_request().get(0).getActual_end_point() +"\n"
//                                        + "another request: " + e.getAll_request().get(0).getExtra_needed() +"\n"
                                );
                                requestDialog.setPositiveButton("拒絕", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                requestDialog.setNegativeButton("確定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // DO SOMETHING HERE
                                        dialog.cancel();
                                    }
                                });
                                requestDialog.show();
                            }
                        });

                        tv_3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AlertDialog.Builder requestDialog = new AlertDialog.Builder(mContext);
                                requestDialog.setMessage("1"
//                                        "time: " + e.getAll_request().get(0).getActual_time() + "\n"
//                                        + "start: " + e.getAll_request().get(0).getActual_start_point() +"\n"
//                                        + "end: " + e.getAll_request().get(0).getActual_end_point() +"\n"
//                                        + "another request: " + e.getAll_request().get(0).getExtra_needed() +"\n"
                                );
                                requestDialog.setPositiveButton("拒絕", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                requestDialog.setNegativeButton("確定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // DO SOMETHING HERE
                                        dialog.cancel();
                                    }
                                });
                                requestDialog.show();
                            }
                        });
                        alertDialog.setView(content_passenegr);
                    }
                    else if(e.getStatus().equals("green")){
                        TextView foo;
                        foo = content_layout.findViewById(R.id.driver_name);
                        foo.setText(e.getUser().getName());
                        foo = content_layout.findViewById(R.id.driver_sex);
                        foo.setText(e.getUser().isSex() ? "男" : "女");
                        foo = content_layout.findViewById(R.id.acceptable_pt_start);
                        foo = content_layout.findViewById(R.id.acceptable_pt_end);

                        alertDialog.setView(content_layout);
                        alertDialog.setTitle(e.getEvent_name());
                    }
                    alertDialog.show();
                }
            });
        }
    }
}

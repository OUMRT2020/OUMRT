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

        holder.event_name.setText(Events.get(position).getEvent_name());
        holder.event_time.setText(Events.get(position).getMy_request().getActual_time().toString());
        holder.event_cost.setText(Events.get(position).getPrice());
        if (Events.get(position).getStatus().equals("red")) {
            holder.itemView.setBackgroundColor(Color.parseColor("#FF0000"));
        } else if (Events.get(position).getStatus().equals("gray")) {
            holder.itemView.setBackgroundColor(Color.parseColor("#808080"));
        }
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

                    foo = content_layout.findViewById(R.id.tv_3);//顯示乘客的電話
                    foo.setVisibility(View.VISIBLE);
                    foo = content_layout.findViewById(R.id.driver_phone);
                    foo.setVisibility(View.VISIBLE);

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

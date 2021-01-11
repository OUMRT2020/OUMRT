package com.skypan.myapplication.inform;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skypan.myapplication.R;
import com.skypan.myapplication.Retrofit.Inform;

import java.util.ArrayList;

public class informAdapter extends RecyclerView.Adapter<informAdapter.ViewHolder> {


    private ArrayList<Inform.inform_content> informs;
    private Context mContext;

    public informAdapter(Context mContext, ArrayList<Inform.inform_content> informs) {
        this.mContext = mContext;
        this.informs = informs;
    }

    @NonNull
    @Override
    public informAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.inform_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull informAdapter.ViewHolder holder, int position) {
        holder.inform_event_name.setText(informs.get(position).getEvent_name());
        holder.inform_detail.setText(informs.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return informs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView inform_event_name;
        private TextView inform_detail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            inform_event_name = itemView.findViewById(R.id.inform_event_name);
            inform_detail = itemView.findViewById(R.id.inform_detail);

        }
    }
}

package com.skypan.myapplication.driver_model.ui.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skypan.myapplication.R;
import com.skypan.myapplication.Retrofit.Ack;
import com.skypan.myapplication.Retrofit.Request;
import com.skypan.myapplication.Retrofit.RetrofitManagerAPI;
import com.skypan.myapplication.Retrofit.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class requests_in_multiRecyclerViewAdapter extends RecyclerView.Adapter<requests_in_multiRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Request> all_request;
    private ArrayList<User> all_request_user;
    private Activity mActivity;

    public requests_in_multiRecyclerViewAdapter(Activity mActivity, Context mContext, ArrayList<Request> all_request, ArrayList<User> all_request_user) {
        this.mContext = mContext;
        this.all_request = all_request;
        this.all_request_user = all_request_user;
        this.mActivity = mActivity;
    }

    @NonNull
    @Override
    public requests_in_multiRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.user_requests_recyclerview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull requests_in_multiRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.user_name.setText(all_request_user.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return all_request_user.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView user_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.requests_userName);
            user_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = getAdapterPosition();
                    final Request request = all_request.get(position);
                    final User user = all_request_user.get(position);
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                    final View content_layout = LayoutInflater.from(mContext).inflate(R.layout.passenger_main_event_detail, null);

                    TextView foo;
                    foo = content_layout.findViewById(R.id.tv_1);
                    foo.setText("乘客姓名");
                    foo = content_layout.findViewById(R.id.driver_name);//直接用乘客主畫面的layout了 id不想改
                    foo.setText(user.getName());

                    foo = content_layout.findViewById(R.id.tv_2);
                    foo.setText("乘客性別");
                    foo = content_layout.findViewById(R.id.driver_sex);
                    foo.setText(user.isSex() ? "男" : "女");

                    //tv3是電話
                    foo = content_layout.findViewById(R.id.actual_start_point);
                    foo.setText(request.getActual_start_point());

                    foo = content_layout.findViewById(R.id.actual_end_point);
                    foo.setText(request.getActual_end_point());

                    foo = content_layout.findViewById(R.id.actual_time);
                    foo.setText(request.getActual_time());

                    //把車照隱藏
                    foo = content_layout.findViewById(R.id.tv_7);
                    foo.setVisibility(View.GONE);
                    ImageView imageView;
                    imageView = content_layout.findViewById(R.id.picture);
                    imageView.setVisibility(View.GONE);

                    foo = content_layout.findViewById(R.id.tv_extra_need);
                    foo.setText(request.getExtra_needed());

                    alertDialog.setTitle("請求詳細資訊");
                    alertDialog.setPositiveButton("接受", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //todo :連線API
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl("http://140.121.197.130:5602/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            RetrofitManagerAPI retrofitManagerAPI = retrofit.create(RetrofitManagerAPI.class);
                            Call<Ack> call = retrofitManagerAPI.acceptRequest(user.getUser_id(), request.getEvent_id());
                            call.enqueue(new Callback<Ack>() {
                                @Override
                                public void onResponse(Call<Ack> call, Response<Ack> response) {
                                    if (!response.isSuccessful()) {
                                        Log.d("accept error", response.message());
                                        Toast.makeText(mContext, "accept error", Toast.LENGTH_SHORT).show();
                                    }
                                    if (!response.body().isSuccess()) {
                                        Log.d("accept fail", response.body().getReason());
                                        Toast.makeText(mContext, "accept fail", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.d("accept success", "accept success");
                                        Toast.makeText(mContext, "accept success", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Ack> call, Throwable t) {
                                    Log.d("accept server error", t.getMessage());
                                }
                            });
                            mActivity.recreate();
                        }
                    });
                    alertDialog.setNegativeButton("拒絕", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            AlertDialog.Builder aaa = new AlertDialog.Builder(mContext);

                            final View content_layout = LayoutInflater.from(mContext).inflate(R.layout.reject, null);
                            EditText editText = content_layout.findViewById(R.id.reject);
                            aaa.setTitle("可輸入拒絕原因");
                            aaa.setPositiveButton("確定拒絕", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    Retrofit retrofit = new Retrofit.Builder()
                                            .baseUrl("http://140.121.197.130:5602/")
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();
                                    RetrofitManagerAPI retrofitManagerAPI = retrofit.create(RetrofitManagerAPI.class);
                                    String Reject = String.valueOf(editText.getText());
                                    Call<Ack> call = retrofitManagerAPI.rejectRequest(user.getUser_id(), request.getEvent_id(), Reject);
                                    call.enqueue(new Callback<Ack>() {
                                        @Override
                                        public void onResponse(Call<Ack> call, Response<Ack> response) {
                                            if (!response.isSuccessful()) {
                                                Log.d("reject error", response.message());
                                                Toast.makeText(mContext, "reject error", Toast.LENGTH_SHORT).show();

                                            }
                                            if (!response.body().isSuccess()) {
                                                Log.d("reject fail", response.body().getReason());
                                                Toast.makeText(mContext, "reject fail", Toast.LENGTH_SHORT).show();

                                            } else {
                                                Log.d("reject success", "reject success");
                                                Toast.makeText(mContext, "reject success", Toast.LENGTH_SHORT).show();

                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Ack> call, Throwable t) {
                                            Log.d("reject server error", t.getMessage());
                                            Toast.makeText(mContext, "reject server error", Toast.LENGTH_SHORT).show();

                                        }
                                    });
                                    mActivity.recreate();
                                }
                            });
                            aaa.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });

                            aaa.setView(content_layout);
                            aaa.show();

                        }
                    });

                    alertDialog.setView(content_layout);
                    alertDialog.show();
                }
            });
        }
    }
}
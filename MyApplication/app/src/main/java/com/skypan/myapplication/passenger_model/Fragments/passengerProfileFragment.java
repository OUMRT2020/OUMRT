package com.skypan.myapplication.passenger_model.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.skypan.myapplication.R;
import com.skypan.myapplication.Retrofit.Ack;
import com.skypan.myapplication.Retrofit.ImageResponse;
import com.skypan.myapplication.Retrofit.RetrofitManagerAPI;
import com.skypan.myapplication.Retrofit.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class passengerProfileFragment extends Fragment {
    private EditText et_nickName, et_phone, et_weight, et_mail, et_rate;
    private RadioButton sex_male, sex_female;
    private Button btn_edit, btn_cancel, btn_upload_img;
    private ImageView iv;
    private Uri selectedImage;
    private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    private Bitmap bitmap;
    private String encoded, img_url;
    private Activity mActivity;
    private SharedPreferences sharedPreferences;
    private boolean isPicAlter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_personal_information, container, false);
        et_nickName = view.findViewById(R.id.nickname);
        et_phone = view.findViewById(R.id.phone);
        et_weight = view.findViewById(R.id.weight);
        et_mail = view.findViewById(R.id.mail);
        et_rate = view.findViewById(R.id.et_rate);
        sex_male = view.findViewById(R.id.gender_man);
        sex_female = view.findViewById(R.id.gender_woman);
        btn_edit = view.findViewById(R.id.edit_information_button);
        btn_cancel = view.findViewById(R.id.cancel_information_button);
        btn_upload_img = view.findViewById(R.id.btn_upload_img);
        iv = view.findViewById(R.id.car_pic);

        //init
        mActivity = getActivity();
        isPicAlter = false;
        sharedPreferences = mActivity.getSharedPreferences("isOUMRTLogin", Context.MODE_PRIVATE);
        img_url = sharedPreferences.getString("car_pic_url", "");
        et_nickName.setText(sharedPreferences.getString("name", ""));
        et_phone.setText(sharedPreferences.getString("phone_num", ""));
        et_weight.setText("" + sharedPreferences.getInt("weight", 48763));
        et_mail.setText(sharedPreferences.getString("email", ""));
        et_rate.setText("" + sharedPreferences.getFloat("rate", (float) -1.0));
        if (sharedPreferences.getString("sex", "").equals("男")) {
            sex_male.setChecked(true);
        } else {
            sex_female.setChecked(true);
        }
        //todo :下載照片
        if (img_url.equals("")) {
            Glide.with(this).load("https://2.bp.blogspot.com/-Ado6ei4W5YU/WY-RnHsRzzI/AAAAAAABoXA/p0AEw7GIMaUgK_-pyrwH4pwBbwGyKRaowCEwYBhgL/s640/70533052.jpg").into(iv);
        } else {
            Glide.with(this).load(img_url).into(iv);
        }

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_edit.getText().toString().equals("Edit")) {
                    et_nickName.setEnabled(true);
                    et_phone.setEnabled(true);
                    et_weight.setEnabled(true);
                    btn_edit.setText("Save");
                    btn_cancel.setVisibility(View.VISIBLE);
                    btn_upload_img.setVisibility(View.VISIBLE);

                } else {//click save
                    //沒修改
                    if (et_nickName.getText().toString().equals(sharedPreferences.getString("name", ""))
                            && et_phone.getText().toString().equals(sharedPreferences.getString("phone_num", ""))
                            && et_weight.getText().toString().equals("" + sharedPreferences.getInt("weight", 48763))
                            && !isPicAlter) {
                        Toast.makeText(mActivity, "沒有任何修改", Toast.LENGTH_SHORT).show();
                    } else {//有修改
                        //如果照片有修改
                        if (isPicAlter) {
                            // callImgurAPI()會自己callAlterUserAPI()
                            Glide.with(mActivity).load("https://www.professionalservicesllc.com/clients/stoneledge/images/loaders/uploading.gif").into(iv);
                            callImgurAPI();
                        } else {
                            callAlterUserAPI();
                        }
                    }
                    et_nickName.setEnabled(false);
                    et_phone.setEnabled(false);
                    et_weight.setEnabled(false);
                    btn_edit.setText("Edit");
                    btn_cancel.setVisibility(View.GONE);
                    btn_upload_img.setVisibility(View.GONE);
                }

            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_nickName.setEnabled(false);
                et_phone.setEnabled(false);
                et_weight.setEnabled(false);
                btn_edit.setText("Edit");
                btn_cancel.setVisibility(View.GONE);
                btn_upload_img.setVisibility(View.GONE);
//                Toast.makeText(mActivity, "isLogin: " + (sharedPreferences.getBoolean("isLogin", false) ? "true" : "false")
//                        + "\n" + "email: " + sharedPreferences.getString("email", "沒有")
//                        + "\n" + "password: " + sharedPreferences.getString("password", "沒有")
//                        + "\n" + "user_id: " + sharedPreferences.getString("user_id", "沒有")
//                        + "\n" + "name: " + sharedPreferences.getString("name", "沒有")
//                        + "\n" + "phone_num: " + sharedPreferences.getString("phone_num", "沒有")
//                        + "\n" + "sex: " + sharedPreferences.getString("sex", "沒有")
//                        + "\n" + "weight: " + "" + sharedPreferences.getInt("weight", -87)
//                        + "\n" + "rate: " + "" + sharedPreferences.getFloat("rate", (float) 8.7)
//                        + "\n" + "car_pic_url: " + "" + sharedPreferences.getString("car_pic_url", "沒有"), Toast.LENGTH_LONG).show();
            }
        });
        btn_upload_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == getActivity().RESULT_OK && data != null) {
            isPicAlter = true;
            selectedImage = data.getData();
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(mActivity.getContentResolver(), selectedImage);
                iv.setImageBitmap(bitmap);
            } catch (IOException e) {
                Toast.makeText(mActivity, "oh, 找不到相片, 真糟糕", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    private void callImgurAPI() {
        try {
            bitmap = MediaStore.Images.Media.getBitmap(mActivity.getContentResolver(), selectedImage);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            Retrofit uploadImg_retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.imgur.com/3/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RetrofitManagerAPI ImgurUploadAPI = uploadImg_retrofit.create(RetrofitManagerAPI.class);
            Call<ImageResponse> call = ImgurUploadAPI.postImg(encoded);
            call.enqueue(new Callback<ImageResponse>() {
                @Override
                public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(mActivity, "error1: " + response.message(), Toast.LENGTH_SHORT).show();
                    } else {
                        img_url = response.body().data.link;
                        iv.setImageBitmap(bitmap);
                        callAlterUserAPI();
                    }
                }

                @Override
                public void onFailure(Call<ImageResponse> call, Throwable t) {
                    Toast.makeText(mActivity, "Imgur server error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Glide.with(mActivity).load(img_url).into(iv);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void callAlterUserAPI() {
        User user = new User("", et_nickName.getText().toString(), et_phone.getText().toString(), false, Integer.parseInt(et_weight.getText().toString()));
        user.setUser_id(sharedPreferences.getString("user_id", "拿preference時沒找到"));
        user.setPicture_url(img_url);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://140.121.197.130:5602/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitManagerAPI retrofitManagerAPI = retrofit.create(RetrofitManagerAPI.class);
        Call<Ack> call2 = retrofitManagerAPI.alterUser(user);
        call2.enqueue(new Callback<Ack>() {
            @Override
            public void onResponse(Call<Ack> call, Response<Ack> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mActivity, "error2: " + response.message(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mActivity, "成功修改", Toast.LENGTH_SHORT).show();
                    sharedPreferences.edit()
                            .putString("name", et_nickName.getText().toString())
                            .putString("phone_num", et_phone.getText().toString())
                            .putInt("weight", Integer.parseInt(et_weight.getText().toString()))
                            .putString("car_pic_url", img_url)
                            .apply();
                }
            }

            @Override
            public void onFailure(Call<Ack> call, Throwable t) {
                Toast.makeText(mActivity, "server error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
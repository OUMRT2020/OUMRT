package com.skypan.myapplication.passenger_model.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.fragment.app.Fragment;

import com.skypan.myapplication.R;


public class passengerProfileFragment extends Fragment {
    private EditText et_nickName, et_phone, et_weight, et_mail;
    private RadioButton sex_male, sex_female;
    private Button btn_edit, btn_cancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_personal_information, container, false);
        et_nickName = view.findViewById(R.id.nickname);
        et_phone = view.findViewById(R.id.phone);
        et_weight = view.findViewById(R.id.weight);
        et_mail = view.findViewById(R.id.mail);
        sex_male = view.findViewById(R.id.gender_man);
        sex_female = view.findViewById(R.id.gender_woman);
        btn_edit = view.findViewById(R.id.edit_information_button);
        btn_cancel = view.findViewById(R.id.cancel_information_button);
        Activity mActivity = getActivity();
        assert mActivity != null;
        SharedPreferences sharedPreferences = mActivity.getSharedPreferences("isOUMRTLogin", Context.MODE_PRIVATE);
        et_nickName.setText(sharedPreferences.getString("name", ""));
        et_phone.setText(sharedPreferences.getString("phone_num", ""));
        et_weight.setText(""+sharedPreferences.getInt("weight", 48763));
        et_mail.setText(sharedPreferences.getString("email", ""));
        if(sharedPreferences.getString("sex", "").equals("男")){
            sex_male.setChecked(true);
        }
        else{
            sex_female.setChecked(true);
        }
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_edit.getText().toString().equals("Edit")) {
                    et_nickName.setEnabled(true);
                    et_phone.setEnabled(true);
                    et_weight.setEnabled(true);
                    et_mail.setEnabled(true);
                    sex_male.setEnabled(true);
                    sex_female.setEnabled(true);
                    btn_edit.setText("Save");
                    btn_cancel.setVisibility(View.VISIBLE);
                    //todo: Call API
                } else {
                    et_nickName.setEnabled(false);
                    et_phone.setEnabled(false);
                    et_weight.setEnabled(false);
                    et_mail.setEnabled(false);
                    sex_male.setEnabled(false);
                    sex_female.setEnabled(false);
                    btn_edit.setText("Edit");
                    btn_cancel.setVisibility(View.GONE);
                }

            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_nickName.setEnabled(false);
                et_phone.setEnabled(false);
                et_weight.setEnabled(false);
                et_mail.setEnabled(false);
                sex_male.setEnabled(false);
                sex_female.setEnabled(false);
                btn_edit.setText("Edit");
                btn_cancel.setVisibility(View.GONE);
            }
        });

        return view;
    }

}
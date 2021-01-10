package com.skypan.myapplication.passenger_model.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.skypan.myapplication.R;
import com.skypan.myapplication.driver_model.DriverMainActivity;
import com.skypan.myapplication.passenger_model.PassengerMainActivity;

public class SwitchToDriverFragment extends Fragment {
    private Button btn_switch_to_driver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_switch_to_driver, container, false);
        btn_switch_to_driver = view.findViewById(R.id.btn_switch_to_driver);
        btn_switch_to_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("確認視窗")
                        .setMessage("確定切換身分?")
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getActivity(), DriverMainActivity.class);
                                intent.putExtra("user_id", ((PassengerMainActivity) getActivity()).user_id);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
            }
        });
        return view;
    }
}
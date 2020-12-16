package com.skypan.myapplication.driver_model.ui.Switch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.common.data.BitmapTeleporter;
import com.skypan.myapplication.R;
import com.skypan.myapplication.driver_model.DriverMainActivity;
import com.skypan.myapplication.passenger_model.PassengerMainActivity;

public class SwitchFragment extends Fragment {

    private Button btn_switch_to_passenger;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_switch, container, false);
        btn_switch_to_passenger = root.findViewById(R.id.btn_switch_to_passenger);
        btn_switch_to_passenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("確認視窗")
                        .setMessage("確定切換身分?")
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getActivity(), PassengerMainActivity.class);
                                intent.putExtra("user_id", ((DriverMainActivity) getActivity()).user_id);
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
        return root;
    }
}
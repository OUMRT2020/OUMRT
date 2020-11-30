package com.skypan.myapplication.driver_model.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.skypan.myapplication.R;
import com.skypan.myapplication.driver_model.addSetting;

import static android.app.ProgressDialog.show;
import static com.skypan.myapplication.driver_model.addSetting.Set;

public class HomeFragment extends Fragment {


    private HomeViewModel homeViewModel;
    private TextView Ed_1;
    private Button refresh;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        //這邊可直接插入一般教學中的activity，不用使用fragment
        View root = inflater.inflate(R.layout.activity_text, container, false);
        Ed_1 = root.findViewById(R.id.et_content1);
        if (Set.size() > 0) {
            Ed_1.setText(addSetting.getSetting(0));
        }
        return root;
    }

}
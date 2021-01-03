package com.skypan.myapplication.personal_information_model;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.skypan.myapplication.R;

public class edit_personal_informationActivity extends AppCompatActivity {
    private Button save_edit_information_button;
    private Button edit_password_information_button;
    private Button return_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_personal_information);

        // find id
        save_edit_information_button.findViewById(R.id.save_edit_information_button);
        edit_password_information_button.findViewById(R.id.edit_password_information_button);
        return_button.findViewById(R.id.return_button);

        //監聽器
        setListeners();
    }

    // 監聽器
    private void setListeners() {
        edit_personal_informationActivity.OnClick onClick = new edit_personal_informationActivity.OnClick();
        return_button.setOnClickListener(onClick);
    }

    // when click ...
    private class OnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.save_edit_information_button:
                // 跳轉到個人資料介面介面
                Intent intent0=new Intent(edit_personal_informationActivity.this, personal_informationActivity.class);
                startActivity(intent0);

                    break;
                //case R.id.edit_password_information_button:
                // 跳轉到認證密碼介面
                //intent=new Intent(edit_personal_informationActivity.this, );
                    //break;
                case R.id.return_button:
                    // 跳轉到個人資料介面介面
                    Intent intent1 = new Intent(edit_personal_informationActivity.this, personal_informationActivity.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                    break;
            }

        }
    }
}
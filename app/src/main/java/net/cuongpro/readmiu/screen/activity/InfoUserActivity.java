package net.cuongpro.readmiu.screen.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import net.cuongpro.readmiu.R;

public class InfoUserActivity extends AppCompatActivity {
    ImageView back, imgAvata ;
    EditText edFullname, edEmail, edPhone;
    Button btnDangKy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_user);
        anhXa();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void anhXa() {
        back = findViewById(R.id.img_backReg);
        edFullname = findViewById(R.id.ed_fullname);
        edEmail = findViewById(R.id.ed_email);
        edPhone = findViewById(R.id.ed_phone);
        imgAvata = findViewById(R.id.img_Avata);
        btnDangKy = findViewById(R.id.btn_dangky);
    }
}
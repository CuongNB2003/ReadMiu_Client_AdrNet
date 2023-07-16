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
    ImageView back;
    EditText fullname, email, phone;
    Button btnDangKy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_user);
        anhXa();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InfoUserActivity.this, RegActivity.class);
                startActivity(intent);
            }
        });
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void anhXa() {
        back = findViewById(R.id.img_back);
        fullname = findViewById(R.id.ed_fullname);
        email = findViewById(R.id.ed_email);
        phone = findViewById(R.id.ed_phone);
        btnDangKy = findViewById(R.id.btn_dangky);
    }
}
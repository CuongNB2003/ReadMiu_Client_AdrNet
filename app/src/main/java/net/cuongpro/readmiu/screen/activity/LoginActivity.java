package net.cuongpro.readmiu.screen.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.cuongpro.readmiu.R;

public class LoginActivity extends AppCompatActivity {
    EditText taikhoan, matkhau;
    Button btnDangNhap, btnDangKy;
    TextView edQuenMK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhXa();
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegActivity.class);
                startActivity(intent);
            }
        });
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadDangNhap();
            }
        });
        edQuenMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, PassRetriActivity.class);
                startActivity(intent);
            }
        });
    }

    private void LoadDangNhap() {

    }

    private void anhXa() {
        taikhoan = findViewById(R.id.ed_UserName);
        matkhau = findViewById(R.id.ed_PassWord);
        btnDangNhap = findViewById(R.id.btn_login);
        btnDangKy = findViewById(R.id.btn_re);
        edQuenMK = findViewById(R.id.tv_resetPass);
    }
}
package net.cuongpro.readmiu.screen.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import net.cuongpro.readmiu.R;

public class PassRetriActivity extends AppCompatActivity {
    private EditText retriUsername, retriPass, retriRePass, retriEmail;
    private Button btnLayLaiTK;
    private ImageView img_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_retri);
        anhXa();
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnLayLaiTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PassRetriActivity.this, "Lấy tài khoản thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void anhXa() {
        retriEmail = findViewById(R.id.ed_retriEmail);
        retriPass = findViewById(R.id.ed_retriPass);
        retriUsername = findViewById(R.id.ed_retriUsername);
        retriRePass = findViewById(R.id.ed_RetriRePass);
        btnLayLaiTK = findViewById(R.id.btn_layMatKhau);
        img_back = findViewById(R.id.img_backRetri);
    }
}
package net.cuongpro.readmiu.screen.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import net.cuongpro.readmiu.R;

public class RegActivity extends AppCompatActivity {
    Button btnNext;
    EditText username, pass, rePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        anhXa();
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void anhXa() {
        btnNext = findViewById(R.id.btn_next);
        username = findViewById(R.id.ed_username);
        pass = findViewById(R.id.ed_password);
        rePass = findViewById(R.id.ed_rePassword);
    }
}
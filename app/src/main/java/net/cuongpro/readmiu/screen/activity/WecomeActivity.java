package net.cuongpro.readmiu.screen.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import net.cuongpro.readmiu.R;

public class WecomeActivity extends AppCompatActivity {
    LinearLayout manChao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wecome);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WecomeActivity.this, MainActivity.class));
                finish();
            }
        }, 1000);
    }
}
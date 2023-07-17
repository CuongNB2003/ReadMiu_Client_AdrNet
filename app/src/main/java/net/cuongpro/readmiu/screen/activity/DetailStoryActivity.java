package net.cuongpro.readmiu.screen.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.cuongpro.readmiu.R;

public class DetailStoryActivity extends AppCompatActivity {
    private ImageView imgBack, imgComic;
    private TextView tenTruyen, tenTacGia, namXuatBan, moTa;
    private EditText ed_comment;
    private Button btnDocTruyen, btnComment;
    private RecyclerView viewComment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_story);
        anhXa();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnDocTruyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailStoryActivity.this, ReadStoryActivity.class);
                startActivity(intent);
            }
        });
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailStoryActivity.this, "Thêm bình luận thanh công", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void anhXa() {
        imgBack = findViewById(R.id.img_backDetal);
        imgComic = findViewById(R.id.img_anhBia);
        tenTruyen = findViewById(R.id.tv_tenTruyen);
        tenTacGia = findViewById(R.id.tv_tenTacGia);
        namXuatBan = findViewById(R.id.tv_namXuatBan);
        moTa = findViewById(R.id.tv_mota);
        btnDocTruyen = findViewById(R.id.btn_read);
        ed_comment = findViewById(R.id.ed_comment);
        btnComment = findViewById(R.id.btn_comment);
        viewComment = findViewById(R.id.recy_comment);
    }
}
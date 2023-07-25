package net.cuongpro.readmiu.screen.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import net.cuongpro.readmiu.R;
import net.cuongpro.readmiu.api.ApiService;
import net.cuongpro.readmiu.api.LinkApi;
import net.cuongpro.readmiu.model.model_api.GetComicOne;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailStoryActivity extends AppCompatActivity {
    private ImageView imgBack, imgComic, imgFavorite;
    private TextView tenTruyen, tenTacGia, namXuatBan, moTa, tvTieuDe;
    private EditText ed_comment;
    private Button btnDocTruyen, btnComment;
    private RecyclerView viewComment;
    private GetComicOne comicOne;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_story);
        anhXa();
        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("idComic");
        getComicOne(id);
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
        imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgFavorite.setImageResource(R.drawable.ic_favorite_true);
            }
        });
//        btnComment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(DetailStoryActivity.this, "Thêm bình luận thanh công", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void getComicOne(String id) {
        ApiService.apiService.getComic(id).enqueue(new Callback<GetComicOne>() {
            @Override
            public void onResponse(Call<GetComicOne> call, Response<GetComicOne> response) {
                if(response.isSuccessful()){
                    comicOne = response.body();
                    Toast.makeText(DetailStoryActivity.this, ""+comicOne.getMsg(), Toast.LENGTH_SHORT).show();
                    // lưu ảnh vào bộ nhớ
                    RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
                    // sử dụng thư viện để load ảnh từ server
                    Glide.with(getApplication()).load(LinkApi.linkUrl +comicOne.getComic().getAnhBia())
                            .apply(requestOptions)
                            .error(R.drawable.img_err)
                            .into(imgComic);
                    tenTruyen.setText(""+comicOne.getComic().getTenChuyen());
                    tvTieuDe.setText(""+comicOne.getComic().getTenChuyen());
                    tenTacGia.setText(""+comicOne.getComic().getTenTacGia());
                    namXuatBan.setText(""+comicOne.getComic().getNamXuatBan());
                    moTa.setText(""+comicOne.getComic().getMotaChuyen());
                }
            }

            @Override
            public void onFailure(Call<GetComicOne> call, Throwable t) {

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
//        ed_comment = findViewById(R.id.ed_comment);
//        btnComment = findViewById(R.id.btn_comment);
        viewComment = findViewById(R.id.recy_comment);
        tvTieuDe = findViewById(R.id.tv_TenTruyen);
        imgFavorite = findViewById(R.id.img_Favorite);
    }
}
package net.cuongpro.readmiu.screen.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.cuongpro.readmiu.R;
import net.cuongpro.readmiu.adapter.AdapterReadComic;
import net.cuongpro.readmiu.api.ApiService;
import net.cuongpro.readmiu.api.LinkApi;
import net.cuongpro.readmiu.model.model_api.GetListPhoto;
import net.cuongpro.readmiu.model.model_api.PhotoList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadStoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView tenTruyen;
    private ImageView imgBack;
    private AdapterReadComic adapterComic;
    private String[] photos;
    private List<PhotoList> listPhoto = new ArrayList<>();
    private GetListPhoto getListPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_story);
        initUi();
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("nameComic");
        String idComic = bundle.getString("idComic");
        tenTruyen.setText(""+name);

//        getListPhoto(idComic);

        //get theo bundle hơi lâu get theo id còn lâu nữa hmmm
        photos = bundle.getStringArray("listPhoto");
        Log.d(LinkApi.TAG, "onCreate: img nd "+photos.length);
        for(int i = 0; i < photos.length; i++){
            listPhoto.add(new PhotoList(photos[i]));
            Log.d(LinkApi.TAG, "onCreate: list ảnh "+listPhoto);
        }
        loadData(listPhoto);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void getListPhoto(String id) {
        ApiService.apiService.getListPhoto(id).enqueue(new Callback<GetListPhoto>() {
            @Override
            public void onResponse(Call<GetListPhoto> call, Response<GetListPhoto> response) {
                if(response.isSuccessful()){
                    getListPhoto = response.body();
                    Log.d(LinkApi.TAG, ""+getListPhoto.getMsg());
//                    Log.d(LinkApi.TAG, "onCreate: list ảnh sum "+photos.length);

                    photos = getListPhoto.getPhotoLists();

                    for(int i = 0; i < photos.length; i++){
                        listPhoto.add(new PhotoList(photos[i]));
                        Log.d(LinkApi.TAG, "onCreate: list ảnh "+listPhoto);
                    }
                    Log.d(LinkApi.TAG, "onCreate: list ảnh sum2 "+photos.length);

                    loadData(listPhoto);

                }
            }

            @Override
            public void onFailure(Call<GetListPhoto> call, Throwable t) {
                Toast.makeText(ReadStoryActivity.this, "Load list photo fasle", Toast.LENGTH_SHORT).show();
                Log.d(LinkApi.TAG, "Load List Photo "+t.getLocalizedMessage());
            }
        });
    }

    private void initUi() {
        recyclerView = findViewById(R.id.recy_read);
        tenTruyen = findViewById(R.id.tvTenTruyen);
        imgBack = findViewById(R.id.img_backRead);
    }

    private void loadData(List<PhotoList> listPhoto) {
        adapterComic = new AdapterReadComic(ReadStoryActivity.this);
        adapterComic.setListPhoto(listPhoto);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterComic);
    }
}
package net.cuongpro.readmiu.screen.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import net.cuongpro.readmiu.R;
import net.cuongpro.readmiu.adapter.AdapterComic;
import net.cuongpro.readmiu.api.ApiService;
import net.cuongpro.readmiu.api.LinkApi;
import net.cuongpro.readmiu.model.Comic;
import net.cuongpro.readmiu.model.model_api.GetComic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    private SearchView searchName;
    private RecyclerView recySearch;
    private TextView tvHuy;
    private ProgressDialog progressDialog;
    private GetComic getComic;
    private List<Comic> lisComic;
    private AdapterComic adapterRecyComic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initUi();
        getListComic();
        tvHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });
        searchName.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filerList(newText);
                return true;
            }
        });
    }

    private void filerList(String newText) {
        ArrayList<Comic> filteredList = new ArrayList<>();
        for (Comic comic : lisComic){
            if (comic.getTenChuyen().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(comic);
            }
        }
        if (!filteredList.isEmpty()){
            adapterRecyComic.setListComic(filteredList);
        }else {
            filteredList.clear();
            adapterRecyComic.setListComic(filteredList);
        }
    }
    private void getListComic() {
        progressDialog.show();
        ApiService.apiService.getAllComic().enqueue(new Callback<GetComic>() {
            @Override
            public void onResponse(Call<GetComic> call, Response<GetComic> response) {
                if(response.isSuccessful()){
                    getComic = response.body();
                    lisComic = Arrays.asList(getComic.getComics());
                    setLayoutComic(lisComic);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<GetComic> call, Throwable t) {
                Log.d(LinkApi.TAG, "Load data comic thất bại"+t.getLocalizedMessage());
                progressDialog.dismiss();
            }
        });
    }
    private void setLayoutComic(List<Comic> lisComic) {
        adapterRecyComic = new AdapterComic(SearchActivity.this);
        adapterRecyComic.setListComic(lisComic);
        GridLayoutManager layoutManager = new GridLayoutManager(SearchActivity.this, 2);
        recySearch.setLayoutManager(layoutManager);
        recySearch.setAdapter(adapterRecyComic);
    }
    private void initUi() {
        progressDialog = new ProgressDialog(SearchActivity.this);
        searchName = findViewById(R.id.SearchName);
        recySearch = findViewById(R.id.recy_search);
        tvHuy = findViewById(R.id.tv_Huy);
    }
}
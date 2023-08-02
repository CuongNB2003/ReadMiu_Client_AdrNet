package net.cuongpro.readmiu.screen.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import net.cuongpro.readmiu.R;
import net.cuongpro.readmiu.adapter.AdapterComic;
import net.cuongpro.readmiu.adapter.AdapterSlides;
import net.cuongpro.readmiu.api.ApiService;
import net.cuongpro.readmiu.api.LinkApi;
import net.cuongpro.readmiu.model.Comic;
import net.cuongpro.readmiu.model.model_api.GetComic;
import net.cuongpro.readmiu.model.slide.DepthPageTransformer;
import net.cuongpro.readmiu.model.slide.Photo;
import net.cuongpro.readmiu.model.slide.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import me.relex.circleindicator.CircleIndicator3;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private ImageView timkiem;
    private ViewPager2 viewPager2;
    private CircleIndicator3 chuyenSlide;
    private List<Comic> lisComic;
    private List<Comic> listSlide;
    private GetComic getComic;
    private AdapterComic adapterRecyComic;
    private AdapterSlides adapterSlides;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private ProgressDialog loading;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                if(viewPager2.getCurrentItem() == lisComic.size() -1){
                    viewPager2.setCurrentItem(0);
                }else {
                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                }
            }catch (NullPointerException e){
            }
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        initUi(view);
        getListComic();
        getListPhoto();
        timkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Chức năng tìm kiếm", Toast.LENGTH_SHORT).show();
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
                getListComic();
                getListPhoto();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void getListPhoto() {
        loading.show();
        ApiService.apiService.getListComic(3, 3).enqueue(new Callback<GetComic>() {
            @Override
            public void onResponse(Call<GetComic> call, Response<GetComic> response) {
                if(response.isSuccessful()){
                    getComic = response.body();
//                    Toast.makeText(getContext(), ""+getComic.getMsg(), Toast.LENGTH_SHORT).show();
//                    Log.d(LinkApi.TAG, "Load dữ liệu list comic thành công: "+ getComic.getMsg());

                    try {
                        listSlide = Arrays.asList(getComic.getComics());
                    }catch (NullPointerException e){

                    }
                    setLayoutPhoto(listSlide);
                    loading.dismiss();
                }
            }

            @Override
            public void onFailure(Call<GetComic> call, Throwable t) {
                try {
                    Log.d(LinkApi.TAG, "Load data comic thất bại"+t.getLocalizedMessage());
                    Toast.makeText(getContext(), "Hãy kết nối với mạng", Toast.LENGTH_SHORT).show();
                }catch (NullPointerException e){
                    Log.d(LinkApi.TAG, "NullPointerException"+e.getMessage());
                }
                loading.dismiss();
            }
        });
    }
    private void setLayoutPhoto(List<Comic> listSlide) {
        adapterSlides = new AdapterSlides(getContext(), listSlide);
        viewPager2.setAdapter(adapterSlides);
        chuyenSlide.setViewPager(viewPager2);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 5000);
            }
        });
        viewPager2.setPageTransformer(new ZoomOutPageTransformer());
    }

    private void getListComic() {
        loading.show();
        ApiService.apiService.getListComic(1, 10).enqueue(new Callback<GetComic>() {
            @Override
            public void onResponse(Call<GetComic> call, Response<GetComic> response) {
                if(response.isSuccessful()){
                    getComic = response.body();
//                    Toast.makeText(getContext(), ""+getComic.getMsg(), Toast.LENGTH_SHORT).show();
//                    Log.d(LinkApi.TAG, "Load dữ liệu list comic thành công: "+ getComic.getMsg());
                    try {
                        lisComic = Arrays.asList(getComic.getComics());
                    }catch (NullPointerException e){

                    }
                    setLayoutComic(lisComic);
                    loading.dismiss();
                }
            }

            @Override
            public void onFailure(Call<GetComic> call, Throwable t) {
                try {
                    Log.d(LinkApi.TAG, "Load data comic thất bại"+t.getLocalizedMessage());
                    Toast.makeText(getContext(), "Hãy kết nối với mạng", Toast.LENGTH_SHORT).show();
                }catch (NullPointerException e){
                    Log.d(LinkApi.TAG, "NullPointerException"+e.getMessage());
                }
                loading.dismiss();
            }
        });
    }

    private void setLayoutComic(List<Comic> lisComic) {
        adapterRecyComic = new AdapterComic(getContext());
        adapterRecyComic.setListComic(lisComic);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterRecyComic);
    }

    private void initUi(View view) {
        loading = new ProgressDialog(getContext());
        loading.setMessage("Đang load truyện...");

        timkiem = view.findViewById(R.id.tv_timkiem);
        viewPager2 = view.findViewById(R.id.id_viewSilde);
        chuyenSlide = view.findViewById(R.id.id_chuyensilde);
        recyclerView = view.findViewById(R.id.recy_comic);
        refreshLayout = view.findViewById(R.id.refreshLayout);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 5000);
    }
}
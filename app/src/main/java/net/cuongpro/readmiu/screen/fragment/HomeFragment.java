package net.cuongpro.readmiu.screen.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import net.cuongpro.readmiu.R;
import net.cuongpro.readmiu.adapter.AdapterComic;
import net.cuongpro.readmiu.adapter.AdapterSlides;
import net.cuongpro.readmiu.model.Comic;
import net.cuongpro.readmiu.model.DepthPageTransformer;
import net.cuongpro.readmiu.model.Slides;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator3;

public class HomeFragment extends Fragment {
    private ImageView timkiem;
    private ViewPager2 vp_slide;
    private CircleIndicator3 chuyenSlide;
    private ArrayList<Comic> lisComic = new ArrayList<>();
    private ArrayList<Slides> listSlide = new ArrayList<>();
    private AdapterComic adapterRecyComic;
    private AdapterSlides adapterSlides;
    private RecyclerView recyclerView;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (vp_slide.getCurrentItem() == listSlide.size() - 1) {
                vp_slide.setCurrentItem(0);
            } else {
                vp_slide.setCurrentItem(vp_slide.getCurrentItem() + 1);
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
        anhXa(view);
        LoadSlide();
        LoadItemComic();
        timkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Chức năng tìm kiếm", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    private void LoadItemComic() {
        lisComic = getListComic();
        adapterRecyComic = new AdapterComic(getActivity());
        adapterRecyComic.setListComic(lisComic);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterRecyComic);
    }

    private ArrayList<Comic> getListComic() {
        ArrayList<Comic> list = new ArrayList<>();
        list.add(new Comic("Truyện 1", "Mô tả truyện 1", R.drawable.img1));
        list.add(new Comic("Truyện 2", "Mô tả truyện 2", R.drawable.img2));
        list.add(new Comic("Truyện 3", "Mô tả truyện 3", R.drawable.img3));
        list.add(new Comic("Truyện 4", "Mô tả truyện 4", R.drawable.img4));
        list.add(new Comic("Truyện 5", "Mô tả truyện 5", R.drawable.img5));
        return list;
    }

    private void LoadSlide() {
        listSlide = getListAnh();
        adapterSlides = new AdapterSlides(listSlide);
        vp_slide.setAdapter(adapterSlides);

        chuyenSlide.setViewPager(vp_slide);

        vp_slide.setPageTransformer(new DepthPageTransformer());
        // auto chạy sau 5s
        vp_slide.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 1000);
            }
        });
    }

    private ArrayList<Slides> getListAnh() {
        ArrayList<Slides>list = new ArrayList<>();
        list.add(new Slides(R.drawable.img1));
        list.add(new Slides(R.drawable.img2));
        list.add(new Slides(R.drawable.img3));
        list.add(new Slides(R.drawable.img4));
        list.add(new Slides(R.drawable.img5));
        return list;
    }

    private void anhXa(View view) {
        timkiem = view.findViewById(R.id.tv_timkiem);
        vp_slide = view.findViewById(R.id.id_viewSilde);
//        gridV_Comic = view.findViewById(R.id.gridV_Comic);
        chuyenSlide = view.findViewById(R.id.id_chuyensilde);
        recyclerView = view.findViewById(R.id.recy_comic);
    }
}
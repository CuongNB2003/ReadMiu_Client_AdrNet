package net.cuongpro.readmiu.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import net.cuongpro.readmiu.R;
import net.cuongpro.readmiu.api.LinkApi;
import net.cuongpro.readmiu.model.Comic;
import net.cuongpro.readmiu.model.slide.Photo;
import net.cuongpro.readmiu.screen.activity.DetailStoryActivity;

import java.util.List;

public class AdapterSlides extends RecyclerView.Adapter<AdapterSlides.PhotoViewHolder> {
    Context context;
    List<Comic> listPhoto;

    public AdapterSlides(Context context, List<Comic> listPhoto) {
        this.context = context;
        this.listPhoto = listPhoto;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slide, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Comic obj = listPhoto.get(position);
        if(listPhoto == null)
            return;
        // lưu ảnh vào bộ nhớ
        RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
        // sử dụng thư viện để load ảnh từ server
        Glide.with(context).load(LinkApi.linkUrl +obj.getAnhBia())
//                .apply(requestOptions)
                .error(R.drawable.img_err)
                .into(holder.imgSilde);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailStoryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("idComic", obj.getId());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listPhoto != null)
            return listPhoto.size();
        return 0;
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgSilde;
        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSilde = itemView.findViewById(R.id.img_slide);
        }
    }
}

package net.cuongpro.readmiu.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import net.cuongpro.readmiu.R;
import net.cuongpro.readmiu.api.LinkApi;
import net.cuongpro.readmiu.model.Comic;
import net.cuongpro.readmiu.screen.activity.DetailStoryActivity;

import java.util.ArrayList;
import java.util.List;

public class AdapterComic extends RecyclerView.Adapter<AdapterComic.ViewComicHolder> {
    Context context;
    List<Comic> listComic;

    public AdapterComic(Context context) {
        this.context = context;
    }

    public void setListComic (List<Comic> list){
        this.listComic = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewComicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comic, parent, false);
        return new ViewComicHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewComicHolder holder, int position) {
        Comic obj = listComic.get(position);
        if(obj == null){
            return;
        }
        // lưu ảnh vào bộ nhớ
        RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
        // sử dụng thư viện để load ảnh từ server
        Glide.with(context).load(LinkApi.linkUrl +obj.getAnhBia())
//                .apply(requestOptions)
                .error(R.drawable.img_err)
                .into(holder.imgComic);
        holder.tvName.setText(obj.getTenChuyen());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailStoryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("idComic", obj.getId());
                bundle.putStringArray("listAnh", obj.getListPhoto());
                bundle.putString("anhBia", obj.getAnhBia());
                bundle.putString("tenTruyen", obj.getTenChuyen());
                bundle.putString("tacGia", obj.getTenTacGia());
                bundle.putString("xuatBan", obj.getNamXuatBan());
                bundle.putString("moTa", obj.getMotaChuyen());

                Log.d(LinkApi.TAG, "onClick: list anh"+obj.getListPhoto());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listComic != null)
            return listComic.size();
        return 0;
    }

    public class ViewComicHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView imgComic;
        public ViewComicHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            imgComic = itemView.findViewById(R.id.img_Comic);
        }
    }
}

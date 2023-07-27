package net.cuongpro.readmiu.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import net.cuongpro.readmiu.R;
import net.cuongpro.readmiu.api.LinkApi;
import net.cuongpro.readmiu.model.model_api.PhotoList;

import java.util.List;

public class AdapterReadComic extends RecyclerView.Adapter<AdapterReadComic.ComicViewHolder> {
    Context context;
    List<PhotoList> listPhoto;

    public AdapterReadComic(Context context) {
        this.context = context;
    }

    public void setListPhoto (List<PhotoList> list){
        this.listPhoto = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ComicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_read_comic, parent, false);
        return new ComicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComicViewHolder holder, int position) {
        PhotoList obj = listPhoto.get(position);
        if(listPhoto == null)
            return;
        // lưu ảnh vào bộ nhớ
        RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
        // sử dụng thư viện để load ảnh từ server
        Glide.with(context).load(LinkApi.linkUrl +obj.getAnh())
//                .apply(requestOptions)
                .error(R.drawable.img_err)
                .into(holder.imgPhoto);

        for (int i = 0; i <= listPhoto.size(); i++){
            holder.tvSoTrang.setText("Trang "+(position+1));
        }
        Log.d(LinkApi.TAG, "onBindViewHolder==: "+(position+1));
    }

    @Override
    public int getItemCount() {
        if(listPhoto != null)
            return listPhoto.size();
        return 0;
    }

    public class ComicViewHolder extends RecyclerView.ViewHolder{
        ImageView imgPhoto;
        TextView tvSoTrang;
        public ComicViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.imgListPhoto);
            tvSoTrang = itemView.findViewById(R.id.tvSoTrang);
        }
    }
}

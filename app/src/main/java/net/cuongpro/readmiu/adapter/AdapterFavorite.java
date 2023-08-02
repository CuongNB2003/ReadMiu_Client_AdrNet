package net.cuongpro.readmiu.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import net.cuongpro.readmiu.model.Favorite;
import net.cuongpro.readmiu.screen.activity.DetailStoryActivity;

import java.util.List;

public class AdapterFavorite extends RecyclerView.Adapter<AdapterFavorite.FavoriteViewHolder> {
    private Context context;
    private IClickListener clickListener;

    public AdapterFavorite(Context context, IClickListener clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }

    private List<Favorite> listFavorite;


    public interface IClickListener{
        void onClickDelete(String id);
    }

    public void setData(List<Favorite> list) {
        this.listFavorite = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite , parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        Favorite obj = listFavorite.get(position);
        if(obj == null)
            return;

        // lưu ảnh vào bộ nhớ
        RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
        // sử dụng thư viện để load ảnh từ server
        Glide.with(context).load(LinkApi.linkUrl +obj.getComic().getAnhBia())
//                .apply(requestOptions)
                .error(R.drawable.img_err)
                .into(holder.imgAnhBia);
        holder.tvTenTacGia.setText("Tác giả: "+obj.getComic().getTenTacGia());
        holder.tvName.setText(""+obj.getComic().getTenChuyen());
        holder.tvMota.setText("Mô tả: "+obj.getComic().getMotaChuyen());
        holder.tvNamXuatBan.setText("Xuất bản: "+obj.getComic().getNamXuatBan());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailStoryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("idComic", obj.getComic().getId());
//                bundle.putBoolean("Favorite", obj.getFavorite());

                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showDialogDelete(obj.getId());
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listFavorite != null)
            return listFavorite.size();
        return 0;
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder{
        ImageView imgAnhBia;
        TextView tvName, tvTenTacGia, tvNamXuatBan, tvMota;
        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAnhBia = itemView.findViewById(R.id.img_AnhBiaF);
            tvMota = itemView.findViewById(R.id.tv_MoTaF);
            tvName = itemView.findViewById(R.id.tv_NameF);
            tvNamXuatBan = itemView.findViewById(R.id.tv_NamXuatBanF);
            tvTenTacGia = itemView.findViewById(R.id.tv_TenTacGiaF);
        }
    }



    private void showDialogDelete(String id) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        mBuilder.setTitle("Thông báo");
        mBuilder.setMessage("Bạn có muốn xóa hay không");
        mBuilder.setNegativeButton("NO", null);
        mBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                clickListener.onClickDelete(id);
            }
        });
        mBuilder.show();
    }
}

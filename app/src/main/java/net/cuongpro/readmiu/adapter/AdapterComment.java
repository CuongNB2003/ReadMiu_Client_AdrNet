package net.cuongpro.readmiu.adapter;

import android.content.Context;
import android.content.SharedPreferences;
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
import net.cuongpro.readmiu.model.Comment;

import java.util.List;

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.ViewCommentHolder> {
    Context context;
    List<Comment> listCmt;

    public AdapterComment(Context context) {
        this.context = context;
    }

    public void setListCmt(List<Comment> list){
        this.listCmt = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewCommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new ViewCommentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewCommentHolder holder, int position) {
        Comment obj = listCmt.get(position);
        if(obj == null)
            return;
        // set ảnh vào edit cmt
        SharedPreferences sharedPreferences = context.getSharedPreferences("DataUser", Context.MODE_PRIVATE);
        String idUser = sharedPreferences.getString("User", "");
        // lưu ảnh vào bộ nhớ
        RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
        // sử dụng thư viện để load ảnh từ server
        Glide.with(context).load(LinkApi.linkUrl + obj.getUser().getAvata())
//                .apply(requestOptions)
                .error(R.drawable.img_err)
                .into(holder.imgAvata);
        holder.tvNDCmt.setText(obj.getNoiDungCmt());
        if(idUser.equalsIgnoreCase(obj.getUser().getId())){
            holder.tvFullName.setText("You");
        }else {
            holder.tvFullName.setText(obj.getUser().getFullname());
        }
    }

    @Override
    public int getItemCount() {
        if(listCmt != null)
            return listCmt.size();
        return 0;
    }

    public class ViewCommentHolder extends RecyclerView.ViewHolder{
        private ImageView imgAvata;
        private TextView tvFullName;
        private TextView tvNDCmt;
        public ViewCommentHolder(@NonNull View itemView) {
            super(itemView);
            imgAvata = itemView.findViewById(R.id.imgItemAvata);
            tvFullName = itemView.findViewById(R.id.tvItemFullName);
            tvNDCmt = itemView.findViewById(R.id.tvItemNDCmt);
        }
    }
}

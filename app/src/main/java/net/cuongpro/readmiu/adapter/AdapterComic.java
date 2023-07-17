package net.cuongpro.readmiu.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.cuongpro.readmiu.R;
import net.cuongpro.readmiu.model.Comic;
import net.cuongpro.readmiu.screen.activity.DetailStoryActivity;

import java.util.ArrayList;

public class AdapterComic extends RecyclerView.Adapter<AdapterComic.ViewComicHolder> {
    Context context;
    ArrayList<Comic> listComic = new ArrayList<>();

    public AdapterComic(Context context) {
        this.context = context;
    }

    public void setListComic (ArrayList<Comic> list){
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
        holder.tvName.setText(obj.getName());
        holder.imgComic.setImageResource(obj.getImg());
        holder.tvDesc.setText(obj.getDesc());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailStoryActivity.class);
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
        TextView tvName, tvDesc;
        ImageView imgComic;
        public ViewComicHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            imgComic = itemView.findViewById(R.id.img_Comic);
            tvDesc = itemView.findViewById(R.id.tv_desc);
        }
    }
}

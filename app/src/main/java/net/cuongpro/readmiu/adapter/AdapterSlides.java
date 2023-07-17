package net.cuongpro.readmiu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.cuongpro.readmiu.R;
import net.cuongpro.readmiu.model.Slides;

import java.util.ArrayList;

public class AdapterSlides extends RecyclerView.Adapter<AdapterSlides.ViewSilde> {

    Context context;
    ArrayList<Slides> listSilde = new ArrayList<>();

    public AdapterSlides(Context context) {
        this.context = context;
    }

    public AdapterSlides(ArrayList<Slides> list) {
        this.listSilde = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewSilde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slide, parent, false);
        return new ViewSilde(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewSilde holder, int position) {
        Slides objSlide = listSilde.get(position);
        if(listSilde == null)
            return;
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, "Chi tiết truyện", Toast.LENGTH_SHORT).show();
//            }
//        });
        holder.imgSilde.setImageResource(objSlide.getImgComic());
    }

    @Override
    public int getItemCount() {
        if(listSilde != null)
            return listSilde.size();
        return 0;
    }

    public class ViewSilde extends RecyclerView.ViewHolder{
        ImageView imgSilde;
        public ViewSilde(@NonNull View itemView) {
            super(itemView);
            imgSilde = itemView.findViewById(R.id.img_slide);
        }
    }
}

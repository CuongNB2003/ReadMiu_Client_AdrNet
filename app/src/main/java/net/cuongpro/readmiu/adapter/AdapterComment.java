package net.cuongpro.readmiu.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import net.cuongpro.readmiu.R;
import net.cuongpro.readmiu.api.ApiService;
import net.cuongpro.readmiu.api.LinkApi;
import net.cuongpro.readmiu.model.Comment;
import net.cuongpro.readmiu.model.model_api.GetComment;
import net.cuongpro.readmiu.model.model_api.MsgCallApi;
import net.cuongpro.readmiu.screen.activity.LoginActivity;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.ViewCommentHolder> {
    private Context context;
    private IClickListener clickListener;
    private List<Comment> listCmt;
    private SimpleDateFormat formatter = new SimpleDateFormat("E, HH:mm dd-M");

    public AdapterComment(Context context, IClickListener clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }

    public void setListCmt(List<Comment> list){
        this.listCmt = list;
        notifyDataSetChanged();
    }

    public interface IClickListener{
        void onClickUpdate(String id, String cmt);
        void onClickDelete(String id);
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
        String idUser = sharedPreferences.getString("UserID", "");
        Boolean checkLogin = sharedPreferences.getBoolean("CheckLogin", false);
        // lưu ảnh vào bộ nhớ
        RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
        // sử dụng thư viện để load ảnh từ server
        Glide.with(context).load(LinkApi.linkUrl + obj.getUser().getAvata())
//                .apply(requestOptions)
                .error(R.drawable.ic_ed_username)
                .into(holder.imgAvata);
        holder.tvNDCmt.setText(obj.getNoiDungCmt());
        holder.tvDate.setText(""+formatter.format(obj.getNgayCmt()));
        if(idUser.equalsIgnoreCase(obj.getUser().getId())){
            holder.tvFullName.setText("You");
        }else {
            holder.tvFullName.setText(obj.getUser().getFullname());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkLogin){
                    if(idUser.equalsIgnoreCase(obj.getUser().getId())){
                        showDialogPut(obj.getId(), obj.getNoiDungCmt());
                    }else {
                        Toast.makeText(context, "Bạn không có quyền sửa bình luận này", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    showDialogLogin();
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(checkLogin){
                    if(idUser.equalsIgnoreCase(obj.getUser().getId())){
                        showDialogDelete(obj.getId());
                    }else {
                        Toast.makeText(context, "Bạn không có quyền xóa bình luận này", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    showDialogLogin();
                }
                return false;
            }
        });
    }

    private void showDialogLogin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_login, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        alertDialog.show();
        // dialog
        Button btnHuy = view.findViewById(R.id.btnCancelLogin);
        Button btnOK = view.findViewById(R.id.btnLogin);
        TextView tvMess = view.findViewById(R.id.tvMess);
        btnOK.setText("Đăng nhập");
        tvMess.setText("Bạn cần phải đăng nhập để sử dụng chức năng này");
        btnHuy.setOnClickListener(view1 -> {
            alertDialog.dismiss();
        });

        btnOK.setOnClickListener(view1 -> {
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
            alertDialog.dismiss();
        });
    }
    private void showDialogPut(String id, String noiDungCmt) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_edit_cmt, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        alertDialog.show();
        // dialog
        EditText noidung = view.findViewById(R.id.edComment);
        Button edit = view.findViewById(R.id.btnEditCmt);
        Button cancel = view.findViewById(R.id.btnDeleteCmt);

        edit.setText("Sửa");
        cancel.setText("Hủy");
        noidung.setText(noiDungCmt);

        cancel.setOnClickListener(view1 -> {
            alertDialog.dismiss();
        });

        edit.setOnClickListener(view1 -> {
            String cmt = noidung.getText().toString();
            clickListener.onClickUpdate(id, cmt);
            alertDialog.dismiss();
        });
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

    @Override
    public int getItemCount() {
        if(listCmt != null)
            return listCmt.size();
        return 0;
    }

    public class ViewCommentHolder extends RecyclerView.ViewHolder{
        private ImageView imgAvata;
        private TextView tvFullName, tvNDCmt, tvDate;
        public ViewCommentHolder(@NonNull View itemView) {
            super(itemView);
            imgAvata = itemView.findViewById(R.id.imgItemAvata);
            tvFullName = itemView.findViewById(R.id.tvItemFullName);
            tvNDCmt = itemView.findViewById(R.id.tvItemNDCmt);
            tvDate = itemView.findViewById(R.id.tvItemDate);
        }
    }
}

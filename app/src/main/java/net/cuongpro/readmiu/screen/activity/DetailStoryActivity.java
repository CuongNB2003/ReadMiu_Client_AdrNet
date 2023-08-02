package net.cuongpro.readmiu.screen.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import net.cuongpro.readmiu.R;
import net.cuongpro.readmiu.adapter.AdapterComment;
import net.cuongpro.readmiu.api.ApiService;
import net.cuongpro.readmiu.api.LinkApi;
import net.cuongpro.readmiu.model.Comic;
import net.cuongpro.readmiu.model.Comment;
import net.cuongpro.readmiu.model.Favorite;
import net.cuongpro.readmiu.model.User;
import net.cuongpro.readmiu.model.model_api.GetComicOne;
import net.cuongpro.readmiu.model.model_api.GetComment;
import net.cuongpro.readmiu.model.model_api.GetOneFavorite;
import net.cuongpro.readmiu.model.model_api.MsgCallApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailStoryActivity extends AppCompatActivity {
    private ImageView imgBack, imgComic, imgFavorite, imgAvata, imgGuiCmt, imgGuiIcon;
    private TextView tenTruyen, tenTacGia, namXuatBan, moTa, tvTieuDe, tvTongBL;
    private EditText edCmt;
    private Button btnDocTruyen;
    private RecyclerView viewComment;
    private GetComicOne comicOne;
    private String idComic, nameComic, idUser, avata, favorite;
    private GetComment getCommet;
    private List<Comment> listCmt = new ArrayList<>();
    private AdapterComment adapterComment;
    private int sumCmt;
    private ProgressDialog loading;
    private MsgCallApi msgCallApi;
    private boolean checkFavorite, checkLogin;
    private GetOneFavorite getOneFavorite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_story);
        initUi();
        // set ảnh vào edit cmt
        SharedPreferences sharedPreferences = getSharedPreferences("DataUser", Context.MODE_PRIVATE);
        checkLogin = sharedPreferences.getBoolean("CheckLogin", false);
        avata = sharedPreferences.getString("Avata","");
        idUser = sharedPreferences.getString("UserID", "");

        Bundle bundle = getIntent().getExtras();
        idComic = bundle.getString("idComic");

        getComicOne(idComic);
        getCommetInComic(idComic);
        getOneFavorite(idUser, idComic);

        Glide.with(DetailStoryActivity.this).load(LinkApi.linkUrl + avata)
                .error(R.drawable.ic_ed_username)
                .into(imgAvata);


        Log.d(LinkApi.TAG, "onCreate:Sau "+checkFavorite);
        if(checkLogin){
            if(checkFavorite){
                imgFavorite.setImageResource(R.drawable.ic_favorite_true);
            }else {
                imgFavorite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialogFavorite();
                    }
                });
            }
        }else {
            imgFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialogLogin();
                }
            });
        }
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnDocTruyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set an hien frament setting
                if(checkLogin){
                    Intent intent = new Intent(DetailStoryActivity.this, ReadStoryActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("idComic", idComic);
                    bundle.putString("nameComic", nameComic);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else {
                    showDialogLogin();
                }
            }
        });
        imgGuiCmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkLogin){
                    if(validateCmt()){
                        Comment comment = new Comment();
                        comment.setIdTruyen(idComic);
                        comment.setUser(new User(idUser));
                        comment.setNoiDungCmt(edCmt.getText().toString());
                        postCmt(comment);
                    }
                }else {
                    showDialogLogin();
                }
            }
        });
        imgGuiIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog("Chức năng sẽ sớm đc cập nhật");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
//        getComicOne(idComic);
//        getCommetInComic(idComic);
//        getOneFavorite(idUser, idComic);
    }
    private void getOneFavorite(String idUser, String idComic) {
        ApiService.apiService.getOneFavorite(idUser, idComic).enqueue(new Callback<GetOneFavorite>() {
            @Override
            public void onResponse(Call<GetOneFavorite> call, Response<GetOneFavorite> response) {
                if(response.isSuccessful()){
                    getOneFavorite = response.body();
                    checkFavorite = getOneFavorite.getStatus();
                    favorite = getOneFavorite.getMsg();
                    Log.d(LinkApi.TAG, "onResponse:trước "+checkFavorite);
                }
            }

            @Override
            public void onFailure(Call<GetOneFavorite> call, Throwable t) {

            }
        });
    }
    private void getCommetInComic(String id) {
        loading.setMessage("Đang load comment...");
        loading.show();
        ApiService.apiService.getCommetInComic(id).enqueue(new Callback<GetComment>() {
            @Override
            public void onResponse(Call<GetComment> call, Response<GetComment> response) {
                if(response.isSuccessful()){
                    getCommet = response.body();
                    listCmt = Arrays.asList(getCommet.getComments());

                    setLayoutComment(listCmt);
                    sumCmt = listCmt.size();
                    tvTongBL.setText("Tổng "+sumCmt+" bình luận");
                    loading.dismiss();
                }
            }

            @Override
            public void onFailure(Call<GetComment> call, Throwable t) {
                Log.d(LinkApi.TAG, "Lỗi get comment "+t.getLocalizedMessage());
                loading.dismiss();
            }
        });
    }
    private void postCmt(Comment comment) {
        loading.setMessage("Đang gửi bình luận...");
        loading.show();
        ApiService.apiService.postComment(comment).enqueue(new Callback<MsgCallApi>() {
            @Override
            public void onResponse(Call<MsgCallApi> call, Response<MsgCallApi> response) {
                if(response.isSuccessful()){
                    msgCallApi = response.body();
//                    Toast.makeText(DetailStoryActivity.this, "Thêm bình luận thành công", Toast.LENGTH_SHORT).show();
                    edCmt.setText("");
                    getCommetInComic(idComic);
                    loading.dismiss();
                }
            }

            @Override
            public void onFailure(Call<MsgCallApi> call, Throwable t) {
                Log.d(LinkApi.TAG, "PostCmt Error: "+t.getLocalizedMessage());
                loading.dismiss();
            }
        });
    }
    private void getComicOne(String id) {
        loading.setMessage("Đang load truyện...");
        loading.show();
        ApiService.apiService.getComic(id).enqueue(new Callback<GetComicOne>() {
            @Override
            public void onResponse(Call<GetComicOne> call, Response<GetComicOne> response) {
                if(response.isSuccessful()){
                    comicOne = response.body();
//                    Toast.makeText(DetailStoryActivity.this, ""+comicOne.getMsg(), Toast.LENGTH_SHORT).show();
                    // lưu ảnh vào bộ nhớ
                    RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
                    // sử dụng thư viện để load ảnh từ server
                    Glide.with(getApplication()).load(LinkApi.linkUrl +comicOne.getComic().getAnhBia())
                            .apply(requestOptions)
                            .error(R.drawable.img_err)
                            .into(imgComic);
                    tenTruyen.setText(""+comicOne.getComic().getTenChuyen());
                    tvTieuDe.setText(""+comicOne.getComic().getTenChuyen());
                    tenTacGia.setText(""+comicOne.getComic().getTenTacGia());
                    namXuatBan.setText(""+comicOne.getComic().getNamXuatBan());
                    moTa.setText(""+comicOne.getComic().getMotaChuyen());
                    //lấy dữ liệu gửi sang read
                    idComic = comicOne.getComic().getId();
                    nameComic = comicOne.getComic().getTenChuyen();

                    loading.dismiss();
                }
            }

            @Override
            public void onFailure(Call<GetComicOne> call, Throwable t) {
                Log.d(LinkApi.TAG, "Không lấy ddc truyện"+t.getLocalizedMessage());
                loading.dismiss();
            }
        });
    }
    private void setLayoutComment(List<Comment> list) {
        adapterComment = new AdapterComment(DetailStoryActivity.this, new AdapterComment.IClickListener() {
            @Override
            public void onClickUpdate(String id, String cmt) {
                loading.show();
                ApiService.apiService.putComment(id, cmt).enqueue(new Callback<MsgCallApi>() {
                    @Override
                    public void onResponse(Call<MsgCallApi> call, Response<MsgCallApi> response) {
                        if(response.isSuccessful()){
                            msgCallApi = response.body();
//                            Toast.makeText(DetailStoryActivity.this, ""+msgCallApi.getMsg()+id, Toast.LENGTH_SHORT).show();
//                            Log.d(LinkApi.TAG, "onResponse sửa: "+id);
                            getCommetInComic(idComic);
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<MsgCallApi> call, Throwable t) {
                        Log.d(LinkApi.TAG, "Sửa cmt thất bại "+ t.getLocalizedMessage());
                        loading.dismiss();
                    }
                });
            }

            @Override
            public void onClickDelete(String id) {
                loading.show();
                ApiService.apiService.deletComment(id).enqueue(new Callback<MsgCallApi>() {
                    @Override
                    public void onResponse(Call<MsgCallApi> call, Response<MsgCallApi> response) {
                        if(response.isSuccessful()){
                            MsgCallApi msgCallApi = response.body();
//                            Toast.makeText(DetailStoryActivity.this, ""+msgCallApi.getMsg()+id, Toast.LENGTH_SHORT).show();
                            Log.d(LinkApi.TAG, "onResponse xóa: "+id);
                            getCommetInComic(idComic);
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<MsgCallApi> call, Throwable t) {
                        Log.d(LinkApi.TAG, "Xóa cmt thất bại "+ t.getLocalizedMessage());
                        loading.dismiss();
                    }
                });
            }
        });
        tvTongBL.setText("Tổng "+sumCmt+" bình luận");
        adapterComment.setListCmt(list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        viewComment.setLayoutManager(layoutManager);
        viewComment.setAdapter(adapterComment);
    }
    private void postFavorite(Favorite favorite){
        ApiService.apiService.postFavorite(favorite).enqueue(new Callback<MsgCallApi>() {
            @Override
            public void onResponse(Call<MsgCallApi> call, Response<MsgCallApi> response) {
                if(response.isSuccessful()){
                    msgCallApi = response.body();
//                    Toast.makeText(DetailStoryActivity.this, ""+msgCallApi.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MsgCallApi> call, Throwable t) {
                Log.d(LinkApi.TAG, "onFailure: "+t.getLocalizedMessage());
            }
        });
    }
    private void initUi() {
        loading = new ProgressDialog(this);

        imgBack = findViewById(R.id.img_backDetal);
        imgComic = findViewById(R.id.img_anhBia);
        tenTruyen = findViewById(R.id.tv_tenTruyen);
        tenTacGia = findViewById(R.id.tv_tenTacGia);
        namXuatBan = findViewById(R.id.tv_namXuatBan);
        moTa = findViewById(R.id.tv_mota);
        btnDocTruyen = findViewById(R.id.btn_read);
        viewComment = findViewById(R.id.recy_comment);
        tvTieuDe = findViewById(R.id.tv_TenTruyen);
        imgFavorite = findViewById(R.id.imgFavorite);

        imgGuiCmt = findViewById(R.id.imgCmt);
        imgGuiIcon = findViewById(R.id.imgIcon);
        imgAvata = findViewById(R.id.imgAvata);
        edCmt = findViewById(R.id.edCmt);

        tvTongBL = findViewById(R.id.tv_tongBL);
    }
    private boolean validateCmt() {
        if(edCmt.getText().toString().isEmpty()){
//            dialog("Bạn cần nhập gì đó");
            edCmt.setError("Bạn phải nhập gì đó");
            return false;
        }
        return true;
    }
    public void dialog(String thongbao) {
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailStoryActivity.this);
        View view = LayoutInflater.from(DetailStoryActivity.this).inflate(R.layout.dialog_notification, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        alertDialog.show();
        TextView tvThongbao = view.findViewById(R.id.tvMess);
        // dialog
        Button btnThongBao = view.findViewById(R.id.btnThongBao);
        tvThongbao.setText(thongbao);

        btnThongBao.setOnClickListener(view1 -> {
            alertDialog.dismiss();
        });
    }
    private void showDialogLogin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailStoryActivity.this);
        View view = LayoutInflater.from(DetailStoryActivity.this).inflate(R.layout.dialog_login, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        alertDialog.show();
        // dialog
        Button btnHuy = view.findViewById(R.id.btnCancelLogin);
        Button btnOK = view.findViewById(R.id.btnLogin);
        TextView tvMess = view.findViewById(R.id.tvMess);
        btnOK.setText("Đăng nhập");
        tvMess.setText("Bạn cần đăng nhập để sử dụng chức năng này !");
        btnHuy.setOnClickListener(view1 -> {
            alertDialog.dismiss();
        });

        btnOK.setOnClickListener(view1 -> {
            Intent intent = new Intent(DetailStoryActivity.this, LoginActivity.class);
            startActivity(intent);
            alertDialog.dismiss();
        });
    }
    private void showDialogFavorite(){
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailStoryActivity.this);
        View view = LayoutInflater.from(DetailStoryActivity.this).inflate(R.layout.dialog_login, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        alertDialog.show();
        // dialog
        Button btnHuy = view.findViewById(R.id.btnCancelLogin);
        Button btnOK = view.findViewById(R.id.btnLogin);
        TextView tvMess = view.findViewById(R.id.tvMess);
        btnOK.setText("Ok");
        tvMess.setText("Bạn có muốn thêm truyện vào danh sách yêu thích ?");
        btnHuy.setOnClickListener(view1 -> {
            alertDialog.dismiss();
        });

        btnOK.setOnClickListener(view1 -> {
            Favorite favorite = new Favorite();
            favorite.setIdUser(idUser);
            favorite.setFavorite(true);
            favorite.setComic(new Comic(idComic));
            postFavorite(favorite);
            imgFavorite.setImageResource(R.drawable.ic_favorite_true);
            alertDialog.dismiss();
        });
    }
}
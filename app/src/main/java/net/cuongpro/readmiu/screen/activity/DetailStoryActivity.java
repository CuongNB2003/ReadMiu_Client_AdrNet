package net.cuongpro.readmiu.screen.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import net.cuongpro.readmiu.model.Comment;
import net.cuongpro.readmiu.model.User;
import net.cuongpro.readmiu.model.model_api.GetComicOne;
import net.cuongpro.readmiu.model.model_api.GetCommet;
import net.cuongpro.readmiu.model.model_api.MsgCallApi;

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
    private String[] listPhoto;
    private String idComic, nameComic, tacGia, motaTruyen, xuatBan, anhBia, idUser, fullname;
    private GetCommet getCommet;
    private List<Comment> listCmt;
    private AdapterComment adapterComment;
    private int sumCmt;
    private MsgCallApi msgCallApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_story);
        initUi();
        // set ảnh vào edit cmt
        SharedPreferences sharedPreferences = getSharedPreferences("DataUser", Context.MODE_PRIVATE);
        boolean checkLogin = sharedPreferences.getBoolean("CheckLogin", false);
        String avata = sharedPreferences.getString("Avata","");
        idUser = sharedPreferences.getString("User", "");
        fullname = sharedPreferences.getString("FullName", "");
        Glide.with(DetailStoryActivity.this).load(LinkApi.linkUrl + avata)
                .error(R.drawable.ic_ed_username)
                .into(imgAvata);

        // ban bundle sang detail
        Bundle bundle = getIntent().getExtras();
        idComic = bundle.getString("idComic");
        nameComic = bundle.getString("tenTruyen");
        tacGia = bundle.getString("tacGia");
        motaTruyen = bundle.getString("moTa");
        xuatBan = bundle.getString("xuatBan");
        anhBia = bundle.getString("anhBia");
        listPhoto = bundle.getStringArray("listAnh");
//        getComicOne(idComic);
        getCommetInComic(idComic);
        // lưu ảnh vào bộ nhớ
        RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
        // sử dụng thư viện để load ảnh từ server
        Glide.with(getApplication()).load(LinkApi.linkUrl +anhBia)
//                .apply(requestOptions)
                .error(R.drawable.img_err)
                .into(imgComic);
        tenTruyen.setText(""+nameComic);
        tvTieuDe.setText(""+nameComic);
        tenTacGia.setText(""+tacGia);
        namXuatBan.setText(""+xuatBan);
        moTa.setText(""+motaTruyen);
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
                    bundle.putStringArray("listPhoto", listPhoto);
                    Log.d(LinkApi.TAG, "onClick: lấy list photo"+listPhoto);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else {
                    showDialogLogin();
                }
            }
        });
        imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgFavorite.setImageResource(R.drawable.ic_favorite_true);
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
                        comment.setNoiDungCmt(edCmt.getText().toString().trim());
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

    private boolean validateCmt() {
        if(edCmt.getText().toString().isEmpty()){
            dialog("Bạn cần nhập gì đó");
            return false;
        }
        return true;
    }
    private void getCommetInComic(String id) {
        ApiService.apiService.getCommetInComic(id).enqueue(new Callback<GetCommet>() {
            @Override
            public void onResponse(Call<GetCommet> call, Response<GetCommet> response) {
                if(response.isSuccessful()){
                    getCommet = response.body();
                    listCmt = Arrays.asList(getCommet.getComments());

                    setDataAdapter(listCmt);
                    sumCmt = listCmt.size();
                    tvTongBL.setText("Tổng "+sumCmt+" bình luận");
                }
            }

            @Override
            public void onFailure(Call<GetCommet> call, Throwable t) {
                Log.d(LinkApi.TAG, "Lỗi get comment "+t.getLocalizedMessage());
            }
        });
    }
    private void setDataAdapter(List<Comment> list) {
        adapterComment = new AdapterComment(DetailStoryActivity.this);
        adapterComment.setListCmt(list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        viewComment.setLayoutManager(layoutManager);
        viewComment.setAdapter(adapterComment);
    }
    private void postCmt(Comment comment) {
        ApiService.apiService.postComment(comment).enqueue(new Callback<MsgCallApi>() {
            @Override
            public void onResponse(Call<MsgCallApi> call, Response<MsgCallApi> response) {
                if(response.isSuccessful()){
                    msgCallApi = response.body();
//                    Toast.makeText(DetailStoryActivity.this, "Thêm bình luận thành công", Toast.LENGTH_SHORT).show();
                    getCommetInComic(comment.getIdTruyen());
                    edCmt.setText("");
                }
            }

            @Override
            public void onFailure(Call<MsgCallApi> call, Throwable t) {

            }
        });
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
        tvMess.setText("Bạn cần phải đăng nhập để sử dụng chức năng này");
        btnHuy.setOnClickListener(view1 -> {
            alertDialog.dismiss();
        });

        btnOK.setOnClickListener(view1 -> {
            Intent intent = new Intent(DetailStoryActivity.this, LoginActivity.class);
            startActivity(intent);
            alertDialog.dismiss();
        });
    }
    private void getComicOne(String id) {
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
                    idComic = comicOne.getComic().getId();
                    nameComic = comicOne.getComic().getTenChuyen();
                    listPhoto = comicOne.getComic().getListPhoto();

                    listPhoto = comicOne.getComic().getListPhoto();
                    Log.d(LinkApi.TAG, "onResponse:check link ảnh "+ comicOne.getComic().getAnhBia());
                }
            }

            @Override
            public void onFailure(Call<GetComicOne> call, Throwable t) {

            }
        });
    }
    private void initUi() {
        imgBack = findViewById(R.id.img_backDetal);
        imgComic = findViewById(R.id.img_anhBia);
        tenTruyen = findViewById(R.id.tv_tenTruyen);
        tenTacGia = findViewById(R.id.tv_tenTacGia);
        namXuatBan = findViewById(R.id.tv_namXuatBan);
        moTa = findViewById(R.id.tv_mota);
        btnDocTruyen = findViewById(R.id.btn_read);
        viewComment = findViewById(R.id.recy_comment);
        tvTieuDe = findViewById(R.id.tv_TenTruyen);
        imgFavorite = findViewById(R.id.img_Favorite);

        imgGuiCmt = findViewById(R.id.imgCmt);
        imgGuiIcon = findViewById(R.id.imgIcon);
        imgAvata = findViewById(R.id.imgAvata);
        edCmt = findViewById(R.id.edCmt);

        tvTongBL = findViewById(R.id.tv_tongBL);
    }
}
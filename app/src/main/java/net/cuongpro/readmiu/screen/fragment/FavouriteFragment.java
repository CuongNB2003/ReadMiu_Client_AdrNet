package net.cuongpro.readmiu.screen.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.cuongpro.readmiu.R;
import net.cuongpro.readmiu.adapter.AdapterComic;
import net.cuongpro.readmiu.adapter.AdapterFavorite;
import net.cuongpro.readmiu.api.ApiService;
import net.cuongpro.readmiu.api.LinkApi;
import net.cuongpro.readmiu.model.Favorite;
import net.cuongpro.readmiu.model.model_api.GetFavorite;
import net.cuongpro.readmiu.model.model_api.MsgCallApi;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FavouriteFragment extends Fragment {
    private RecyclerView recyFavorite;
    private ProgressDialog progressDialog;
    private List<Favorite> listFavorite;
    private AdapterFavorite adapterFavorite;
    private GetFavorite getFavorite;
    private String idUser;
    private boolean checkLogin;
    public static FavouriteFragment newInstance(String param1, String param2) {
        FavouriteFragment fragment = new FavouriteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_favourite, container, false);
        initUi(view);
        // set ảnh vào edit cmt
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("DataUser", Context.MODE_PRIVATE);
        idUser = sharedPreferences.getString("UserID", "");
        checkLogin = sharedPreferences.getBoolean("CheckLogin", false);

        if(checkLogin){
            getListFavorite(idUser);
        }else {

        }

        return view;
    }

    private void getListFavorite(String id) {
        progressDialog.show();
        ApiService.apiService.getFavorite(id).enqueue(new Callback<GetFavorite>() {
            @Override
            public void onResponse(Call<GetFavorite> call, Response<GetFavorite> response) {
                if(response.isSuccessful()){
                    getFavorite = response.body();
                    listFavorite = Arrays.asList(getFavorite.getFavorites());
                    setLayoutFavorite(listFavorite);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<GetFavorite> call, Throwable t) {
                Log.d(LinkApi.TAG, "onFailure: "+ t.getLocalizedMessage());
            }
        });
    }

    private void setLayoutFavorite(List<Favorite> listFavorite) {
        adapterFavorite = new AdapterFavorite(getContext(), new AdapterFavorite.IClickListener() {
            @Override
            public void onClickDelete(String id) {
                progressDialog.show();
                ApiService.apiService.deleteFavorite(id).enqueue(new Callback<MsgCallApi>() {
                    @Override
                    public void onResponse(Call<MsgCallApi> call, Response<MsgCallApi> response) {
                        if(response.isSuccessful()){
                            MsgCallApi msgCallApi = response.body();
//                            Toast.makeText(getContext(), ""+msgCallApi.getMsg(), Toast.LENGTH_SHORT).show();
                            getListFavorite(idUser);
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<MsgCallApi> call, Throwable t) {
                        Log.d(LinkApi.TAG, "Lỗi ở favorite "+t.getLocalizedMessage());
                        progressDialog.dismiss();
                    }
                });
            }
        });
        adapterFavorite.setData(listFavorite);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyFavorite.setLayoutManager(layoutManager);
        recyFavorite.setAdapter(adapterFavorite);
    }

    private void initUi(View view) {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Đang load...");
        recyFavorite = view.findViewById(R.id.recy_favorite);
    }



}
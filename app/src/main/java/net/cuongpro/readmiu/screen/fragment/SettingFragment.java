package net.cuongpro.readmiu.screen.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.cuongpro.readmiu.R;
import net.cuongpro.readmiu.api.LinkApi;
import net.cuongpro.readmiu.screen.activity.LoginActivity;
import net.cuongpro.readmiu.screen.activity.MainActivity;
import net.cuongpro.readmiu.screen.activity.RegActivity;

public class SettingFragment extends Fragment {
    Button btnLogin, btnLogout;
    ImageView imgAvata;
    TextView tvRegister, tvFullname, tvEmail;
    LinearLayout changePass, changeInfo, myHistory, myFavourite;
    LinearLayout loginFalse, loginTrue;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_setting, container, false);
        anhXa(view);
        //trạng thái chưa đăng nhập
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RegActivity.class);
                startActivity(intent);
            }
        });
        // trạng thái sau khi đăng nhập
        changeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog("Chức năng đang được phát triên, Bạn hãy quay lại sau nhé");
            }
        });
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog("Chức năng đang được phát triên, Bạn hãy quay lại sau nhé");
            }
        });
        myHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog("Chức năng đang được phát triên, Bạn hãy quay lại sau nhé");
            }
        });
        myFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog("Chức năng đang được phát triên, Bạn hãy quay lại sau nhé");
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();
                SharedPreferences settings = getActivity().getSharedPreferences("DataUser", Context.MODE_PRIVATE);
                settings.edit().clear().commit();
            }
        });

        //set an hien frament setting
        SharedPreferences mySharePref = getActivity().getSharedPreferences("DataUser", Context.MODE_PRIVATE);
        boolean checkLogin = mySharePref.getBoolean("CheckLogin", false);
        Log.d("=============", "onCreateView checkLogin: " + checkLogin);
        if(checkLogin){
            loginTrue.setVisibility(View.VISIBLE);
            loginFalse.setVisibility(View.GONE);
            getDataUser();
        }else {
            loginTrue.setVisibility(View.GONE);
            loginFalse.setVisibility(View.VISIBLE);
        }

        return view;
    }

    private void getDataUser(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("DataUser", Context.MODE_PRIVATE);
        String fullname = sharedPreferences.getString("FullName","");
        String email = sharedPreferences.getString("Email","");
        String avata = sharedPreferences.getString("Avata","");
        tvFullname.setText(fullname);
        tvEmail.setText(email);
        Glide.with(getContext()).load( LinkApi.linkUrl + avata).error(R.drawable.ic_menu_home).into(imgAvata);
    }

    public void dialog(String thongbao) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_notification, null);
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

    private void anhXa(View view) {
        // chưa đăng nhập
        btnLogin = view.findViewById(R.id.btn_dangnhap);
        tvRegister = view.findViewById(R.id.tv_dangky);
        // đã đăng nhập
        btnLogout = view.findViewById(R.id.btn_logout);
        changePass = view.findViewById(R.id.id_changePass);
        changeInfo = view.findViewById(R.id.id_changeInfo);
        myFavourite = view.findViewById(R.id.id_DanhSachYeuThich);
        myHistory = view.findViewById(R.id.id_LichSuDoc);
        imgAvata = view.findViewById(R.id.img_avata);
        tvFullname = view.findViewById(R.id.tv_fullname);
        tvEmail = view.findViewById(R.id.tv_email);
        // set layout
        loginFalse = view.findViewById(R.id.id_ll_LoginFalse);
        loginTrue = view.findViewById(R.id.id_ll_LoginTrue);
    }
}
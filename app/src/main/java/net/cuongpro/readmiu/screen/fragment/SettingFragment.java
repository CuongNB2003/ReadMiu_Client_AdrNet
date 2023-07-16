package net.cuongpro.readmiu.screen.fragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.cuongpro.readmiu.R;
import net.cuongpro.readmiu.screen.activity.LoginActivity;
import net.cuongpro.readmiu.screen.activity.RegActivity;

public class SettingFragment extends Fragment {
    Button btn_Login, btn_Logout;
    TextView tv_Register;
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
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        tv_Register.setOnClickListener(new View.OnClickListener() {
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

        return view;
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
        btn_Login = view.findViewById(R.id.btn_dangnhap);
        tv_Register = view.findViewById(R.id.tv_dangky);
        // đã đăng nhập
        btn_Logout = view.findViewById(R.id.btn_logout);
        changePass = view.findViewById(R.id.id_changePass);
        changeInfo = view.findViewById(R.id.id_changeInfo);
        myFavourite = view.findViewById(R.id.id_DanhSachYeuThich);
        myHistory = view.findViewById(R.id.id_LichSuDoc);
        // set layout
        loginFalse = view.findViewById(R.id.id_ll_LoginFalse);
        loginTrue = view.findViewById(R.id.id_ll_LoginTrue);
    }
}
package net.cuongpro.readmiu.screen.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.cuongpro.readmiu.R;
import net.cuongpro.readmiu.api.ApiService;
import net.cuongpro.readmiu.api.LinkApi;
import net.cuongpro.readmiu.model.model_api.InfoUser;
import net.cuongpro.readmiu.model.model_api.Login;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText edTaiKhoan, edMatKhau;
    private Button btnDangNhap, btnDangKy;
    private TextView tvQuenMK;
    private Login login;
    private InfoUser infoUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhXa();

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, InfoUserActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taikhoan = edTaiKhoan.getText().toString();
                String matkhau = edMatKhau.getText().toString();
                if(validate(taikhoan, matkhau)){
                    LoginApp(taikhoan, matkhau);
                }
            }
        });
        tvQuenMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, PassRetriActivity.class);
                startActivity(intent);
//                finish();
            }
        });
    }

    private boolean validate(String taikhoan, String matkhau) {
        if(taikhoan.isEmpty()){
            edTaiKhoan.setError("Phải nhập tài khoản");
            return false;
        }
        if(matkhau.isEmpty()){
            edMatKhau.setError("Phải nhập mật khẩu !!!");
            return false;
        }
        return true;
    }

    private void LoginApp(String username, String password) {
        ApiService.apiService.loginApp(username, password).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if(response.isSuccessful()){
                    login = response.body();
                    if(login.getCheck() == false){
                        if(login.getMsg().equalsIgnoreCase("Sai mật khẩu")){
                            edMatKhau.setError(login.getMsg());
                        }else if(login.getMsg().equalsIgnoreCase("Tài khoản không tồn tại")){
                            edTaiKhoan.setError(login.getMsg());
                        }
                    }
                    if(login.getCheck() == true){
                        GetInfoUser(username);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }else {
                    Toast.makeText(LoginActivity.this, "Khong load dc du lieu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Log.d(LinkApi.TAG, "onFailure: "+ t.getLocalizedMessage());
                Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void GetInfoUser(String username){
        ApiService.apiService.getInfoUser(username).enqueue(new Callback<InfoUser>() {
            @Override
            public void onResponse(Call<InfoUser> call, Response<InfoUser> response) {
                if(response.isSuccessful()){
                    infoUser = response.body();
                    Log.d(LinkApi.TAG, "onResponse: "+infoUser.getUser().getId());
                    SharedPreferences mySharePref = getSharedPreferences("DataUser", MODE_PRIVATE);
                    SharedPreferences.Editor editor = mySharePref.edit();
                    editor.putBoolean("CheckLogin", true);
                    editor.putString("FullName", infoUser.getUser().getFullname());
                    editor.putString("Avata", infoUser.getUser().getAvata());
                    editor.putString("Phone", infoUser.getUser().getPhone());
                    editor.putString("Email", infoUser.getUser().getEmail());
                    editor.putString("User", infoUser.getUser().getId());
                    editor.apply();
                }
            }

            @Override
            public void onFailure(Call<InfoUser> call, Throwable t) {

            }
        });
    }


    private void anhXa() {
        edTaiKhoan = findViewById(R.id.ed_UserName);
        edMatKhau = findViewById(R.id.ed_PassWord);
        btnDangNhap = findViewById(R.id.btn_login);
        btnDangKy = findViewById(R.id.btn_re);
        tvQuenMK = findViewById(R.id.tv_resetPass);
    }
}
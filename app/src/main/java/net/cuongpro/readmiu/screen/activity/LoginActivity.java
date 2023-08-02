package net.cuongpro.readmiu.screen.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

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
    private TextInputLayout errTaiKhoan, errMatKhau;
    private Button btnDangNhap, btnDangKy;
    private TextView tvQuenMK;
    private Login login;
    private InfoUser infoUser;
    private ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUi();

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
                LoginApp(taikhoan, matkhau);
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

    private void LoginApp(String username, String password) {
        ApiService.apiService.loginApp(username, password).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if(response.isSuccessful()){
                    login = response.body();
                    if(validateLogin(login.getErr(), login.getMsg())){
                        if(login.getCheck() == true){
//                            Toast.makeText(LoginActivity.this, ""+login.getMsg(), Toast.LENGTH_SHORT).show();
                            GetInfoUser(username);
                        }
                    }
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
        loading.show();
        ApiService.apiService.getInfoUser(username).enqueue(new Callback<InfoUser>() {
            @Override
            public void onResponse(Call<InfoUser> call, Response<InfoUser> response) {
                if(response.isSuccessful()){
                    infoUser = response.body();
//                    Log.d(LinkApi.TAG, "onResponse: check id "+infoUser.getUser().getId());
                    SharedPreferences mySharePref = getSharedPreferences("DataUser", MODE_PRIVATE);
                    SharedPreferences.Editor editor = mySharePref.edit();
                    editor.putBoolean("CheckLogin", true);
                    editor.putString("FullName", infoUser.getUser().getFullname());
                    editor.putString("Avata", infoUser.getUser().getAvata());
                    editor.putString("Phone", infoUser.getUser().getPhone());
                    editor.putString("Email", infoUser.getUser().getEmail());
                    editor.putString("UserID", infoUser.getUser().getId());
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    loading.dismiss();
                }
            }

            @Override
            public void onFailure(Call<InfoUser> call, Throwable t) {
                loading.dismiss();
            }
        });
    }
    private void initUi() {
        loading = new ProgressDialog(this);
        loading.setMessage("Đang đăng nhập...");
        edTaiKhoan = findViewById(R.id.ed_UserName);
        edMatKhau = findViewById(R.id.ed_PassWord);
        btnDangNhap = findViewById(R.id.btn_login);
        btnDangKy = findViewById(R.id.btn_re);
        tvQuenMK = findViewById(R.id.tv_resetPass);
        errTaiKhoan = findViewById(R.id.txtErrUsername);
        errMatKhau = findViewById(R.id.txtErrPassword);
    }
    private boolean validateLogin(String err, String msg) {
        // check trống tài khoản
        if(err.equalsIgnoreCase("errNullU")){
            errTaiKhoan.setErrorEnabled(true);
            errTaiKhoan.setError(msg);
            return false;
        }else {
            errTaiKhoan.setErrorEnabled(false);
            errTaiKhoan.setError("");
        }
        // check trông mật khẩu
        if(err.equalsIgnoreCase("errNullP")){
            errMatKhau.setErrorEnabled(true);
            errMatKhau.setError(msg);
            return false;
        }else{
            errMatKhau.setErrorEnabled(false);
            errMatKhau.setError("");
        }
        // sai tài khoản
        if(err.equalsIgnoreCase("errNotU")){
            errTaiKhoan.setErrorEnabled(true);
            errTaiKhoan.setError(msg);
            return false;
        }else {
            errTaiKhoan.setErrorEnabled(false);
            errTaiKhoan.setError("");
        }
        // sai mật khẩu
        if(err.equalsIgnoreCase("errNotP")){
            errMatKhau.setErrorEnabled(true);
            errMatKhau.setError(msg);
            return false;
        }else {
            errMatKhau.setErrorEnabled(false);
            errMatKhau.setError("");
        }
        return true;
    }
}
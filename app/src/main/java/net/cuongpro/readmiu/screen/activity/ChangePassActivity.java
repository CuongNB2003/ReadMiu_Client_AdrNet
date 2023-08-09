package net.cuongpro.readmiu.screen.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import net.cuongpro.readmiu.R;
import net.cuongpro.readmiu.api.ApiService;
import net.cuongpro.readmiu.model.model_api.Login;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassActivity extends AppCompatActivity {
    private EditText edPass, edPassNew, edPassRe;
    private Button btnChangePass;
    private TextInputLayout errPass, errPassNew, errPassRe;
    private ImageView imgBack;
    private String idUser;
    private ProgressDialog progressDialog;
    private Login changePass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        initUi();
        SharedPreferences sharedPreferences = getSharedPreferences("DataUser", Context.MODE_PRIVATE);
        idUser = sharedPreferences.getString("UserID", "");

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = edPass.getText().toString().trim();
                String passNew = edPassNew.getText().toString().trim();
                String passRe = edPassRe.getText().toString().trim();
                changePass(pass, passNew, passRe);
            }
        });
    }


    private void changePass(String pass, String passNew, String passRe) {
        ApiService.apiService.changePass(idUser, pass, passNew, passRe).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if(response.isSuccessful()){
                    changePass = response.body();
                    if(validateChangePass(changePass.getMsg(), changePass.getErr())){
                        progressDialog.show();
                        Toast.makeText(ChangePassActivity.this, ""+changePass.getMsg(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        Intent intent = new Intent(ChangePassActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
    private boolean validateChangePass(String msg, String err) {
        // check trống tài khoản
        if(err.equalsIgnoreCase("errNullPass")){
            errPass.setErrorEnabled(true);
            errPass.setError(msg);
            return false;
        }else {
            errPass.setErrorEnabled(false);
            errPass.setError("");
        }
        // check trông mật khẩu
        if(err.equalsIgnoreCase("errNullPassNew")){
            errPassNew.setErrorEnabled(true);
            errPassNew.setError(msg);
            return false;
        }else{
            errPassNew.setErrorEnabled(false);
            errPassNew.setError("");
        }
        if(err.equalsIgnoreCase("errNullPassRe")){
            errPassRe.setErrorEnabled(true);
            errPassRe.setError(msg);
            return false;
        }else{
            errPassRe.setErrorEnabled(false);
            errPassRe.setError("");
        }
        // check trông mật khẩu
        if(err.equalsIgnoreCase("errNotPass")){
            errPassRe.setErrorEnabled(true);
            errPassRe.setError(msg);
            return false;
        }else{
            errPassRe.setErrorEnabled(false);
            errPassRe.setError("");
        }
        if(err.equalsIgnoreCase("errNotPassErr")){
            errPass.setErrorEnabled(true);
            errPass.setError(msg);
            return false;
        }else{
            errPass.setErrorEnabled(false);
            errPass.setError("");
        }

        return true;
    }
    private void initUi() {
        progressDialog = new ProgressDialog(ChangePassActivity.this);
        progressDialog.setMessage("Đang đổi mật khẩu...");

        edPass = findViewById(R.id.ed_PassChange);
        edPassNew = findViewById(R.id.ed_PassNewChange);
        edPassRe = findViewById(R.id.ed_RePassChange);
        errPass = findViewById(R.id.txtErrPassChange);
        errPassNew = findViewById(R.id.txtErrPassNewChange);
        errPassRe = findViewById(R.id.txtErrRePassChange);
        btnChangePass = findViewById(R.id.btn_DoiMatKhau);
        imgBack = findViewById(R.id.img_backChangePass);
    }
}
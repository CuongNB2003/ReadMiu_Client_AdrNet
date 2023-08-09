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

public class ChangeInfoActivity extends AppCompatActivity {
    private EditText edFullname, edEmail, edPhone;
    private TextInputLayout errFullName, errEmail, errPhone;
    private Button btnChangeInfo;
    private ImageView imgBack;
    private String idUser, fullname, email, phone;
    private ProgressDialog progressDialog;
    private Login changeInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);
        initUi();
        SharedPreferences sharedPreferences = getSharedPreferences("DataUser", Context.MODE_PRIVATE);
        idUser = sharedPreferences.getString("UserID", "");
        fullname = sharedPreferences.getString("FullName", "");
        email = sharedPreferences.getString("Email", "");
        phone = sharedPreferences.getString("Phone", "");

        edFullname.setText(fullname);
        edEmail.setText(email);
        edPhone.setText(phone);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnChangeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullnameNew = edFullname.getText().toString().trim();
                String emailNew = edEmail.getText().toString().trim();
                String phoneNew = edPhone.getText().toString().trim();
                changeInfo(fullnameNew, emailNew, phoneNew);

            }
        });

    }

    private void changeInfo(String fullname, String email, String phone) {
        ApiService.apiService.changeInfo(idUser, fullname, email, phone).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if(response.isSuccessful()){
                    changeInfo = response.body();
                    if(validateChangeInfo(changeInfo.getMsg(), changeInfo.getErr())){
                        progressDialog.show();

                        SharedPreferences mySharePref = getSharedPreferences("DataUser", MODE_PRIVATE);
                        SharedPreferences.Editor editor = mySharePref.edit();
                        editor.putString("FullName", fullname);
                        editor.putString("Phone", phone);
                        editor.putString("Email", email);
                        editor.apply();

                        Toast.makeText(ChangeInfoActivity.this, ""+changeInfo.getMsg(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        Intent intent = new Intent(ChangeInfoActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {

            }
        });
    }

    private boolean validateChangeInfo(String msg, String err) {
        // check trống tài khoản
        if(err.equalsIgnoreCase("errNullFullname")){
            errFullName.setErrorEnabled(true);
            errFullName.setError(msg);
            return false;
        }else {
            errFullName.setErrorEnabled(false);
            errFullName.setError("");
        }
        // check trông mật khẩu
        if(err.equalsIgnoreCase("errNullEmail")){
            errEmail.setErrorEnabled(true);
            errEmail.setError(msg);
            return false;
        }else{
            errEmail.setErrorEnabled(false);
            errEmail.setError("");
        }
        if(err.equalsIgnoreCase("errNullPhone")){
            errPhone.setErrorEnabled(true);
            errPhone.setError(msg);
            return false;
        }else{
            errPhone.setErrorEnabled(false);
            errPhone.setError("");
        }
        return true;
    }

    private void initUi() {
        progressDialog = new ProgressDialog(ChangeInfoActivity.this);
        edFullname = findViewById(R.id.ed_FullnameChange);
        edEmail = findViewById(R.id.ed_EmailChange);
        edPhone = findViewById(R.id.ed_PhoneChange);
        errFullName = findViewById(R.id.txtErrFullnameChange);
        errEmail = findViewById(R.id.txtErrEmailChange);
        errPhone = findViewById(R.id.txtErrPhoneChange);
        btnChangeInfo = findViewById(R.id.btn_ChangeInfo);
        imgBack = findViewById(R.id.img_backChangeInfo);
    }
}
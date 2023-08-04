package net.cuongpro.readmiu.screen.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;

import net.cuongpro.readmiu.R;
import net.cuongpro.readmiu.api.ApiService;
import net.cuongpro.readmiu.api.LinkApi;
import net.cuongpro.readmiu.model.User;
import net.cuongpro.readmiu.model.model_api.Login;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoUserActivity extends AppCompatActivity {
    private ImageView back, imgAvata ;
    private EditText edFullname, edEmail, edPhone;
    private TextInputLayout errFullname, errPhone, errEmail;
    private Button btnTiep;
    private Login infoUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_user);
        initUi();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnTiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname = edFullname.getText().toString();
                String email = edEmail.getText().toString();
                String phone = edPhone.getText().toString();
                if(validateInfoUser(fullname, email, phone)){
                    Intent intent = new Intent(InfoUserActivity.this, RegActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("fullname", fullname);
                    bundle.putString("email", email);
                    bundle.putString("phone", phone);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
    private boolean validateInfoUser(String fullname, String email, String phone) {
        String regexEmail = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
        String regexPhone = "/(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})\\b/";
        // check trống tài khoản
        if(fullname.isEmpty()){
            errFullname.setErrorEnabled(true);
            errFullname.setError("Bạn cần điền tên của bạn");
            return false;
        }else {
            errFullname.setErrorEnabled(false);
            errFullname.setError("");
        }
        // check trông email
        if(email.isEmpty()){
            errEmail.setErrorEnabled(true);
            errEmail.setError("Bạn cần điền email");
            return false;
        }else{
            errEmail.setErrorEnabled(false);
            errEmail.setError("");
        }
        if(!email.matches(regexEmail)){
            errEmail.setErrorEnabled(true);
            errEmail.setError("Email sai định dạng !");
            return false;
        }else{
            errEmail.setErrorEnabled(false);
            errEmail.setError("");
        }
        // số điện thoại
        if(phone.isEmpty()){
            errPhone.setErrorEnabled(true);
            errPhone.setError("Bạn cần điền số điện thoại");
            return false;
        }else {
            errPhone.setErrorEnabled(false);
            errPhone.setError("");
        }
        // số điện thoại
//        if(!phone.matches(regexPhone)){
//            errPhone.setErrorEnabled(true);
//            errPhone.setError("Số điện thoại chưa đúng");
//            return false;
//        }else {
//            errPhone.setErrorEnabled(false);
//            errPhone.setError("");
//        }
        return true;
    }

    private void initUi() {
        back = findViewById(R.id.img_backReg);
        edFullname = findViewById(R.id.ed_fullname);
        edEmail = findViewById(R.id.ed_email);
        edPhone = findViewById(R.id.ed_phone);
        imgAvata = findViewById(R.id.img_Avata);
        btnTiep = findViewById(R.id.btn_tiep);

        errFullname = findViewById(R.id.textErrFullname);
        errEmail = findViewById(R.id.textErrEmail);
        errPhone = findViewById(R.id.textErrPhone);
    }
}
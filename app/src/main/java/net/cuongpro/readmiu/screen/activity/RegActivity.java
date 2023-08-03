package net.cuongpro.readmiu.screen.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import net.cuongpro.readmiu.R;
import net.cuongpro.readmiu.api.ApiService;
import net.cuongpro.readmiu.api.LinkApi;
import net.cuongpro.readmiu.model.User;
import net.cuongpro.readmiu.model.model_api.InfoUser;
import net.cuongpro.readmiu.model.model_api.Login;
import net.cuongpro.readmiu.service.NotifyConfig;

import java.net.URISyntaxException;
import java.util.Date;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegActivity extends AppCompatActivity {
    Button btnNext;
    EditText edUsername, edPass, edRePass;
    private TextInputLayout errUsername, errPass, errRePass;
    private Login regAcc;
    private InfoUser infoUser;
    private ProgressDialog loading;
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(LinkApi.linkUrl);
        } catch (URISyntaxException e) {}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        initUi();

        // mở kết nối
        mSocket.connect();
        // lắng nghe sự kiện
        Bundle bundle = getIntent().getExtras();
        String fullname = bundle.getString("fullname");
        String email = bundle.getString("email");
        String phone = bundle.getString("phone");
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = edPass.getText().toString();
                String repass = edRePass.getText().toString();
                String username = edUsername.getText().toString();
                Log.d(LinkApi.TAG, "onClick: "+fullname+email+phone);
                User user = new User(username, pass, fullname, email, phone);
                regAccount(user, repass);
            }
        });
    }
    private void postNotify(String title, String content){
        // Khởi tạo layout cho Notify
        Notification customNotification = new NotificationCompat.Builder(RegActivity.this, NotifyConfig.CHANEL_ID)
                .setSmallIcon(android.R.drawable.ic_delete)
                .setContentTitle( title )
                .setContentText(content)
                .setAutoCancel(true)

                .build();
        // Khởi tạo Manager để quản lý notify
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(RegActivity.this);

        // Cần kiểm tra quyền trước khi hiển thị notify
        if (ActivityCompat.checkSelfPermission(RegActivity.this,
                android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

            // Gọi hộp thoại hiển thị xin quyền người dùng
            ActivityCompat.requestPermissions(RegActivity.this,
                    new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 999999);
            Toast.makeText(RegActivity.this, "Chưa cấp quyền", Toast.LENGTH_SHORT).show();
            return; // thoát khỏi hàm nếu chưa được cấp quyền
        }
        // nếu đã cấp quyền rồi thì sẽ vượt qua lệnh if trên và đến đây thì hiển thị notify
        // mỗi khi hiển thị thông báo cần tạo 1 cái ID cho thông báo riêng
        int id_notiy = (int) new Date().getTime();// lấy chuỗi time là phù hợp
        //lệnh hiển thị notify
        notificationManagerCompat.notify(id_notiy , customNotification);

    }
    private void regAccount(User user, String repass) {
        ApiService.apiService.regUser(user, repass).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if(response.isSuccessful()){
                    regAcc = response.body();
                    if(validateAccount(regAcc.getErr(), regAcc.getMsg())){
//                        Toast.makeText(RegActivity.this, ""+regAcc.getMsg(), Toast.LENGTH_SHORT).show();
                        GetInfoUser(user.getUsername());
                    }
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Log.d(LinkApi.TAG, "Tạo tài khoản thất bại: "+t.getLocalizedMessage());
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
                    Log.d(LinkApi.TAG, "onResponse: "+infoUser.getUser().getId());
                    SharedPreferences mySharePref = getSharedPreferences("DataUser", MODE_PRIVATE);
                    SharedPreferences.Editor editor = mySharePref.edit();
                    editor.putBoolean("CheckLogin", true);
                    editor.putString("FullName", infoUser.getUser().getFullname());
                    editor.putString("Avata", infoUser.getUser().getAvata());
                    editor.putString("Phone", infoUser.getUser().getPhone());
                    editor.putString("Email", infoUser.getUser().getEmail());
                    editor.putString("UserID", infoUser.getUser().getId());
                    editor.apply();

                    Intent intent = new Intent(RegActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    loading.dismiss();
                    postNotify("Tạo tài khoản thành công", "Chào mừng "+infoUser.getUser().getFullname()+ " đến với readmiu");
                }
            }

            @Override
            public void onFailure(Call<InfoUser> call, Throwable t) {
                loading.dismiss();
            }
        });
    }
    private boolean validateAccount(String err, String msg) {
        // check trống tài khoản
        if(err.equalsIgnoreCase("errNullUser")){
            errUsername.setErrorEnabled(true);
            errUsername.setError(msg);
            return false;
        }else {
            errUsername.setErrorEnabled(false);
            errUsername.setError("");
        }
        // check trông mật khẩu
        if(err.equalsIgnoreCase("errNullPass")){
            errPass.setErrorEnabled(true);
            errPass.setError(msg);
            return false;
        }else{
            errPass.setErrorEnabled(false);
            errPass.setError("");
        }
        // check trống re mật khẩu
        if(err.equalsIgnoreCase("errNullRePass")){
            errRePass.setErrorEnabled(true);
            errRePass.setError(msg);
            return false;
        }else{
            errRePass.setErrorEnabled(false);
            errRePass.setError("");
        }
        // sai re-pass
        if(err.equalsIgnoreCase("rePassErr")){
            errRePass.setErrorEnabled(true);
            errRePass.setError(msg);
            return false;
        }else {
            errRePass.setErrorEnabled(false);
            errRePass.setError("");
        }
        // tài khoản đã tồn tai
        if(err.equalsIgnoreCase("userExists")){
            errUsername.setErrorEnabled(true);
            errUsername.setError(msg);
            return false;
        }else {
            errUsername.setErrorEnabled(false);
            errUsername.setError("");
        }
        return true;
    }
    private void initUi() {
        loading = new ProgressDialog(this);
        loading.setMessage("Đang đăng nhập...");
        btnNext = findViewById(R.id.btn_next);
        edUsername = findViewById(R.id.ed_username);
        edPass = findViewById(R.id.ed_password);
        edRePass = findViewById(R.id.ed_rePassword);

        errUsername = findViewById(R.id.textErrUsername);
        errPass = findViewById(R.id.textErrPass);
        errRePass = findViewById(R.id.textErrRePass);

    }
}
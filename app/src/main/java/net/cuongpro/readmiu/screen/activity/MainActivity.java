package net.cuongpro.readmiu.screen.activity;

import static net.cuongpro.readmiu.R.id.*;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Notification;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import net.cuongpro.readmiu.R;
import net.cuongpro.readmiu.api.LinkApi;
import net.cuongpro.readmiu.screen.fragment.FavouriteFragment;
import net.cuongpro.readmiu.screen.fragment.HomeFragment;
import net.cuongpro.readmiu.screen.fragment.SettingFragment;
import net.cuongpro.readmiu.service.NotifyConfig;

import java.net.URISyntaxException;
import java.util.Date;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(LinkApi.linkUrl);
        } catch (URISyntaxException e) {}
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSocket.connect();
        // lắng nghe sự kiện
        mSocket.on("add comic", addComicNew);

        AHBottomNavigation bottomNavigation =  findViewById(R.id.bottom_navigation);
        replaceFrament(new HomeFragment());
        // tạo item để cho vào bottom
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.title_home, R.drawable.ic_menu_home, R.color.mau_nen);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.title_favourite, R.drawable.ic_menu_favorite, R.color.mau_nen);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.title_setting, R.drawable.ic_menu_setting, R.color.mau_nen);
        // add item vào bottom
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        //Thay đổi hiệu ứng click vào icon
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE_FORCE);
        bottomNavigation.setColored(true);
        bottomNavigation.setBehaviorTranslationEnabled(true);
        //set màu cho icon khi click
        bottomNavigation.setAccentColor(getColor(R.color.text));
        //set màu cho icon khi ko click
        bottomNavigation.setInactiveColor(getColor(R.color.nav_bottom));
        // xử lý add fragment
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position){
                    case 0 :
                        replaceFrament(new HomeFragment());
                        break;
                    case 1 :
                        replaceFrament(new FavouriteFragment());
                        break;
                    case 2 :
                        replaceFrament(new SettingFragment());
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

    }

    private Emitter.Listener addComicNew = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String data = (String) args[0];
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    postNotify("Thông báo", data);
                }
            });
        }
    };

    @Override
    public void onBackPressed() {
        Exit();
    }
    private void Exit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_login, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        alertDialog.show();
        // dialog
        Button btnHuy = view.findViewById(R.id.btnCancelLogin);
        Button btnOK = view.findViewById(R.id.btnLogin);
        TextView tvMess = view.findViewById(R.id.tvMess);
        tvMess.setText("Bạn muốn thoát ứng dụng ?");
        btnOK.setText("Thoát");
        btnHuy.setOnClickListener(view1 -> {
            alertDialog.dismiss();
        });

        btnOK.setOnClickListener(view1 -> {
            alertDialog.dismiss();
            System.exit(0);
        });
    }
    private  void  replaceFrament(Fragment fragment){
        FragmentManager fragmentManager  = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameLayout,fragment);
        transaction.commit();

    }
    private void postNotify(String title, String content){
        // Khởi tạo layout cho Notify
        Notification customNotification = new NotificationCompat.Builder(MainActivity.this, NotifyConfig.CHANEL_ID)
                .setSmallIcon(android.R.drawable.ic_delete)
                .setContentTitle( title )
                .setContentText(content)
                .setAutoCancel(true)

                .build();
        // Khởi tạo Manager để quản lý notify
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(MainActivity.this);

        // Cần kiểm tra quyền trước khi hiển thị notify
        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

            // Gọi hộp thoại hiển thị xin quyền người dùng
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 999999);
            Toast.makeText(MainActivity.this, "Chưa cấp quyền", Toast.LENGTH_SHORT).show();
            return; // thoát khỏi hàm nếu chưa được cấp quyền
        }
        // nếu đã cấp quyền rồi thì sẽ vượt qua lệnh if trên và đến đây thì hiển thị notify
        // mỗi khi hiển thị thông báo cần tạo 1 cái ID cho thông báo riêng
        int id_notiy = (int) new Date().getTime();// lấy chuỗi time là phù hợp
        //lệnh hiển thị notify
        notificationManagerCompat.notify(id_notiy , customNotification);

    }
}
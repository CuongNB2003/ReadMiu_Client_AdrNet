package net.cuongpro.readmiu.screen.activity;

import static net.cuongpro.readmiu.R.id.*;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import net.cuongpro.readmiu.R;
import net.cuongpro.readmiu.screen.fragment.FavouriteFragment;
import net.cuongpro.readmiu.screen.fragment.HomeFragment;
import net.cuongpro.readmiu.screen.fragment.SettingFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
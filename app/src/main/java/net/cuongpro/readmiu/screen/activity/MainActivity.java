package net.cuongpro.readmiu.screen.activity;

import static net.cuongpro.readmiu.R.id.*;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;

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
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.title_home, R.drawable.ic_menu_home, R.color.nav_bottom);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.title_favourite, R.drawable.ic_menu_favorite, R.color.nav_bottom);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.title_setting, R.drawable.ic_menu_setting, R.color.nav_bottom);
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
        bottomNavigation.setInactiveColor(getColor(R.color.mau_nen));
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

    private  void  replaceFrament(Fragment fragment){
        FragmentManager fragmentManager  = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameLayout,fragment);
        transaction.commit();

    }
}
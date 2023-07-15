package net.cuongpro.readmiu.screen;

import static net.cuongpro.readmiu.R.id.*;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import net.cuongpro.readmiu.R;
import net.cuongpro.readmiu.databinding.ActivityMainBinding;
import net.cuongpro.readmiu.screen.fragment.HomeFragment;
import net.cuongpro.readmiu.screen.fragment.SettingFragment;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding activityMainBinding;
    // cường đang testgit nè

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        replaceFrament(new HomeFragment());
        activityMainBinding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == nav_home) {
                replaceFrament(new HomeFragment());
            } else if (itemId == nav_setting) {
                replaceFrament(new SettingFragment());
            }
            return true;
        });
    }

    private  void  replaceFrament(Fragment fragment){
        FragmentManager fragmentManager  = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameLayout,fragment);
        transaction.commit();

    }
}
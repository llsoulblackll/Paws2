package com.app.pawapp;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.app.pawapp.Fragments.HomeFragment;
import com.app.pawapp.Fragments.PetFragment;
import com.app.pawapp.Fragments.ProfileFragment;
import com.app.pawapp.Util.Util;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        Window window = this.getWindow();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        //final Fragment homeFragment = new HomeFragment();
        //final Fragment profileFragment = new ProfileFragment();
        //final Fragment petFragment = new PetFragment();

        if (savedInstanceState == null) {
            //FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            //fragmentTransaction.replace(R.id.fragmentContainer, homeFragment).commit();
            //Util.FragmentHelper.navigate(HomeFragment.class, R.id.fragmentContainer, "homeFragment", fragmentManager);
        }

        fragmentManager = getSupportFragmentManager();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.petsItem) {
                    //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    //fragmentTransaction.replace(R.id.fragmentContainer, petFragment).commit();
                    Util.FragmentHelper.navigate(PetFragment.class, R.id.fragmentContainer, "petFragment", fragmentManager);
                } else if (item.getItemId() == R.id.homeItem) {
                    //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    //fragmentTransaction.replace(R.id.fragmentContainer, homeFragment).commit();
                    Util.FragmentHelper.navigate(HomeFragment.class, R.id.fragmentContainer, "homeFragment", fragmentManager);
                } else if (item.getItemId() == R.id.profileItem) {
                    //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    //fragmentTransaction.replace(R.id.fragmentContainer, profileFragment).commit();
                    Util.FragmentHelper.navigate(ProfileFragment.class, R.id.fragmentContainer, "profileFragment", fragmentManager);
                }
                return true;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.homeItem);
        //Menu menu = bottomNavigationView.getMenu();
        //MenuItem menuItem = menu.getItem(1);
        //menuItem.setChecked(true);

    }
}
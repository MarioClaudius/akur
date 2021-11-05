package com.example.akurandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, View.OnClickListener {
    public static final String EXTRA_SCAN_RESULT = "";
    public static final String EXTRA_STORE_NAME = "NO NAME";
    BottomNavigationView bottomNavigationView;
    FloatingActionButton floatingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingButton = findViewById(R.id.floating_button);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);       //membuat background bottomNavigationView tidak ada
        bottomNavigationView.setOnItemSelectedListener(this::onNavigationItemSelected);

        floatingButton.setOnClickListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();

        if(getIntent() != null){
            String nama = getIntent().getStringExtra("EXTRA_STORE_NAME");
            if(nama == null){
                Log.d("MARIO1", "TIDAK ADA ISI");
            }
            else{
                Log.d("MARIO2", nama);
            }
        }
//        if(!EXTRA_SCAN_RESULT.equals("")){
//            FragmentManager fm = getSupportFragmentManager();
//            HomeFragment fragment = (HomeFragment) fm.findFragmentById(R.id.fragment_container);
//            String result = getIntent().getStringExtra(EXTRA_SCAN_RESULT);
//            fragment.setText(result);
//        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;

        switch(item.getItemId()){
            case R.id.nav_home:
                selectedFragment = new HomeFragment();
                break;

            case R.id.nav_account:
                String nama = getIntent().getStringExtra("EXTRA_STORE_NAME");
                if(nama == null){
                    AccountFragment fragment = AccountFragment.newInstance("No Name");
                    selectedFragment = fragment;
                }
                else{
                    AccountFragment fragment = AccountFragment.newInstance(nama);
                    selectedFragment = fragment;
                }
                break;

            case R.id.nav_history:
                selectedFragment = new HistoryFragment();
                break;

            case R.id.nav_track:
                selectedFragment = new TrackFragment();
                break;
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                selectedFragment).commit();

        return true;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, ScanActivity.class);
        startActivity(intent);
//        FragmentManager fm = getSupportFragmentManager();
//        HomeFragment fragment = (HomeFragment) fm.findFragmentById(R.id.fragment_container);
//        fragment.setText();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String nama = getIntent().getStringExtra("EXTRA_STORE_NAME");
        if(nama == null){
            outState.putString("EXTRA_STORE_NAME", "No Name");
        }
        else{
            outState.putString("EXTRA_STORE_NAME", nama);
        }
    }
}
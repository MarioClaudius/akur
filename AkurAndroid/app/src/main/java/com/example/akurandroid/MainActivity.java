package com.example.akurandroid;
//test this comment for bagus
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, View.OnClickListener {
    public static final String EXTRA_SCAN_RESULT = "";
    public static final String EXTRA_STORE_NAME = "NO NAME";
    String store_name;
    String email;
    String username;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton floatingButton;
    private Dialog dialog;
    Fragment selectedFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingButton = findViewById(R.id.floating_button);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);       //membuat background bottomNavigationView tidak ada
        bottomNavigationView.setOnItemSelectedListener(this::onNavigationItemSelected);
        dialog = new Dialog(this);

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
        //Fragment selectedFragment = null;
        int id = getIntent().getIntExtra("id", 0);
        switch(item.getItemId()){
            case R.id.nav_home:
                selectedFragment = new HomeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
                break;

            case R.id.nav_account:
                Log.d("ID MARIO", "NILAI ID DI NAV ACCOUNT = " + id);
                ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
                Call<AkurAccount> call = apiInterface.getAkurAccountInfo(id);
                Log.d("HORE1", "MASUK KE CASE NAV ACCOUNT");
                call.enqueue(new Callback<AkurAccount>() {
                    @Override
                    public void onResponse(Call<AkurAccount> call, Response<AkurAccount> response) {
                        Log.d("HORE2", "MASUK KE ENQUEUE");
                        AkurAccount account = response.body();
                        store_name = account.getStoreName();
                        email = account.getEmail();
                        username = account.getUsername();
                        int id = getIntent().getIntExtra("id", 0);
                        if(store_name == null){
                            store_name = username;
                        }
                        AccountFragment fragment = AccountFragment.newInstance(id, store_name, username, email);
                        selectedFragment = fragment;
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                selectedFragment).commit();
                    }

                    @Override
                    public void onFailure(Call<AkurAccount> call, Throwable t) {

                    }
                });
                break;

            case R.id.nav_history:
                HistoryFragment fragment = HistoryFragment.newInstance(id);
                selectedFragment = fragment;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
                break;

            case R.id.nav_track:
                selectedFragment = new TrackFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        int id = getIntent().getIntExtra("id", 0);
        Intent intent = new Intent(MainActivity.this, ScanActivity.class);
        intent.putExtra("idUser", id);
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

    public void onBackPressed(){
        dialog.setContentView(R.layout.dialog_exit);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        MaterialButton exitButton = dialog.findViewById(R.id.exit_button);
        MaterialButton cancelButton = dialog.findViewById(R.id.cancel_exit_button);
        dialog.show();
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                moveTaskToBack(true);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
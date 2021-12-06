package com.example.akurandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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
        int id = getIntent().getIntExtra("id", 0);
        String accountUsername = getIntent().getStringExtra("usernameIntent");
        HomeFragment fragment = HomeFragment.newInstance(id, accountUsername);

        floatingButton.setOnClickListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                fragment).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = getIntent().getIntExtra("id", 0);
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<AkurAccount> call = apiInterface.getAkurAccountInfo(id);
        switch(item.getItemId()){
            case R.id.nav_home:
                call.enqueue(new Callback<AkurAccount>() {
                    @Override
                    public void onResponse(Call<AkurAccount> call, Response<AkurAccount> response) {
                        AkurAccount account = response.body();
                        username = account.getUsername();
                        selectedFragment = HomeFragment.newInstance(id, username);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                selectedFragment).commit();
                    }

                    @Override
                    public void onFailure(Call<AkurAccount> call, Throwable t) {

                    }
                });
                break;

            case R.id.nav_account:
                call.enqueue(new Callback<AkurAccount>() {
                    @Override
                    public void onResponse(Call<AkurAccount> call, Response<AkurAccount> response) {
                        Log.d("HORE2", "MASUK KE ENQUEUE");
                        AkurAccount account = response.body();
                        store_name = account.getStoreName();
                        email = account.getEmail();
                        username = account.getUsername();
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
                selectedFragment = HistoryFragment.newInstance(id);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
                break;

            case R.id.nav_track:
                selectedFragment = TrackFragment.newInstance(id);
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
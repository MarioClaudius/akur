package com.example.akurandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailTrackActivity extends AppCompatActivity {
    private RecyclerView rvDetailTrack;
    private List<Scan> list = new ArrayList<Scan>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_track);

        rvDetailTrack = findViewById(R.id.rv_detail_track);
        rvDetailTrack.setHasFixedSize(true);
        int id = getIntent().getIntExtra("IdUser", 0);
        String courierName = getIntent().getStringExtra("ShipmentName");
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<List<Scan>> call = apiInterface.getCourierData(id, courierName);
        call.enqueue(new Callback<List<Scan>>() {
            @Override
            public void onResponse(Call<List<Scan>> call, Response<List<Scan>> response) {
                if(response.isSuccessful()){
                    list = response.body();
                    TextView tvCount = findViewById(R.id.sent_count);
                    tvCount.setText("" + list.size());
                    rvDetailTrack.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    ListDetailTrackAdapter listDetailTrackAdapter = new ListDetailTrackAdapter(list);
                    rvDetailTrack.setAdapter(listDetailTrackAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Scan>> call, Throwable t) {
                Toast.makeText(DetailTrackActivity.this, "Fetch data failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
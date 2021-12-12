package com.akur.akurandroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryFragment extends Fragment {
    private RecyclerView rvHistory;
    private List<Scan> list = new ArrayList<Scan>();

    public HistoryFragment(){}

    public static HistoryFragment newInstance(int id){
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putInt("idBundle", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history, container, false);
        rvHistory = v.findViewById(R.id.rv_row_history);
        rvHistory.setHasFixedSize(true);
        int id = getArguments().getInt("idBundle");
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<List<Scan>> call = apiInterface.getHistoryList(id);
        call.enqueue(new Callback<List<Scan>>() {
            @Override
            public void onResponse(Call<List<Scan>> call, Response<List<Scan>> response) {
                if(response.isSuccessful()){
                    list = response.body();
                    Collections.reverse(list);
                    rvHistory.setLayoutManager(new LinearLayoutManager(v.getContext()));
                    ListScanHistoryAdapter listScanHistoryAdapter = new ListScanHistoryAdapter(list);
                    rvHistory.setAdapter(listScanHistoryAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Scan>> call, Throwable t) {

            }
        });
        return v;
    }
}

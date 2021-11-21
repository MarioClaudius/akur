package com.example.akurandroid;

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

public class HistoryFragment extends Fragment {
    private RecyclerView rvHistory;
    private ArrayList<ScanHistory> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history, container, false);
        rvHistory = v.findViewById(R.id.rv_row_history);
        rvHistory.setHasFixedSize(true);
        list.addAll(ScanHistoryData.getListData());
        rvHistory.setLayoutManager(new LinearLayoutManager(v.getContext()));
        ListScanHistoryAdapter listScanHistoryAdapter = new ListScanHistoryAdapter(list);
        rvHistory.setAdapter(listScanHistoryAdapter);
        //ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        return v;
    }
}

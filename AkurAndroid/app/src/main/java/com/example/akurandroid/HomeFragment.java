package com.example.akurandroid;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private LineChart lineChart;
    private List<Scan> list = new ArrayList<Scan>();
    private HashMap<String, Integer> map = new HashMap<>();

    public HomeFragment(){}

    public static HomeFragment newInstance(int id){
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt("idBundle", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        int id = getArguments().getInt("idBundle");
        lineChart = v.findViewById(R.id.line_chart);
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<List<Scan>> call = apiInterface.getHistoryList(id);
        call.enqueue(new Callback<List<Scan>>() {
            @Override
            public void onResponse(Call<List<Scan>> call, Response<List<Scan>> response) {
                if(response.isSuccessful()){
                    list = response.body();
                    return;
                }
            }

            @Override
            public void onFailure(Call<List<Scan>> call, Throwable t) {

            }
        });
        final SimpleDateFormat DATE = new SimpleDateFormat("dd MMMM yyyy");
        Calendar calAfter = Calendar.getInstance();
        Calendar calBefore = Calendar.getInstance();
        calBefore.add(Calendar.DATE, -6);
        Date dateAfter = calAfter.getTime();
        Date dateBefore = calBefore.getTime();
        List<Scan> newList = new ArrayList<>();
        for(Scan s : list){
            String sDate = s.getDate();
            Date date = null;
            try {
                date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH).parse(sDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(date.after(dateBefore) && date.before(dateAfter)){
                newList.add(s);
            }
        }

        ArrayList<ILineDataSet> datasets = new ArrayList<>();
        ArrayList<Entry> dataVals = new ArrayList<>();
        for(int i = 0; i < 7 ; i++){
            final SimpleDateFormat axisX = new SimpleDateFormat("dd/MM");
            int count = 0;
//            ArrayList<Entry> dataVals = new ArrayList<>();
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -6 + i);
            Date date = cal.getTime();
            for(Scan s : newList){
                Date dateScan = null;
                try {
                    dateScan = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH).parse(s.getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(DATE.format(s.getDate()).equals(DATE.format(date))){
                    count++;
                }
            }
            dataVals.add(new Entry(date.getTime(), count));
        }
        LineDataSet lineScanDataSet = new LineDataSet(dataVals, "Data set 1");
        datasets.add(lineScanDataSet);
        LineData data = new LineData(datasets);
        lineChart.setData(data);
        lineChart.invalidate();
        return v;
    }
}

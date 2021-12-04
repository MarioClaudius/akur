package com.example.akurandroid;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

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
    private PieChart pieChart;
    private List<Scan> list = new ArrayList<Scan>();
    private HashMap<String, Integer> map = new HashMap<>();

    public HomeFragment(){}

    public static HomeFragment newInstance(int id, String username){
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt("idBundle", id);
        args.putString("usernameBundle", username);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        int id = getArguments().getInt("idBundle");
        lineChart = v.findViewById(R.id.line_chart);
        pieChart = v.findViewById(R.id.pie_chart);
        TextView tvUsername = v.findViewById(R.id.tv_nama_user_home);
        TextView tvTotalProductToday = v.findViewById(R.id.tv_total_product_today);
        String username = getArguments().getString("usernameBundle");
        tvUsername.setText(username + "!");
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<List<Scan>> call = apiInterface.getHistoryList(id);
        call.enqueue(new Callback<List<Scan>>() {
            @Override
            public void onResponse(Call<List<Scan>> call, Response<List<Scan>> response) {
                if(response.isSuccessful()){
                    list = response.body();
                    //Algoritma Line chart
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
                            date = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss zzz", Locale.ENGLISH).parse(sDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if(date.after(dateBefore) && date.before(dateAfter)){
                            newList.add(s);
                        }
                    }

                    Thread lineChartThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if(getActivity() == null){
                                return;
                            }
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ArrayList<ILineDataSet> datasets = new ArrayList<>();
                                    ArrayList<Entry> dataVals = new ArrayList<>();
                                    int max = 0;
                                    int scanToday;
                                    for(int i = 0; i < 7 ; i++){
//                                        final SimpleDateFormat axisXFormat = new SimpleDateFormat("dd/MM");
                                        int count = 0;
                                        Calendar cal = Calendar.getInstance();
                                        cal.add(Calendar.DATE, -6 + i);
                                        Date date = cal.getTime();
                                        for(Scan s : newList){
                                            Date dateScan = null;
                                            try {
                                                dateScan = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss zzz", Locale.ENGLISH).parse(s.getDate());
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                            Log.d("DATE1", DATE.format(dateScan));
                                            Log.d("DATE2", DATE.format(date));
                                            if(DATE.format(dateScan).equals(DATE.format(date))){
                                                count++;
                                            }
                                        }
                                        if(max < count){
                                            max = count;
                                        }
                                        if(i == 6){
                                            tvTotalProductToday.setText(count + " Products");
                                        }
//                                        else {
                                            dataVals.add(new Entry(i  , count));
//                                        }
//                                        dataVals.add(new Entry(i - 0.5f, (float) count));
                                    }
                                    LineDataSet lineScanDataSet = new LineDataSet(dataVals, "Jumlah Scan");
                                    lineScanDataSet.setLineWidth(5);
                                    lineScanDataSet.setDrawFilled(true);
                                    lineScanDataSet.setColor(getResources().getColor(R.color.garis_linechart));
                                    lineScanDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                                    lineScanDataSet.setCubicIntensity(0.2f);
                                    datasets.add(lineScanDataSet);
                                    LineData data = new LineData(datasets);
                                    lineChart.setData(data);
                                    XAxis xAxis = lineChart.getXAxis();
                                    YAxis yAxis= lineChart.getAxisLeft();
                                    xAxis.setDrawGridLines(false);
                                    xAxis.setGranularity(0.5f);
//                                    xAxis.setAxisMaximum(7.5f);
                                    yAxis.setGranularity(1);
                                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                                    yAxis.setAxisMinimum(0f);
                                    yAxis.setAxisMaximum(max + 0.25f * max);
                                    yAxis.setEnabled(false);
                                    lineChart.getAxisRight().setEnabled(false);
                                    xAxis.setValueFormatter(new ValueFormatter() {
                                        @Override
                                        public String getAxisLabel(float value, AxisBase axis) {
                                            final SimpleDateFormat axisXFormat = new SimpleDateFormat("dd/MM");
                                            Calendar cal = Calendar.getInstance();
                                            Integer position = Math.round(value);
                                            cal.add(Calendar.DATE, -6 + position);
                                            Date dateAxis = cal.getTime();
                                            return axisXFormat.format(dateAxis);
                                        }
                                    });
                                    Description description = lineChart.getDescription();;
                                    description.setText("Statistik Penjualan");
                                    description.setTextSize(18);
                                    Typeface montserrat = ResourcesCompat.getFont(getContext(), R.font.montserrat_regular);
                                    description.setTypeface(montserrat);
                                    lineChart.getDescription().setPosition(500f, 70f);
                                    lineChart.setDrawBorders(false);
                                    lineChart.setBackgroundColor(getResources().getColor(R.color.background_biru_linechart));
                                    lineChart.invalidate();
                                }
                            });
                        }
                    });
                    lineChartThread.start();

//                    Algoritma Pie Chart
                    Thread pieChartThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if(getActivity() == null){
                                return;
                            }
                            getActivity().runOnUiThread(new Runnable() {
                                @SuppressLint("ResourceType")
                                @Override
                                public void run() {
                                    ArrayList<PieEntry> pieEntries = new ArrayList<>();
                                    ArrayList<Integer> colors = new ArrayList<>();
//                                    for(int color: ColorTemplate.VORDIPLOM_COLORS){
//                                        colors.add(color);
//                                    }
                                    for(int color: ColorTemplate.MATERIAL_COLORS){
                                        colors.add(color);
                                    }
                                    for(int color: ColorTemplate.VORDIPLOM_COLORS){
                                        colors.add(color);
                                    }
                                    for(Shipment shipment : ShipmentData.getListData()){
                                        int count = 0;
                                        for(Scan s : list){
                                            if(s.getCourierName().equals(shipment.getName())){
                                                count++;
                                            }
                                        }
                                        float percentage = (float)count / list.size();
//                                Log.d("PERCENTAGE", "" + percentage);
                                        if(percentage != 0){
                                            pieEntries.add(new PieEntry(percentage, shipment.getName()));
                                        }
                                    }
                                    PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
                                    pieDataSet.setColors(colors);
                                    pieDataSet.setSliceSpace(10);
                                    PieData pieData = new PieData(pieDataSet);
                                    pieData.setDrawValues(true);
                                    pieData.setValueFormatter(new PercentFormatter(pieChart));
                                    pieData.setValueTextSize(12f);
                                    pieData.setValueTextColor(Color.BLACK);
                                    pieChart.setDrawHoleEnabled(true);
                                    pieChart.setUsePercentValues(true);
                                    pieChart.setEntryLabelColor(Color.BLACK);
//                                    pieChart.setCenterText("Shipment Frequently Used");
                                    pieChart.setCenterTextSize(20);
                                    Typeface comfortaa = ResourcesCompat.getFont(getContext(), R.font.comfortaa);
                                    pieChart.setCenterTextTypeface(comfortaa);
                                    pieChart.getDescription().setEnabled(false);
                                    Legend l = pieChart.getLegend();
                                    l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                                    l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
                                    l.setEnabled(true);
                                    l.setWordWrapEnabled(true);
                                    l.setFormToTextSpace(2);
                                    pieChart.setData(pieData);
                                    pieChart.setBackgroundColor(getResources().getColor(R.color.background_krem_piechart));
                                    pieChart.setRotationEnabled(true);
                                    pieChart.invalidate();
                                }
                            });
                        }
                    });
                    pieChartThread.start();
                }
            }

            @Override
            public void onFailure(Call<List<Scan>> call, Throwable t) {

            }
        });
        return v;
    }
}

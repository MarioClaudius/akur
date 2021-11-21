package com.example.akurandroid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListScanHistoryAdapter extends RecyclerView.Adapter<ListScanHistoryAdapter.ListViewHolder> {
    private ArrayList<ScanHistory> listHistory;

    public ListScanHistoryAdapter(ArrayList<ScanHistory> list){
        this.listHistory = list;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_history, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListScanHistoryAdapter.ListViewHolder holder, int position) {
        final SimpleDateFormat FORMAT_DAY_MONTH = new SimpleDateFormat("dd MMMM");
        final SimpleDateFormat FORMAT_YEAR_HOUR = new SimpleDateFormat("yyyy HH:mm");
        ScanHistory scanHistory = listHistory.get(position);
        holder.tvIdShipment.setText("#" + scanHistory.getId());
        holder.tvReceiptNumber.setText(scanHistory.getCourierName() + " - " + scanHistory.getReceiptNumber());
        holder.tvShipmentType.setText("Reguler");
        String sDate = scanHistory.getDate();
        Date date = null;
        try {
            date = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss").parse(sDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dayMonth = FORMAT_DAY_MONTH.format(date);
        String yearHour = FORMAT_YEAR_HOUR.format(date);
        holder.tvDayMonth.setText(dayMonth);
        holder.tvYearHour.setText(yearHour);
    }

    @Override
    public int getItemCount() {
        return listHistory.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{
        TextView tvIdShipment, tvReceiptNumber, tvShipmentType, tvDayMonth, tvYearHour;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdShipment = itemView.findViewById(R.id.tv_id_shipment_history);
            tvReceiptNumber = itemView.findViewById(R.id.tv_nomor_resi_history);
            tvShipmentType = itemView.findViewById(R.id.tv_shipment_type_history);
            tvDayMonth = itemView.findViewById(R.id.tv_day_month);
            tvYearHour = itemView.findViewById(R.id.tv_year_hour);
        }
    }
}

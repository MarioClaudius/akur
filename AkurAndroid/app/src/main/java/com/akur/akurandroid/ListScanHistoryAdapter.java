package com.akur.akurandroid;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListScanHistoryAdapter extends RecyclerView.Adapter<ListScanHistoryAdapter.ListViewHolder> {
    private List<Scan> listHistory;
    private Dialog dialog;

    public ListScanHistoryAdapter(List<Scan> list){
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
        Scan scan = listHistory.get(position);
        if(scan.getCourierType() == null ||scan.getCourierType().equals("-")){
            holder.tvShipmentName.setText(scan.getCourierName());
        }
        else{
            holder.tvShipmentName.setText(scan.getCourierName() + " - " + scan.getCourierType());
        }
        holder.tvReceiptNumber.setText(scan.getReceiptNumber());
        String sDate = scan.getDate();
        Date date = null;
        try {
            date = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss zzz", Locale.ENGLISH).parse(sDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dayMonth = FORMAT_DAY_MONTH.format(date);
        String yearHour = FORMAT_YEAR_HOUR.format(date);
        holder.tvDayMonth.setText(dayMonth);
        holder.tvYearHour.setText(yearHour);
        holder.btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.dialog_packet_information);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                TextView tvIdScan = dialog.findViewById(R.id.tv_id_qr_information);
                TextView tvNumberReceipt = dialog.findViewById(R.id.tv_number_receipt_information);
                TextView tvShipmentName = dialog.findViewById(R.id.tv_shipment_name_information);
                TextView tvShipmentType = dialog.findViewById(R.id.tv_shipment_type_information);
                MaterialButton doneBtn = dialog.findViewById(R.id.btn_done_information);
                tvIdScan.setText("#" + scan.getId());
                tvNumberReceipt.setText(scan.getReceiptNumber());
                tvShipmentName.setText(scan.getCourierName());
                if(scan.getCourierType() != null){
                    tvShipmentType.setText(scan.getCourierType());
                }
                else {
                    tvShipmentType.setText("-");
                }

                dialog.show();
                doneBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return listHistory.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{
        TextView tvReceiptNumber, tvShipmentName, tvDayMonth, tvYearHour;
        Button btnHistory;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvShipmentName = itemView.findViewById(R.id.tv_shipment_name_history);
            tvReceiptNumber = itemView.findViewById(R.id.tv_receipt_number_history);
            tvDayMonth = itemView.findViewById(R.id.tv_day_month);
            tvYearHour = itemView.findViewById(R.id.tv_year_hour);
            btnHistory = itemView.findViewById(R.id.btn_history);
        }
    }
}

package com.akur.akurandroid;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListDetailTrackAdapter extends RecyclerView.Adapter<ListDetailTrackAdapter.ListViewHolder> {
    private List<Scan> list;
    private Dialog dialog;

    public ListDetailTrackAdapter(List<Scan> list){
        this.list = list;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_detail_track, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListDetailTrackAdapter.ListViewHolder holder, int position) {
        final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("dd MMMM yyyy\nHH:mm");
        Scan s = list.get(position);
        holder.tvBarcode.setText(s.getReceiptNumber());
        String sDate = s.getDate();
        Date date = null;
        try {
            date = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss zzz", Locale.ENGLISH).parse(sDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dateText = FORMAT_DATE.format(date);
        holder.tvDate.setText(dateText);

        holder.imgBtnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("dd MMMM yyyy, HH:mm");
                dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.dialog_detail_track);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                TextView tvIdPacket = dialog.findViewById(R.id.tv_id_qr_information_detail);
                TextView tvReceiptNumber = dialog.findViewById(R.id.tv_number_receipt_information_detail);
                TextView tvShipmentName = dialog.findViewById(R.id.tv_shipment_name_information_detail);
                TextView tvShipmentType = dialog.findViewById(R.id.tv_shipment_type_information_detail);
                TextView tvShipmentStatus = dialog.findViewById(R.id.tv_status_information_detail);
                TextView tvDate = dialog.findViewById(R.id.tv_date_detail);
                MaterialButton doneBtn = dialog.findViewById(R.id.btn_done_information_detail);
                tvIdPacket.setText("#" + s.getId());
                tvReceiptNumber.setText(s.getReceiptNumber());
                tvShipmentName.setText(s.getCourierName());

                ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
                Call<Scan> call = apiInterface.getScanDetails(s.getId());
                call.enqueue(new Callback<Scan>() {
                    @Override
                    public void onResponse(Call<Scan> call, Response<Scan> response) {
                        Scan scan = response.body();
                        tvShipmentType.setText(scan.getCourierType());
                        tvShipmentStatus.setText(scan.getStatus());
                        tvDate.setText(scan.getDate());
                        String sDate = s.getDate();
                        Date date = null;
                        try {
                            date = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss zzz", Locale.ENGLISH).parse(sDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String datestr = FORMAT_DATE.format(date);
                        tvDate.setText(datestr);
                    }

                    @Override
                    public void onFailure(Call<Scan> call, Throwable t) {

                    }
                });
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
        return list.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{
        TextView tvBarcode, tvDate;
        ImageButton imgBtnDetail;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBarcode = itemView.findViewById(R.id.tv_detail_track_barcode);
            tvDate = itemView.findViewById(R.id.tv_detail_track_date);
            imgBtnDetail = itemView.findViewById(R.id.img_btn_detail_track);
        }
    }
}

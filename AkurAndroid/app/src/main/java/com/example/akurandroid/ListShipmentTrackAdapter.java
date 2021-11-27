package com.example.akurandroid;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListShipmentTrackAdapter extends RecyclerView.Adapter<ListShipmentTrackAdapter.ListViewHolder> {
    private ArrayList<Shipment> listShipment;

    public ListShipmentTrackAdapter(ArrayList<Shipment> listShipment){
        this.listShipment = listShipment;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_track, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListShipmentTrackAdapter.ListViewHolder holder, int position) {
        Shipment shipment = listShipment.get(position);
        holder.imgButton.setImageResource(shipment.getLogo());
        holder.imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.context, DetailTrackActivity.class);
                intent.putExtra("IdUser", shipment.getId());
                intent.putExtra("ShipmentName", shipment.getName());
                holder.context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listShipment.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{
        ImageButton imgButton;
        Context context;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            imgButton = itemView.findViewById(R.id.img_btn_track);
            context = itemView.getContext();
        }
    }
}

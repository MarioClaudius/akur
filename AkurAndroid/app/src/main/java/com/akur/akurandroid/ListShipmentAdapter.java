package com.akur.akurandroid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListShipmentAdapter extends RecyclerView.Adapter<ListShipmentAdapter.ListViewHolder> {
    private ArrayList<Shipment> listShipment;

    public ListShipmentAdapter(ArrayList<Shipment> list){
        this.listShipment = list;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_edit_shipment, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListShipmentAdapter.ListViewHolder holder, int position) {
        Shipment shipment = listShipment.get(position);
        holder.tvShipmentNumber.setText((position + 1) + ".");
        holder.tvShipmentName.setText(shipment.getName());
        holder.shipmentSwitch.setChecked(shipment.isAppear());
        holder.shipmentSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    ShipmentData.shipmentAppear[position] = true;
                }
                else {
                    ShipmentData.shipmentAppear[position] = false;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listShipment.size();
    }

    public void setItems(ArrayList<Shipment> list){
        this.listShipment = list;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{
        TextView tvShipmentName;
        TextView tvShipmentNumber;
        Switch shipmentSwitch;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvShipmentNumber = itemView.findViewById(R.id.tv_edit_shipment_number);
            tvShipmentName = itemView.findViewById(R.id.tv_edit_shipment_item_name);
            shipmentSwitch = itemView.findViewById(R.id.item_edit_shipment_switch);
        }
    }
}

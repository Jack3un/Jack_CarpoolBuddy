package com.jack.ridesharepro.Adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jack.ridesharepro.R;

public class myCarHolder extends RecyclerView.ViewHolder{
    protected TextView vehicleModel;
    protected TextView plate;
    protected Button close;
    protected TextView seatsRemaining;
    public myCarHolder(@NonNull View itemView) {
        super(itemView);
        vehicleModel = itemView.findViewById(R.id.vehicleBrand);
        plate = itemView.findViewById(R.id.Lisence);
        close = itemView.findViewById(R.id.closeCar);
        seatsRemaining = itemView.findViewById(R.id.seatsRemaining);

    }

}
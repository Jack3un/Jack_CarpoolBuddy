package com.jack.ridesharepro.Adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jack.ridesharepro.R;

public class myRideHolder extends RecyclerView.ViewHolder{
    protected TextView vehicleModel;
    protected TextView owner;
    protected TextView price;
    protected TextView plate;
    public myRideHolder(@NonNull View itemView) {
        super(itemView);
        vehicleModel = itemView.findViewById(R.id.rideBrand);
        plate = itemView.findViewById(R.id.plateNoRide);
        price = itemView.findViewById(R.id.priceTextView);
        owner = itemView.findViewById(R.id.owner);


    }

}

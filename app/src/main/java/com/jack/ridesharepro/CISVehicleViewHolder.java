package com.jack.ridesharepro;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class CISVehicleViewHolder extends RecyclerView.ViewHolder {
    protected TextView vehicleModel;
    protected TextView vehicleType;
    protected TextView price;
    protected TextView plate;
    protected Button bookRide;
    protected TextView seatsRemaining;
    public CISVehicleViewHolder(@NonNull View itemView) {
        super(itemView);
        vehicleModel = itemView.findViewById(R.id.vehicleModel);
        plate = itemView.findViewById(R.id.licensePlate);
        price = itemView.findViewById(R.id.price);
        bookRide = itemView.findViewById(R.id.bookSeat);
        seatsRemaining = itemView.findViewById(R.id.avaliSeats);
        vehicleType = itemView.findViewById(R.id.EVG);


    }
}

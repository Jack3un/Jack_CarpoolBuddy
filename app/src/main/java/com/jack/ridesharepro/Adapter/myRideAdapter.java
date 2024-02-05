package com.jack.ridesharepro.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jack.ridesharepro.BaseClasses.Vehicle;
import com.jack.ridesharepro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class myRideAdapter extends RecyclerView.Adapter<myRideHolder>{
    private ArrayList<Vehicle> allMyRides;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public myRideAdapter(ArrayList<Vehicle> allVehicles) {
        this.allMyRides = allVehicles;
    }
    @NonNull
    @Override
    public myRideHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_rides_row_view,parent,false);
        myRideHolder holder = new myRideHolder(myView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull myRideHolder holder, int position) {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        holder.vehicleModel.setText(allMyRides.get(position).getBrand());
        holder.owner.setText("Owner: " + allMyRides.get(position).getOwner());
        holder.plate.setText(allMyRides.get(position).getLicensePlate());
        holder.price.setText(String.valueOf(allMyRides.get(position).getPrice()));

    }

    @Override
    public int getItemCount() {
        return allMyRides.size();
    }
}

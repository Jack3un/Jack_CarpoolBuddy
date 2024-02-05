package com.jack.ridesharepro;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jack.ridesharepro.BaseClasses.User;
import com.jack.ridesharepro.BaseClasses.Vehicle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CISVehicleAdapter extends RecyclerView.Adapter<CISVehicleViewHolder> {

    ArrayList<Vehicle> allVehicles;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public CISVehicleAdapter(ArrayList<Vehicle> allVehicles) {
        this.allVehicles = allVehicles;
    }

    @NonNull
    @Override
    public CISVehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cis_row_view,parent,false);
        CISVehicleViewHolder holder = new CISVehicleViewHolder(myView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CISVehicleViewHolder holder, int position) {
        int lastPosition = holder.getAdapterPosition();

        holder.vehicleType.setText(allVehicles.get(lastPosition).getType());
        int seatsAvailable = allVehicles.get(position).getSeatsAvailable();
        holder.vehicleModel.setText(allVehicles.get(position).getBrand());
        holder.plate.setText( allVehicles.get(position).getType());
        holder.price.setText("Price per seat: "+String.valueOf(allVehicles.get(position).getPrice()));
        holder.seatsRemaining.setText(seatsAvailable + " seats remaining");
        holder.bookRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence text = "Seat booked successfully";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(view.getContext(), text, duration);
                toast.show();
                db.collection("users").document(mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot snapshot = task.getResult();
                            User x = snapshot.toObject(User.class);
                            ArrayList<Vehicle> rides = new ArrayList<>();
                            int mileage = allVehicles.get(lastPosition).getMileage();
                            x.setMileage(x.getMileage()+mileage);
                            Vehicle toAdd = allVehicles.get(lastPosition);
                            toAdd.setSeatsAvailable(toAdd.getSeatsAvailable()-1);
                            if (x.getUserRides()!= null){
                                rides = x.getUserRides();
                                rides.add(toAdd);
                                x.setUserRides(rides);
                            }else {
                                rides.add(toAdd);
                                x.setUserRides(rides);
                            }

                            db.collection("users").document(mAuth.getUid()).set(x);

                        }
                    });

                db.collection("Vehicles").document(allVehicles.get(lastPosition).getLicensePlate()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot snapshot = task.getResult();
                        Vehicle x = snapshot.toObject(Vehicle.class);
                        x.setSeatsAvailable(allVehicles.get(lastPosition).getSeatsAvailable()-1);


                        db.collection("Vehicles").document(allVehicles.get(lastPosition).getLicensePlate()).set(x);

                    }
                });
                holder.bookRide.setText("booked!");
                holder.seatsRemaining.setText(String.valueOf(allVehicles.get(lastPosition).getSeatsAvailable()-1 + " seats available") );
                holder.bookRide.setEnabled(false);

            }
        });

    }

    @Override
    public int getItemCount() {
        return allVehicles.size();
    }
}

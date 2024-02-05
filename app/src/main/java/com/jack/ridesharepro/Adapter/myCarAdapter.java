package com.jack.ridesharepro.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jack.ridesharepro.BaseClasses.User;
import com.jack.ridesharepro.BaseClasses.Vehicle;
import com.jack.ridesharepro.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class myCarAdapter  extends RecyclerView.Adapter<myCarHolder>{
    private ArrayList<Vehicle> allMyCars;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public myCarAdapter(ArrayList<Vehicle> allVehicles) {

        this.allMyCars = allVehicles;
    }
    @NonNull
    @Override
    public myCarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cars_row_view,parent,false);
        myCarHolder holder = new myCarHolder(myView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull myCarHolder holder, int position) {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        holder.vehicleModel.setText(allMyCars.get(position).getBrand());
        holder.seatsRemaining.setText("seats remaining: " + allMyCars.get(position).getSeatsAvailable());
        holder.plate.setText(allMyCars.get(position).getLicensePlate());


        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence text = "Vehicle closed successfully";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(view.getContext(), text, duration);
                toast.show();
                final Vehicle[] carToUpdate = {new Vehicle()};
                db.collection("Vehicles").document(allMyCars.get(holder.getAdapterPosition()).getLicensePlate()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot snapshot = task.getResult();
                        Vehicle x = snapshot.toObject(Vehicle.class);
                        x.setOpen(false);
                        carToUpdate[0] = x;
                        db.collection("Vehicles").document(allMyCars.get(holder.getAdapterPosition()).getLicensePlate()).set(x);

                    }
                });
                db.collection("users").document(mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot snapshot = task.getResult();
                        User x = snapshot.toObject(User.class);
                        ArrayList<Vehicle> current = x.getUserCars();
                        for(int i = 0; i < current.size(); i++){
                            if (current.get(i).getLicensePlate().equals(carToUpdate[0].getLicensePlate())){
                                current.set(i, carToUpdate[0]);
                                break;
                            }
                        }
                        x.setUserCars(current);
                        db.collection("users").document(mAuth.getUid()).set(x);

                    }
                });
                holder.close.setText("closed!");
                holder.close.setEnabled(false);

            }
        });

    }

    @Override
    public int getItemCount() {
        return allMyCars.size();
    }
}

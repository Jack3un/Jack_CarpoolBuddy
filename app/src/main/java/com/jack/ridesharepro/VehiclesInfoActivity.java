package com.jack.ridesharepro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jack.ridesharepro.BaseClasses.Vehicle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

public class VehiclesInfoActivity extends AppCompatActivity {

    private RecyclerView carRec;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private Button back;
    private CISVehicleAdapter x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicles_info);
        carRec = (RecyclerView) this.findViewById(R.id.carRecView);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        back = (Button) this.findViewById(R.id.back5);


        ArrayList<Vehicle> allCars = new ArrayList<>();
        db.collection("Vehicles").whereEqualTo("open",true).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        allCars.add(document.toObject(Vehicle.class));
                    }
                    for (int i =0; i<allCars.size(); i++){
                        if(allCars.get(i).getSeatsAvailable() <=0){
                            allCars.remove(i);
                        }
                    }
                    x = new CISVehicleAdapter(allCars);
                    carRec.setAdapter(x);
                    carRec.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                } else {
                    Log.w("Data", "Error getting documents.", task.getException());
                }
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VehiclesInfoActivity.this, MenuActivity.class);
                startActivity(intent);

            }
        });


    }
}
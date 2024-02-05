package com.jack.ridesharepro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jack.ridesharepro.BaseClasses.User;
import com.jack.ridesharepro.BaseClasses.Vehicle;
import com.jack.ridesharepro.Adapter.myCarAdapter;
import com.jack.ridesharepro.Adapter.myRideAdapter;

import java.util.ArrayList;

public class UserRidesInfo extends AppCompatActivity {


    private Button back;
    private RecyclerView myRec;
    private RecyclerView VecRec;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_rides_info);
        back = this.findViewById(R.id.back3);
        myRec = this.findViewById(R.id.rideRecView);
        VecRec = this.findViewById(R.id.RecVec);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        try{
            db.collection("users").document(mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@androidx.annotation.NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot snapshot = task.getResult();
                    User x = snapshot.toObject(User.class);
                    ArrayList<Vehicle> myRides = x.getUserRides();
                    ArrayList<Vehicle> myCars = x.getUserCars();
                    if (myRides !=null){
                        myRideAdapter display = new myRideAdapter(myRides);
                        myRec.setAdapter(display);
                        myRec.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                    }else {
                        Context context = getApplicationContext();
                        CharSequence text = "User does not have any rides";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                    if (myCars!=null){
                        myCarAdapter display = new myCarAdapter(myCars);
                        VecRec.setAdapter(display);
                        VecRec.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                    }else {
                        Context context = getApplicationContext();
                        CharSequence text = "user does not have any cars";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }

                }
            });
        }catch (Exception e){
            System.out.println("failed");
            System.out.println(e);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserRidesInfo.this, MenuActivity.class);
                startActivity(intent);

            }
        });
    }
}
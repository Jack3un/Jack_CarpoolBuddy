package com.jack.ridesharepro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.QuerySnapshot;
import com.jack.ridesharepro.BaseClasses.User;
import com.jack.ridesharepro.BaseClasses.Vehicle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class displayFriend extends AppCompatActivity {
    private Button back;
    private TextView friendName;
    private RecyclerView displayFriendRides;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String friendID;
    private Button toMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_friend);

        back = this.findViewById(R.id.back6);
        friendName = this.findViewById(R.id.displayFriendName);
        displayFriendRides = this.findViewById(R.id.RecViewFriend);
        toMenu = this.findViewById(R.id.toMenu);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        friendID = getIntent().getStringExtra("cleanName");

        try {
            db.collection("users").whereEqualTo("name", friendID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            User x = document.toObject(User.class);
                            friendName.setText(x.getName() + " user's rides:");
                            ArrayList<Vehicle> allFriendRides = x.getUserRides();
                            if (allFriendRides != null) {
                                CISVehicleAdapter display = new CISVehicleAdapter(allFriendRides);
                                displayFriendRides.setAdapter(display);
                                displayFriendRides.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                            } else {
                                friendName.setText(x.getName() + " does not have any rides");
                            }
                        }
                    } else {
                        // 处理查询失败的情况
                    }
                }
            });
        } catch (Exception exception) {
            System.out.println(exception);
        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(displayFriend.this, friend.class);
                startActivity(intent);

            }
        });
        toMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(displayFriend.this, MenuActivity.class);
                startActivity(intent);

            }
        });



    }
}
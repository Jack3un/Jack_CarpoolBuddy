package com.jack.ridesharepro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.jack.ridesharepro.BaseClasses.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class
MenuActivity extends AppCompatActivity {
    private Button joinFriend;
    private EditText search2;
    private Button addCar;
    private Button joinCar;
    private Button myRides;
    private Button signOut;
    private Button userProfile;
    private Button vehicleProfile;
    private TextView myId;
    private TextView mileageText;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        joinFriend = (Button) this.findViewById(R.id.friend);
        search2 = (EditText) this.findViewById(R.id.editTextTextPersonName);
        addCar = (Button) this.findViewById(R.id.addCar);
        joinCar = (Button) this.findViewById(R.id.joinRide);
        myRides = (Button) this.findViewById(R.id.rides);
        signOut = (Button) this.findViewById(R.id.signOut);
        userProfile = (Button) this.findViewById(R.id.UserProfile);
        vehicleProfile = (Button) this.findViewById(R.id.VehicleProfile);
        db = FirebaseFirestore.getInstance();
        myId = this.findViewById(R.id.textView5);
        mileageText = this.findViewById(R.id.textViewMileageText);
        mAuth = FirebaseAuth.getInstance();
        db.collection("users").document(mAuth.getUid()).get();
        db.collection("users").document(mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@androidx.annotation.NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot snapshot = task.getResult();
                User x = snapshot.toObject(User.class);
                String mileage = String.valueOf(x.getMileage());
                String name = String.valueOf(x.getName());
                myId.setText(name);
                mileageText.setText("Your Mileage: " + mileage);


            }
        });

        search2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // 如果按下的是回车键，并且动作 ID 匹配搜索动作
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    String friendName = search2.getText().toString();
                    if (!friendName.isEmpty()) {
                        Intent i = new Intent(MenuActivity.this, displayFriend.class);
                        i.putExtra("cleanName", friendName);
                        startActivity(i);
                    } else {
                        Toast.makeText(MenuActivity.this, "Please enter a name", Toast.LENGTH_SHORT).show();
                    }
                    return true; // 消耗掉这个事件
                }
                return false; // 保留其他键的默认操作
            }
        });

        addCar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, AddVehicleActivity.class);
                startActivity(intent);
            }
        });
        joinFriend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, friend.class);
                startActivity(intent);

            }
        });
        joinCar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, VehiclesInfoActivity.class);
                startActivity(intent);

            }
        });
        myRides.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, UserRidesInfo.class);
                startActivity(intent);

            }
        });

        userProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, UserProfileActivity.class);
                startActivity(intent);

            }
        });
        vehicleProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(MenuActivity.this,VehicleProfileActivity.class);
                startActivity(i);

            }
        });




    }
}
package com.jack.ridesharepro;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.jack.ridesharepro.BaseClasses.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;


public class addLocation extends AppCompatActivity {

    private Button allow;
    private Button deny;
    private double lat = 0;
    private double lon = 0;
    private FirebaseAuth mAuth;
    int LOCATION_REFRESH_TIME = 15000;
    int LOCATION_REFRESH_DISTANCE = 500;
    private LocationManager locationManager;
    private LocationListener listener;


    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            //your code here
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        allow = this.findViewById(R.id.allowPerms);
        deny = this.findViewById(R.id.DenyPerms);
        mAuth = FirebaseAuth.getInstance();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lon = location.getLongitude();
                lat = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                //
            }

            @Override
            public void onProviderEnabled(String s) {
                //
            }
        };

        allow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {


                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("users").document(mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@androidx.annotation.NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot snapshot = task.getResult();
                        User x = snapshot.toObject(User.class);
                        lat =37.422005;
                        lon =-122.084095;

                        x.setLat(lat);
                        x.setLon(lon);
                        System.out.println(lat + "persons position");
                        System.out.println(lon + "persons position");
                        System.out.println("working");
                        db.collection("users").document(mAuth.getUid()).set(x);
                        Intent intent = new Intent(addLocation.this, MenuActivity.class);
                        startActivity(intent);

                    }
                });

            }

        });
        deny.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(addLocation.this, "Unable to use application due to no location permissions",
                        Toast.LENGTH_LONG).show();

            }
        });


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }

    void configure_button() {
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(addLocation.this, ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(addLocation.this,
                ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                request_permission();
            }
        } else {
            // permission has been granted
            locationManager.requestLocationUpdates("gps", 5000, 0, listener);
        }
    }

    private void request_permission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(addLocation.this,
                ACCESS_COARSE_LOCATION)) {

            Snackbar.make(findViewById(R.id.DenyPerms), "Location permission is needed because ...",
                            Snackbar.LENGTH_LONG)
                    .setAction("retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(new String[]{ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION}, 10);
                            }
                        }
                    })
                    .show();
        } else {
            // permission has not been granted yet. Request it directly.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION}, 10);
            }
        }
    }

}
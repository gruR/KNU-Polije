package com.knupolije.ambulance;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;

public class driver_navigation extends AppCompatActivity implements OnMapReadyCallback {
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    Double latitude,longitude;

    EditText time_to_attend, distance_to_attend;
    Button finish_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_navigation);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        time_to_attend = findViewById(R.id.time_to_attend);
        distance_to_attend = findViewById(R.id.distance_to_attend);
        finish_button = findViewById(R.id.finish_button);

        latitude = Double.parseDouble(getIntent().getExtras().getString("latitude"));
        longitude = Double.parseDouble(getIntent().getExtras().getString("longitude"));
        String estimated_time = getIntent().getExtras().getString("estimated_time");
        String distance = getIntent().getExtras().getString("distance");
        Toast.makeText(this, latitude+" "+longitude+" "+distance+" "+estimated_time, Toast.LENGTH_SHORT).show();
        time_to_attend.setText(estimated_time);
        distance_to_attend.setText(distance);
        finish_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(driver_navigation.this,driver_waiting.class);
                startActivity(intent);
            }
        });

        fetchLastLocation();
    }
    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    currentLocation = location;

//                    String latitude = String.valueOf(currentLocation.getLatitude());
//                    String longitude = String.valueOf(currentLocation.getLongitude());

                    SupportMapFragment supportMapFragment = (SupportMapFragment)
                            getSupportFragmentManager().findFragmentById(R.id.maps_frame_navigation);
                    supportMapFragment.getMapAsync(driver_navigation.this);
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(latLng)
                .title("Our Position");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
        googleMap.addMarker(markerOptions);

        LatLng latLng2 = new LatLng(latitude,longitude);
        MarkerOptions markerOptions2 = new MarkerOptions().position(latLng2)
                .title("Patient Location");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng2));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng2,15));
        googleMap.addMarker(markerOptions2);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE :
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }
                break;
        }
    }
}

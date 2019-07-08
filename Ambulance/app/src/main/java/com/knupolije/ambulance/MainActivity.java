package com.knupolije.ambulance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {
    TextView main_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_tv = findViewById(R.id.main_tv);
        Toast.makeText(this, "AAA", Toast.LENGTH_SHORT).show();
        checkFirebaseToken();
    }
    void checkFirebaseToken(){
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.i("Firebase Token","Refreshed Token : "+refreshedToken);
        Toast.makeText(this, refreshedToken, Toast.LENGTH_SHORT).show();
        SessionForSavingTempData.firebase_token = refreshedToken;
    }
}

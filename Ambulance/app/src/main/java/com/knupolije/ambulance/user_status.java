package com.knupolije.ambulance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class user_status extends AppCompatActivity {
    TextView arrive_time;
    String estimated_time;
    Button user_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_status);
        arrive_time = findViewById(R.id.arrive_time);
        user_button = findViewById(R.id.user_button);

        estimated_time = getIntent().getExtras().getString("estimated_time"); //FROM Notification
        arrive_time.setText(estimated_time);
        Toast.makeText(this, estimated_time, Toast.LENGTH_SHORT).show();
        user_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(user_status.this,HomeUser.class);
                startActivity(intent);
            }
        });
    }
}

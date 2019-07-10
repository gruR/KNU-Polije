package com.knupolije.ambulance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.knupolije.ambulance.RemoteURL.APIUrl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class registration extends AppCompatActivity {
    TextView editName, editEmail, editPhonenumber,editAddress, editPassword1, editPassword2;
    Button register_button;
    APIUrl apiUrl = new APIUrl();

    String url_user_registration = apiUrl.getApi_url()+"User/Register.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        //initialization widget
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editPhonenumber = findViewById(R.id.editPhonenumber);
        editAddress = findViewById(R.id.editAddress);
        editPassword1 = findViewById(R.id.editPassword1);
        editPassword2 = findViewById(R.id.editPassword2);
        register_button = findViewById(R.id.register_button);

        //If Register Button Clicked
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }
    void registerUser(){
        final String name = editName.getText().toString();
        final String email = editEmail.getText().toString();
        final String phone_number =editPhonenumber.getText().toString();
        final String address = editAddress.getText().toString();
        final String password1 = editPassword1.getText().toString();
        String password2 = editPassword2.getText().toString();
//        Toast.makeText(this, name+" "+ email+" "+ phone_number+" "+address+" "+password1+ " "+ password2, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        //Check String Must Filled
        if(name.equals(null) || email.equals(null) || phone_number.equals(null) || address.equals(null) || password1.equals(null) || password2.equals(null)) {
            Toast.makeText(this, "Form Must Be Filled", Toast.LENGTH_SHORT).show();


        }else{
            //Password And Confirmation Password Must Be Same
            Toast.makeText(this, "Checking Password", Toast.LENGTH_SHORT).show();
            if(password1.equals(password2)){
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url_user_registration,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String success = jsonObject.getString("Success");
                                    String message = jsonObject.getString("Message");
                                    if (success.equals("true")) {
                                        Toast.makeText(registration.this, "Register Successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(registration.this,HomeUser.class);
                                        startActivity(intent);
                                    }else if(success.equals("false")){
                                        Toast.makeText(registration.this, "Failed", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(registration.this, "Error On JSON User", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                                Toast.makeText(registration.this, "Error Volley "+error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                )
                {
                    @Override
                    protected Map<String,String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("name",name);
                        params.put("email",email);
                        params.put("phonenumber",phone_number);
                        params.put("address",address);
                        params.put("password",password1);
                        params.put("fbtoken", FirebaseInstanceId.getInstance().getToken());
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);
            }else{
                Toast.makeText(this, "Password Not Same, Check Again", Toast.LENGTH_SHORT).show();
            }
        }

    }
}

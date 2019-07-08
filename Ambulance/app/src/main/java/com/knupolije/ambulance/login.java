package com.knupolije.ambulance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
//import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.iid.InstanceIdResult;
import com.knupolije.ambulance.RemoteURL.APIUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {
    APIUrl apiUrl = new APIUrl();
    String url_login_driver = apiUrl.getApi_url()+"Driver/Login.php";
    String url_login_user =apiUrl.getApi_url()+"User/Login.php";

    Button login_btn,signup_btn;
    TextView editId,editPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_login);
        Toast.makeText(this, "AAA", Toast.LENGTH_SHORT).show();
        login_btn = findViewById(R.id.login_btn);
        signup_btn = findViewById(R.id.signup_btn);
        editId = findViewById(R.id.editId);
        editPassword = findViewById(R.id.editPassword);
        checkFirebaseToken();

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TAG","TES LOGCAT");
                System.out.println("TES123");
                System.out.println("Refreshed Token"+SessionForSavingTempData.firebase_token);
                Intent intent = new Intent(login.this,registration.class);
                startActivity(intent);
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(login.this, "Go To Home User Page", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(login.this,HomeUser.class);
//                startActivity(intent);
                String id = editId.getText().toString();
                String password = editPassword.getText().toString();
                if(id.isEmpty() || password.isEmpty()){
                    Toast.makeText(login.this, "Username or Password Required", Toast.LENGTH_SHORT).show();
                }else{
                    authDriver(id,password);
                }


            }
        });

    }
    void checkFirebaseToken(){
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.i("Firebase Token","Refreshed Token : "+refreshedToken);
        Toast.makeText(this, refreshedToken, Toast.LENGTH_SHORT).show();
        SessionForSavingTempData.firebase_token = refreshedToken;
    }
    private void authDriver(final String id, final String password){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_login_driver,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("Success");
//                            Toast.makeText(login.this, success, Toast.LENGTH_SHORT).show();
                            if (success.equals("true")) {
                                Toast.makeText(login.this, "Login is Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(login.this,driver_waiting.class);
                                startActivity(intent);
                            }else if(success.equals("false")){
                                //If Data Not Found on Table Driver, We Must Check On Table User
                                authUser(id,password);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(login.this, "Error On Auth Driver", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(login.this, "Error Volley Driver "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email",id);
                params.put("password",password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void authUser(final String id, final String password){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_login_user,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("Success");
                            if (success.equals("true")) {
                                Toast.makeText(login.this, "Login is Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(login.this,HomeUser.class);
                                startActivity(intent);
                            }else if(success.equals("false")){
                                Toast.makeText(login.this, "Username Or Password Not Registered", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(login.this, "Error On Auth User", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(login.this, "Error Volley "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email",id);
                params.put("password",password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

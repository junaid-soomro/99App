package com.example.dark.a99app;

import android.*;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.example.dark.a99app.Admin.AdminDashboard;
import com.example.dark.a99app.Employee.EmployeeDashboard;
import com.example.dark.a99app.SessionManager.SessionManager;
import com.example.dark.a99app.Singletons.RequestQueues;
import com.example.dark.a99app.User.UserDashboard;
import com.example.dark.a99app.Volley.LoginRequest;

import org.json.JSONObject;

public class Login extends AppCompatActivity {

    ImageView reg_user;

    TextInputLayout username,pass;

    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        checkPermission();
        initiliaze();
        checkSession();
        login();

    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(Login.this, Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_DENIED || ContextCompat.checkSelfPermission(Login.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(Login.this, new String[]{Manifest.permission.RECORD_AUDIO, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
        }
    }

    private boolean validation(String Name,String Pass){

        if(Name.equals(""))
        {
            username.setError("Enter username");
            return false;

        }

        else if(Pass.equals(""))

        {

            pass.setError("Enter password");
            return false;

        }
        username.setErrorEnabled(false);
        pass.setErrorEnabled(false);
        return true;
    }

    @Override
    public void onBackPressed(){
        finishAffinity();
    }

    private void checkSession() {

        if(new SessionManager(Login.this).CheckIfSessionExist()){

            if(new SessionManager(Login.this).getType().equals("employee")){

                startActivity(new Intent(Login.this,EmployeeDashboard.class));
                finish();
            }
            else if(new SessionManager(Login.this).getType().equals("user")){

                startActivity(new Intent(Login.this,UserDashboard.class));
                finish();
            }else if(new SessionManager(Login.this).getType().equals("admin")){
                startActivity(new Intent(Login.this,AdminDashboard.class));
                finish();
            }
        }
    }

    private void login() {

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String EMAIL = username.getEditText().getText().toString();
                String PASS = pass.getEditText().getText().toString();
                if(validation(EMAIL,PASS))
                {
                    final ProgressDialog progressDialog = new ProgressDialog(Login.this);
                    progressDialog.setTitle("Please Wait");
                    progressDialog.setMessage("Logging You In");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    LoginRequest loginRequest = new LoginRequest(EMAIL, PASS, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("Login Response", response);
                            progressDialog.dismiss();

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getBoolean("success")) {


                                    new SessionManager(Login.this,jsonObject.getString("ID"),jsonObject.getString("Name"),
                                            jsonObject.getString("Phone"),jsonObject.getString("Address")
                                            ,jsonObject.getString("Username"),jsonObject.getString("type"),jsonObject.getString("national_id"),
                                            jsonObject.getString("image"),jsonObject.getString("Dept_id"));

                                    Toast.makeText(Login.this, "Login success", Toast.LENGTH_SHORT).show();

                                    if(jsonObject.getString("type").equals("employee")){



                                        startActivity(new Intent(Login.this,EmployeeDashboard.class));
                                        finish();
                                        return;
                                    }else if(jsonObject.getString("type").equals("user")){

                                        startActivity(new Intent(Login.this,UserDashboard.class));
                                        finish();
                                        return;
                                    }
                                    else if(jsonObject.getString("type").equals("admin")){

                                        startActivity(new Intent(Login.this,AdminDashboard.class));
                                        finish();
                                        return;
                                    }
                                    Toast.makeText(Login.this, jsonObject.getString("type"), Toast.LENGTH_SHORT).show();

                                } else {
                                    if(jsonObject.getString("status").equals("INVALID"))
                                        Toast.makeText(Login.this, "User Not Found", Toast.LENGTH_SHORT).show();
                                    else {

                                        Toast.makeText(Login.this, "Passwords dont match.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(Login.this, "Bad Response From Server "+e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            if (error instanceof ServerError)
                                Toast.makeText(Login.this, "Server Error", Toast.LENGTH_SHORT).show();
                            else if (error instanceof TimeoutError)
                                Toast.makeText(Login.this, "Connection Timed Out", Toast.LENGTH_SHORT).show();
                            else if (error instanceof NetworkError)
                                Toast.makeText(Login.this, "Bad Network Connection"+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    RequestQueues.getInstance(Login.this).addToRequestQue(loginRequest);
                }
            }
        });



    }

    private void initiliaze() {


        reg_user = (ImageView)findViewById(R.id.reg_user);
        reg_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,Register.class));
            }
        });
        username = (TextInputLayout)findViewById(R.id.login_email);
        login = (Button)findViewById(R.id.Login);
        pass = (TextInputLayout)findViewById(R.id.reg_password);
    }
}

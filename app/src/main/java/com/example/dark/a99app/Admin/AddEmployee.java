package com.example.dark.a99app.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.dark.a99app.Adapters.DepartmentAdapter;
import com.example.dark.a99app.Constants;
import com.example.dark.a99app.R;
import com.example.dark.a99app.SessionManager.SessionManager;
import com.example.dark.a99app.Singletons.RequestQueues;
import com.example.dark.a99app.Volley.add_edit_deleteEmployee;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddEmployee extends AppCompatActivity {

    TextInputLayout name,phone,username,password;

    Button add_employee;

    String NAME,PHONE,USERNAME,PASSWORD,DEPT,ADMIN_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        initiliaze();

        work();
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(AddEmployee.this,AdminDashboard.class));
    }

    private void work() {
        DEPT = new SessionManager(AddEmployee.this).getDept_id();
        ADMIN_ID = new SessionManager(AddEmployee.this).getId();

        add_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NAME = name.getEditText().getText().toString();
                PHONE = phone.getEditText().getText().toString();
                USERNAME = username.getEditText().getText().toString();
                PASSWORD = password.getEditText().getText().toString();

                if(NAME.equals("") || PHONE.equals("") || USERNAME.equals("") || PASSWORD.equals("")){
                    Toast.makeText(AddEmployee.this, "إملئ الحقول", Toast.LENGTH_SHORT).show();
                    return;
                }

                addEmployee();

            }
        });


    }

    private void addEmployee() {

        final ProgressDialog progressDialog = new ProgressDialog(AddEmployee.this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Adding Employee");
        progressDialog.setCancelable(false);
        progressDialog.show();

        add_edit_deleteEmployee request2 = new add_edit_deleteEmployee(NAME, PHONE, USERNAME, PASSWORD, DEPT,ADMIN_ID, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    progressDialog.dismiss();
                try {
                    if(new JSONObject(response).getBoolean("status")){
                        Toast.makeText(AddEmployee.this, "Added Employee,", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(AddEmployee.this,AdminDashboard.class));
                    }
                    else{
                        Toast.makeText(AddEmployee.this, "Failed to add.,", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Log.i("Exception", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.i("Volley error", error.getMessage());
            }
        }) ;

        RequestQueues.getInstance(AddEmployee.this).addToRequestQue(request2);
    }


    private void initiliaze() {

        name = (TextInputLayout)findViewById(R.id.emp_name);
        phone = (TextInputLayout)findViewById(R.id.emp_phone);
        username = (TextInputLayout)findViewById(R.id.emp_username);
        password = (TextInputLayout)findViewById(R.id.emp_password);

        add_employee = (Button)findViewById(R.id.add_employee);

    }
}

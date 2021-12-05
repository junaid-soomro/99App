package com.example.dark.a99app.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.dark.a99app.Adapters.DepartmentAdapter;
import com.example.dark.a99app.Constants;
import com.example.dark.a99app.R;
import com.example.dark.a99app.Singletons.RequestQueues;
import com.example.dark.a99app.Volley.add_edit_deleteEmployee;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EditEmployee extends AppCompatActivity {

    TextInputLayout name,phone,username,password;

    Button edit_employee;

    CheckBox up_pass;

    String ID,NAME,PHONE,USERNAME,PASSWORD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee);
        initiliaze();

        fetchValues();
        update();
    }

    private void update() {

        edit_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NAME = name.getEditText().getText().toString();
                PHONE = phone.getEditText().getText().toString();
                USERNAME = username.getEditText().getText().toString();
                if (password.getEditText().getText().toString().equals("")) {

                    final ProgressDialog progressDialog = new ProgressDialog(EditEmployee.this);
                    progressDialog.setTitle("الرجاء الإنتظار");
                    progressDialog.setMessage("تحديث الموظف");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    add_edit_deleteEmployee request = new add_edit_deleteEmployee(ID, NAME, PHONE, USERNAME, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("Response", response.toString());
                            progressDialog.dismiss();
                            try {
                                if (new JSONObject(response).getBoolean("status")) {
                                    Toast.makeText(EditEmployee.this, "تحديث الموظف,", Toast.LENGTH_SHORT).show();
                                    finish();
                                    startActivity(new Intent(EditEmployee.this, AdminDashboard.class));
                                } else {
                                    Toast.makeText(EditEmployee.this, "الرقم السري غير صحيح,", Toast.LENGTH_SHORT).show();

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
                    });
                    RequestQueues.getInstance(EditEmployee.this).addToRequestQue(request);



                } else {
                    PASSWORD = password.getEditText().getText().toString();

                    final ProgressDialog progressDialog = new ProgressDialog(EditEmployee.this);
                    progressDialog.setTitle("الرجاء الإنتظار");
                    progressDialog.setMessage("تحديث الموظف");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    add_edit_deleteEmployee request = new add_edit_deleteEmployee(ID, NAME, PHONE, USERNAME, PASSWORD, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("Response", response.toString());
                            progressDialog.dismiss();
                            try {
                                if (new JSONObject(response).getBoolean("status")) {
                                    Toast.makeText(EditEmployee.this, "تم النحديث,", Toast.LENGTH_SHORT).show();
                                    finish();
                                    startActivity(new Intent(EditEmployee.this, AdminDashboard.class));
                                } else {
                                    Toast.makeText(EditEmployee.this, "فشل في التحديث,", Toast.LENGTH_SHORT).show();

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
                    });
                    RequestQueues.getInstance(EditEmployee.this).addToRequestQue(request);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(EditEmployee.this,AdminDashboard.class));
    }

    private void fetchValues() {

        NAME = getIntent().getStringExtra("name");
        USERNAME = getIntent().getStringExtra("username");
        PHONE = getIntent().getStringExtra("phone");
        ID = getIntent().getStringExtra("ID");

        name.getEditText().setText(NAME);
        username.getEditText().setText(USERNAME);
        phone.getEditText().setText(PHONE);
    }



    private void initiliaze()  {

        up_pass = (CheckBox)findViewById(R.id.up_pass);
        name = (TextInputLayout)findViewById(R.id.edit_emp_name);
        phone = (TextInputLayout)findViewById(R.id.edit_emp_phone);
        username = (TextInputLayout)findViewById(R.id.edit_emp_username);
        password = (TextInputLayout)findViewById(R.id.edit_emp_password);
        password.setVisibility(View.INVISIBLE);
        up_pass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(up_pass.isChecked()){
                    password.setVisibility(View.VISIBLE);
                }else{
                    password.setVisibility(View.INVISIBLE);
                }
            }
        });

        edit_employee = (Button)findViewById(R.id.edit_employee);
    }
}

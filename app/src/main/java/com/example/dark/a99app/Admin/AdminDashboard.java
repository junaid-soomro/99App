package com.example.dark.a99app.Admin;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.dark.a99app.Login;
import com.example.dark.a99app.R;
import com.example.dark.a99app.SessionManager.SessionManager;
import com.example.dark.a99app.Singletons.RequestQueues;
import com.example.dark.a99app.Volley.changeAdminPass;

import org.json.JSONException;
import org.json.JSONObject;

public class AdminDashboard extends AppCompatActivity {

    ImageView logout;

    Button manage,change_pass;

    String current_pass,new_pass,new_pass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        logout = (ImageView)findViewById(R.id.logout_admin);

        manage = (Button)findViewById(R.id.manage_employee);
        change_pass = (Button)findViewById(R.id.change_pass);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                new SessionManager(AdminDashboard.this).Logout();
                startActivity(new Intent(AdminDashboard.this, Login.class));
            }
        });

        manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminDashboard.this);
                builder.setTitle("إختر أمرا");
                builder.setMessage("ماهو طلبك؟");

                builder.setPositiveButton("إضافة موظف", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        startActivity(new Intent(AdminDashboard.this,AddEmployee.class));
                    }
                });
                builder.setNegativeButton("تعديل أو حدف موظف", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                        startActivity(new Intent(AdminDashboard.this,ManageEmployee.class));
                    }
                });
                builder.setNeutralButton("الرجوع", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //do nothing.
                    }
                });
                builder.show();
            }
        });

        change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminDashboard.this);
                builder.setTitle("تغير الرقم السري");
                builder.setMessage("إدخل الرقم السري الحالي");
                final EditText input = new EditText(AdminDashboard.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);
                builder.setPositiveButton("التالي", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                             if(input.getText().toString().equals("")){
                                 Toast.makeText(AdminDashboard.this, "Please enter valid password.", Toast.LENGTH_SHORT).show();
                             }else{
                                 current_pass = input.getText().toString();
                                 validate();
                             }
                    }
                });
                builder.setNeutralButton("إلغاء", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //do nothing
                    }
                });
                builder.show();
            }
        });

    }

    private void validate() {

        AlertDialog.Builder builder3 = new AlertDialog.Builder(AdminDashboard.this);
        builder3.setTitle("تغير الرقم السري");
        builder3.setMessage("إدخل الرقم السري الجديد");
        final EditText input3 = new EditText(AdminDashboard.this);
        input3.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder3.setView(input3);
        builder3.setPositiveButton("التالي", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(input3.getText().toString().equals("")){
                    Toast.makeText(AdminDashboard.this, "ادخل رقم سري صحيح", Toast.LENGTH_SHORT).show();
                }else{
                    new_pass = input3.getText().toString();
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(AdminDashboard.this);
                    builder2.setTitle("تغير الرقم السري");
                    builder2.setMessage("إعادة الرقم السري");
                    final EditText input2 = new EditText(AdminDashboard.this);
                    input2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    builder2.setView(input2);
                    builder2.setPositiveButton("تأكيد", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(input2.getText().toString().equals("")){
                                Toast.makeText(AdminDashboard.this, "إختر رقم سري صالح", Toast.LENGTH_SHORT).show();
                            }else{
                                new_pass2 = input2.getText().toString();
                                if(new_pass.equals(new_pass2)){
                                    updatePass();
                                }else{
                                    Toast.makeText(AdminDashboard.this, "لا يوجد تطابق", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                    builder2.setNeutralButton("إلغاء", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //do nothing
                        }
                    });
                    builder2.show();
                }
            }
        });
        builder3.setNeutralButton("إلغاء", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //do nothing
            }
        });
        builder3.show();

    }

    private void updatePass() {

        final ProgressDialog progressDialog = new ProgressDialog(AdminDashboard.this);
        progressDialog.setTitle("الرجاء الإنتظار");
        progressDialog.setMessage("تحديث الرقم السري");
        progressDialog.setCancelable(false);
        progressDialog.show();
        changeAdminPass request = new changeAdminPass(new SessionManager(AdminDashboard.this).getId(), current_pass,new_pass, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.i("Response", response.toString());

                try {
                    if(new JSONObject(response).getBoolean("PASSWORD")) {
                        Toast.makeText(AdminDashboard.this, "Incorrect current password.", Toast.LENGTH_SHORT).show();
                    }
                    if(new JSONObject(response).getBoolean("success")){
                        Toast.makeText(AdminDashboard.this, "Password updated", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Log.i("Exception", e.getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.i("Vollet error", error.getMessage());
            }
        }) ;
        RequestQueues.getInstance(AdminDashboard.this).addToRequestQue(request);
    }
}

package com.example.dark.a99app.Employee;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.dark.a99app.EditProfile;
import com.example.dark.a99app.Login;
import com.example.dark.a99app.R;
import com.example.dark.a99app.SessionManager.SessionManager;

public class EmployeeDashboard extends AppCompatActivity {

    ImageView logoff;

    Button all_req;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_dashboard);
        initiliaze();
        throwuser();
    }

    private void throwuser() {

        /*edit_prof_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(EmployeeDashboard.this, EditProfile.class));
            }
        });*/

        all_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(EmployeeDashboard.this,AllRequests.class));
            }
        });

    }

    private void initiliaze() {
        //edit_prof_admin = (ImageView) findViewById(R.id.edit_prof_admin);
        all_req = (Button)findViewById(R.id.requests);
        logoff = (ImageView)findViewById(R.id.logoff_employee);

        logoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SessionManager(EmployeeDashboard.this).Logout();
                startActivity(new Intent(EmployeeDashboard.this, Login.class));
                finish();
            }
        });
    }
}

package com.example.dark.a99app.Admin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.sip.SipSession;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.dark.a99app.Adapters.EmployeeAdapter;
import com.example.dark.a99app.Constants;
import com.example.dark.a99app.Employee.AllRequests;
import com.example.dark.a99app.R;
import com.example.dark.a99app.SessionManager.SessionManager;
import com.example.dark.a99app.Singletons.RequestQueues;
import com.example.dark.a99app.Volley.add_edit_deleteEmployee;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ManageEmployee extends AppCompatActivity {

    ListView listView;

    ArrayList<EmployeeModel> arrayList = new ArrayList<>();

    EmployeeModel model;

    EmployeeAdapter adapter;

    int EMP_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_employee);
        listView = (ListView)findViewById(R.id.e_list);
        fetchEmp();
        action();
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(ManageEmployee.this,AdminDashboard.class));
    }

    private void action() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EMP_ID = i;

                AlertDialog.Builder builder = new AlertDialog.Builder(ManageEmployee.this);
                builder.setTitle("اختر مهمه");
                builder.setMessage("حذف او تعديل الموظف");
                builder.setPositiveButton("حذف", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(ManageEmployee.this,EditEmployee.class);
                                intent.putExtra("name",arrayList.get(EMP_ID).getE_name());
                                intent.putExtra("username",arrayList.get(EMP_ID).getE_username());
                                intent.putExtra("phone",arrayList.get(EMP_ID).getE_phone());
                                intent.putExtra("ID",arrayList.get(EMP_ID).getE_id());
                                startActivity(intent);
                                finish();
                    }
                });
                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final ProgressDialog progressDialog = new ProgressDialog(ManageEmployee.this);
                        progressDialog.setTitle("الرجاء الانتظار");
                        progressDialog.setMessage("حذف موظف");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        add_edit_deleteEmployee request = new add_edit_deleteEmployee(arrayList.get(EMP_ID).e_id, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                    progressDialog.dismiss();

                                try {
                                    if(new JSONObject(response).getBoolean("status")){

                                        Toast.makeText(ManageEmployee.this, "تم الحذف.", Toast.LENGTH_SHORT).show();
                                            arrayList.remove(EMP_ID);
                                            adapter.notifyDataSetChanged();
                                    }else{
                                        Toast.makeText(ManageEmployee.this, "تعذر الحذف.", Toast.LENGTH_SHORT).show();
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
                        RequestQueues.getInstance(ManageEmployee.this).addToRequestQue(request);
                    }
                });
                builder.setNeutralButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //do nothing
                    }
                });
                builder.show();
            }
        });

    }

    private void fetchEmp() {
        final ProgressDialog progressDialog = new ProgressDialog(ManageEmployee.this);
        progressDialog.setTitle("الرجاء الانتظار");
        progressDialog.setMessage("استرجاع بيانات الموظفين");
        progressDialog.setCancelable(false);
        progressDialog.show();
        final add_edit_deleteEmployee request = new add_edit_deleteEmployee(new SessionManager(ManageEmployee.this).getId(), "lol", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
progressDialog.dismiss();
                Log.i("Response", response.toString());
            int count = 0;
                JSONObject object;
                try {
                    JSONArray array = new JSONArray(response);
                    while (count < array.length()) {

                        object = array.getJSONObject(count);

                        model = new EmployeeModel(object.getString("e_id"), object.getString("e_name"), object.getString("e_username"),
                                object.getString("e_phone"));
                        arrayList.add(model);
                        count++;
                    }
                }catch(JSONException e){
                        Log.i("Exception", e.getMessage());
                    }


                setValues(arrayList);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.i("Volley error", error.getMessage());
            }
        });
RequestQueues.getInstance(ManageEmployee.this).addToRequestQue(request);
    }

    private void setValues(ArrayList<EmployeeModel> arrayList) {
        this.arrayList = arrayList;
        adapter = new EmployeeAdapter(arrayList,ManageEmployee.this);
        listView.setAdapter(adapter);
    }
}

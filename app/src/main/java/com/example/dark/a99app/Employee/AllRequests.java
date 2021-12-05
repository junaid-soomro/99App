package com.example.dark.a99app.Employee;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.dark.a99app.R;
import com.example.dark.a99app.SessionManager.SessionManager;
import com.example.dark.a99app.Singletons.RequestQueues;
import com.example.dark.a99app.Volley.helpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AllRequests extends AppCompatActivity {

    ListView list;

    ArrayList<RequestsModel> arrayList = new ArrayList<>();
    RequestsAdapter adapter;
    RequestsModel model;

    int ID = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_requests);

        list = (ListView)findViewById(R.id.all_employee_list);
        work();
        listonclick();
    }

    private void listonclick() {

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ID = i;
                Button check = (Button)view.findViewById(R.id.check_req);
                check.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(AllRequests.this,ShowRequest.class);
                        intent.putExtra("name",arrayList.get(ID).getUsername());
                        intent.putExtra("vn",arrayList.get(ID).getVn());
                        intent.putExtra("lat",arrayList.get(ID).getLat());
                        intent.putExtra("lon",arrayList.get(ID).getLon());
                        intent.putExtra("comment",arrayList.get(ID).getComment());
                        intent.putExtra("req_id",arrayList.get(ID).getReq_id());
                        intent.putExtra("user_id",arrayList.get(ID).getUser_id());
                        startActivity(intent);
                    }
                });
            }
        });


    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(AllRequests.this,EmployeeDashboard.class));
    }

    private void work() {

        final ProgressDialog progressDialog = new ProgressDialog(AllRequests.this);
        progressDialog.setTitle("الرجاء الانتظار");
        progressDialog.setMessage("جلب الطلبات");
        progressDialog.setCancelable(false);
        progressDialog.show();

        final helpRequest request = new helpRequest(new SessionManager(AllRequests.this).getDept_id(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    progressDialog.dismiss();

                    int count = 0;

                try {
                    JSONArray array = new JSONArray(response);
                    JSONObject object;
                    while (count<array.length()){

                        object = array.getJSONObject(count);
                        model = new RequestsModel(object.getString("req_id"),object.getString("user_id"),object.getString("lat"),object.getString("lon")
                                ,object.getString("comment"),object.getString("vn"),object.getString("status"),object.getString("user_image"),
                                object.getString("name"),object.getString("address"),object.getString("phone"));
                        arrayList.add(model);
                        count++;
                    }
                    setvalues(arrayList);
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Log.i("Exception", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d("Volley error", error.getMessage());
            }
        });
        RequestQueues.getInstance(AllRequests.this).addToRequestQue(request);
    }

    private void setvalues(ArrayList<RequestsModel> arrayList) {

        this.arrayList = arrayList;
        adapter = new RequestsAdapter(arrayList,AllRequests.this);
        list.setAdapter(adapter);
    }
}

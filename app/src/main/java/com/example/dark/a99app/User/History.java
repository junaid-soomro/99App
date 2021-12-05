package com.example.dark.a99app.User;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.dark.a99app.Adapters.HistoryAdapter;
import com.example.dark.a99app.R;
import com.example.dark.a99app.SessionManager.SessionManager;
import com.example.dark.a99app.Singletons.RequestQueues;
import com.example.dark.a99app.Volley.HistoryRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {


    ArrayList<HistoryModel> arrayList = new ArrayList<>();
    HistoryModel model;
    HistoryAdapter adapter;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        listView = (ListView)findViewById(R.id.history_list);

        fetch();

    }

    private void fetch() {
        final ProgressDialog progressDialog = new ProgressDialog(History.this);
        progressDialog.setTitle("الرجاء الانتظار");
        progressDialog.setMessage("طلب المساعده قيد التنفيذ");
        progressDialog.setCancelable(false);
        progressDialog.show();

        final HistoryRequest request = new HistoryRequest(new SessionManager(History.this).getId(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
progressDialog.dismiss();
                int count =0;
                JSONObject object;

                try {
                    JSONArray array = new JSONArray(response);

                    while(count<array.length()){
                        object = array.getJSONObject(count);
                        model = new HistoryModel(object.getString("name"),object.getString("department"),object.getString("date"),object.getString("status"));
                        arrayList.add(model);
                        count++;
                    }

setValues(arrayList);
                } catch (JSONException e) {
                    Log.i("Exception", e.getMessage());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.i("Volley", error.getLocalizedMessage());
            }
        });

        RequestQueues.getInstance(History.this).addToRequestQue(request);
    }

    private void setValues(ArrayList<HistoryModel> arrayList) {
        this.arrayList = arrayList;
        adapter = new HistoryAdapter(this.arrayList,History.this);
        listView.setAdapter(adapter);
    }
}

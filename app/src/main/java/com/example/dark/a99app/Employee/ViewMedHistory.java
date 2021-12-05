package com.example.dark.a99app.Employee;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.midi.MidiInputPort;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.dark.a99app.Constants;
import com.example.dark.a99app.R;
import com.example.dark.a99app.Singletons.RequestQueues;
import com.example.dark.a99app.Volley.fetchMedHistory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewMedHistory extends AppCompatActivity {

    String user_id;

    ListView diseases_list;

    TextView blood_group_med;

    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> adapter;

    EditText other_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_med_history);
        user_id = getIntent().getStringExtra("ID");
        other_view = (EditText)findViewById(R.id.other_view_med);
        other_view.setInputType(InputType.TYPE_NULL);
        initiliaze();

        work();

    }

    private void fetchDieseases() {
        final ProgressDialog progressDialog = new ProgressDialog(ViewMedHistory.this);
        progressDialog.setTitle("الرجاء الانتظار");
        progressDialog.setMessage("جلب البيانات");
        progressDialog.setCancelable(false);
        progressDialog.show();

        fetchMedHistory request = new fetchMedHistory(user_id, "lol", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("Response", response.toString());
                int count =0;
                JSONObject object;
                try {
                    progressDialog.dismiss();
                        JSONArray array = new JSONArray(response);
                     while (count<array.length()) {
                         object = array.getJSONObject(count);
                         arrayList.add(object.getString("diseases"));
                         count++;
                     }
                    setDiseases(arrayList);
                } catch (Exception e) {
                        Log.i("Exception", e.getMessage());
                    }
                }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.i("Volley Exception",error.getMessage());
            }
        });
        RequestQueues.getInstance(ViewMedHistory.this).addToRequestQue(request);

    }

    private void setDiseases(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
        adapter = new ArrayAdapter<String>(ViewMedHistory.this,android.R.layout.simple_list_item_1,arrayList);
        diseases_list.setAdapter(adapter);

    }

    private void work() {
        final ProgressDialog progressDialog = new ProgressDialog(ViewMedHistory.this);
        progressDialog.setTitle("الرجاء الانتظار");
        progressDialog.setMessage("جلب الملف الطبي");
        progressDialog.setCancelable(false);
        progressDialog.show();

            fetchMedHistory request = new fetchMedHistory(user_id, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
progressDialog.dismiss();

                    Log.i("Response", response.toString());

                    try{
                        if(new JSONObject(response).getBoolean("med")){
                            Toast.makeText(ViewMedHistory.this, "تم اخفاء الملف الطبي", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                       ///     startActivity(new Intent(ViewMedHistory.this,ShowRequest.class));
                        }fetchDieseases();
                        blood_group_med.setText(new JSONObject(response).getString("blood"));
                        other_view.setText(new JSONObject(response).getString("other"));
                    }
                    catch (Exception e){
                        Log.i("Exception",e.getMessage());
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Log.i("Volley error", error.getMessage());
                }
            });
        RequestQueues.getInstance(ViewMedHistory.this).addToRequestQue(request);
    }

    private void initiliaze() {


        blood_group_med = (TextView)findViewById(R.id.blood_group_med);
        diseases_list = (ListView)findViewById(R.id.diseases_list);

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

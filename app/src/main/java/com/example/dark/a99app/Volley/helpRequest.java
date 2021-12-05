package com.example.dark.a99app.Volley;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.dark.a99app.Constants;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by abd on 20-Mar-18.
 */

public class helpRequest extends StringRequest {

    private Map<String,String> parameters;

    public helpRequest(String id, ArrayList<String> dept_id, String lat, String lon, String comment, String vn, String date_time,String access, Response.Listener<String> listener,
                       Response.ErrorListener errorListener){
        super(Request.Method.POST,new Constants().helpme,listener,errorListener);

        parameters = new HashMap<>();

        JSONArray array = new JSONArray(dept_id);

        parameters.put("user_id",id);
        parameters.put("dept_id",array.toString());
        parameters.put("lat",lat);
        parameters.put("lon",lon);
        parameters.put("comment",comment);
        parameters.put("vn",vn);
        parameters.put("date_time",date_time);
        parameters.put("med_access",access);

    }

    public helpRequest(String id, Response.Listener<String> listener,
                       Response.ErrorListener errorListener) {
        super(Request.Method.POST, new Constants().helpme, listener, errorListener);

        parameters = new HashMap<>();
        parameters.put("dept",id);
    }

    public helpRequest(String id,String status, Response.Listener<String> listener,
                       Response.ErrorListener errorListener) {
        super(Request.Method.POST, new Constants().respond, listener, errorListener);

        parameters = new HashMap<>();
        parameters.put("req_id",id);
        parameters.put("status",status);
    }

    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}

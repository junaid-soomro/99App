package com.example.dark.a99app.Volley;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.dark.a99app.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by abd on 07-Apr-18.
 */

public class fetchMedHistory extends StringRequest {

Map<String,String> parameters;


    public fetchMedHistory(String user_id, Response.Listener<String> listener,
                                   Response.ErrorListener errorListener) {
        super(Method.POST, new Constants().fetchMed, listener, errorListener);

        parameters = new HashMap<>();

        parameters.put("user_id",user_id);
    }
    public fetchMedHistory(String user_id,String smthng, Response.Listener<String> listener,
                           Response.ErrorListener errorListener) {
        super(Method.POST, new Constants().fetchMed, listener, errorListener);

        parameters = new HashMap<>();

        parameters.put("id",user_id);
    }

    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}

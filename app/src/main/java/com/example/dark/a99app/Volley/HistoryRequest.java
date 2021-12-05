package com.example.dark.a99app.Volley;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.dark.a99app.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by abd on 01-May-18.
 */

public class HistoryRequest extends StringRequest {

    private Map<String,String> parameters;

    public HistoryRequest(String id, Response.Listener<String> listener,
                       Response.ErrorListener errorListener) {
        super(Request.Method.POST, new Constants().history, listener, errorListener);

        parameters = new HashMap<>();
        parameters.put("user",id);
    }

    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }

}

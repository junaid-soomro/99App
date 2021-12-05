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

public class changeAdminPass extends StringRequest {

    Map<String,String> parameters;

    public changeAdminPass(String admin_id,String pass,String new_pass, Response.Listener<String> listener,
                                   Response.ErrorListener errorListener) {
        super(Method.POST, new Constants().admin_pass, listener, errorListener);

        parameters = new HashMap<>();

        parameters.put("admin_id",admin_id);
        parameters.put("password",pass);
        parameters.put("new_password",new_pass);
    }
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }

}

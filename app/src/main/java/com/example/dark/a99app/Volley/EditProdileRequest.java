package com.example.dark.a99app.Volley;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.dark.a99app.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by abd on 21-Jan-18.
 */

public class EditProdileRequest extends StringRequest {

    private Map<String,String> parameters;
//update pass with image
    public EditProdileRequest(String id, String name, String username, String password, String new_password, String phone, String address,String image, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, new Constants().EDIT_PROFILE, listener, errorListener);
        parameters = new HashMap<>();
        parameters.put("ID",id);
        parameters.put("Name",name);
        parameters.put("Username",username);
        parameters.put("password",password);
        parameters.put("new_password",new_password);
        parameters.put("address",address);
        parameters.put("phone",phone);
        parameters.put("image",image);
    }
    //update pass without image
    public EditProdileRequest(String id, String name, String username, String password, String new_password, String phone, String address, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, new Constants().EDIT_PROFILE, listener, errorListener);
        parameters = new HashMap<>();
        parameters.put("ID",id);
        parameters.put("Name",name);
        parameters.put("Username",username);
        parameters.put("password",password);
        parameters.put("new_password",new_password);
        parameters.put("address",address);
        parameters.put("phone",phone);

    }
//update without image without pass
    public EditProdileRequest(String id, String name, String phone, String address, String username, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, new Constants().EDIT_PROFILE, listener, errorListener);
        parameters = new HashMap<>();
        parameters.put("ID",id);
        parameters.put("Name",name);
        parameters.put("phone",phone);
        parameters.put("address",address);
        parameters.put("Username",username);
    }
    //update with image without pass
    public EditProdileRequest(String id, String name, String phone, String address, String username,String image, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, new Constants().EDIT_PROFILE, listener, errorListener);
        parameters = new HashMap<>();
        parameters.put("ID",id);
        parameters.put("Name",name);
        parameters.put("phone",phone);
        parameters.put("address",address);
        parameters.put("Username",username);
        parameters.put("image",image);
    }

    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }

}

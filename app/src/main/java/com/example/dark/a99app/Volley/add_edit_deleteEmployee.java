package com.example.dark.a99app.Volley;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.dark.a99app.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by abd on 26-Mar-18.
 */

public class add_edit_deleteEmployee extends StringRequest {

    Map<String,String> parameters;

//adding employee
    public add_edit_deleteEmployee(String name, String phone, String username, String password, String dept,String admin_id, Response.Listener<String> listener,
                                   Response.ErrorListener errorListener){
        super(Method.POST,new Constants().add_edit_deleteEmployee,listener,errorListener);

    parameters = new HashMap<>();

    parameters.put("name",name);
    parameters.put("phone",phone);
    parameters.put("username",username);
    parameters.put("password",password);
    parameters.put("dept_id",dept);
    parameters.put("admin_id",admin_id);


    }//deleting employee
    public add_edit_deleteEmployee(String delete_id, Response.Listener<String> listener,
                                   Response.ErrorListener errorListener) {
        super(Method.POST, new Constants().add_edit_deleteEmployee, listener, errorListener);

        parameters = new HashMap<>();

        parameters.put("delete_id",delete_id);
    }

    //fetching all department employee
    public add_edit_deleteEmployee(String admin_id,String smthng, Response.Listener<String> listener,
                                   Response.ErrorListener errorListener) {
        super(Method.POST, new Constants().add_edit_deleteEmployee, listener, errorListener);

        parameters = new HashMap<>();

        parameters.put("admin_id",admin_id);
    }


    //editing employee with password
    public add_edit_deleteEmployee(String e_id,String name, String phone, String username, String password, Response.Listener<String> listener,
                                   Response.ErrorListener errorListener) {
        super(Method.POST, new Constants().add_edit_deleteEmployee, listener, errorListener);

        parameters = new HashMap<>();

        parameters.put("e_id",e_id);
        parameters.put("namee",name);
        parameters.put("phone",phone);
        parameters.put("username",username);
        parameters.put("password",password);

    }
    public add_edit_deleteEmployee(String e_id,String name, String phone, String username, Response.Listener<String> listener,
                                   Response.ErrorListener errorListener) {
        super(Method.POST, new Constants().add_edit_deleteEmployee, listener, errorListener);

        parameters = new HashMap<>();

        parameters.put("e_id",e_id);
        parameters.put("namee",name);
        parameters.put("phone",phone);
        parameters.put("username",username);

    }

        protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }

}

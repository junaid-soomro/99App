package com.example.dark.a99app.Volley;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.dark.a99app.*;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by abd on 17-Jan-18.
 */

public class RegisterRequest extends StringRequest {

    ArrayList<String> arrayList = new ArrayList<>();

    private Map<String, String> parameters;
    public RegisterRequest(String username, String name, String password, String national_id, String address, String phone,String image, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, new Constants().REGISTER, listener, errorListener);
        parameters = new HashMap<>();

        parameters.put("national_id", national_id);
        parameters.put("name", name);
        parameters.put("password", password);
        parameters.put("address",address);
        parameters.put("username", username);
        parameters.put("phone", phone);
        parameters.put("image",image);
            }

    public RegisterRequest(String user_id, ArrayList<String> diseases, String other_details, String blood_group, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, new Constants().REGISTER, listener, errorListener);
        parameters = new HashMap<>();
        parameters.put("user_id",user_id);

        for(int i=0;i<diseases.size();i++){

            this.arrayList.add(diseases.get(i));

        }

        JSONArray array = new JSONArray(this.arrayList);
        Log.d("Array", array.toString());

        parameters.put("diseases",array.toString());
        parameters.put("other_details",other_details);
        parameters.put("blood_group",blood_group);


    }
    public RegisterRequest(String user_id, ArrayList<String> diseases, String other_details, String blood_group,String lol, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, new Constants().update_med_history, listener, errorListener);
        parameters = new HashMap<>();
        parameters.put("edit",lol);
        parameters.put("user_id",user_id);

        for(int i=0;i<diseases.size();i++){

            this.arrayList.add(diseases.get(i));

        }

        JSONArray array = new JSONArray(this.arrayList);
        Log.d("Array", array.toString());

        parameters.put("diseases",array.toString());
        parameters.put("other_details",other_details);
        parameters.put("blood_group",blood_group);


    }


        @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}

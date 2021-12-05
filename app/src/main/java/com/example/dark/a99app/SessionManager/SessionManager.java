package com.example.dark.a99app.SessionManager;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.dark.a99app.*;
/**
 * Created by abd on 21-Jan-18.
 */

public class SessionManager {

    private String id, name , phone,address,username,type,national_id,image,dept_id;
    SharedPreferences session ;

    public String getImage() {
        return image;
    }

    public String getDept_id() {
        return dept_id;
    }

    public String getType() {
        return type;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public SessionManager(){}

    public SessionManager(Context c)
    {
        session= c.getSharedPreferences(new Constants().SESSION,Context.MODE_PRIVATE);
        this. id = session.getString("id",null);
        this.name = session.getString("name",null);
        this.address = session.getString("address",null);
        this.phone = session.getString("phone",null);
        this.username = session.getString("username",null);
        this.type = session.getString("type",null);
        this.national_id = session.getString("national_id",null);
        this.image = session.getString("image",null);
        this.dept_id = session.getString("dept_id",null);
    }

    public SessionManager(Context c , String id, String name, String phone, String address, String username,String type,String national_id,String image,String dept_id)
    {
        session= c.getSharedPreferences(new Constants().SESSION,Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = session.edit();
        editor.putString("id", id);
        editor.putString("name",name);
        editor.putString("phone",phone);
        editor.putString("address",address);
        editor.putString("username",username);
        editor.putString("type",type);
        editor.putString("national_id",national_id);
        editor.putString("image",image);
        editor.putString("dept_id",dept_id);
        editor.commit();

        new SessionManager(c);

    }

    public String getimage() {
        return image;
    }

    public String getNational_id() {
        return national_id;
    }

    public boolean CheckIfSessionExist(){
        if (session.contains("id"))
            return true;
        else
            return  false;

    }
    public void Logout (){
        SharedPreferences.Editor editor = session.edit();
        editor.clear();
        editor.commit();

        this.image=null;
        this.national_id = null;
        this.id=null;
        this.name=null;
        this.phone=null;
        this.address = null;
        this.username = null;
    }




}

package com.example.dark.a99app.Admin;

/**
 * Created by abd on 26-Mar-18.
 */

public class EmployeeModel {

  String department_id,department;

  String e_id,e_name,e_username,e_phone;

    public EmployeeModel(String department_id, String department) {
        this.department_id = department_id;
        this.department = department;
    }

    public EmployeeModel(String e_id, String e_name, String e_username, String e_phone) {
        this.e_id = e_id;
        this.e_name = e_name;
        this.e_username = e_username;
        this.e_phone = e_phone;
    }


    public String getDepartment_id() {
        return department_id;
    }

    public String getDepartment() {
        return department;
    }

    public String getE_id() {
        return e_id;
    }

    public String getE_name() {
        return e_name;
    }

    public String getE_username() {
        return e_username;
    }

    public String getE_phone() {
        return e_phone;
    }

}

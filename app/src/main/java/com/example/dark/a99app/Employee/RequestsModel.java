package com.example.dark.a99app.Employee;

/**
 * Created by abd on 21-Mar-18.
 */

public class RequestsModel {


    String req_id,user_id,address,lat,lon,comment,vn,request_status,username,user_image,phone;


    public RequestsModel(String req_id, String user_id, String lat, String lon, String comment, String vn, String request_status,String image,String
                         username,String address,String phone) {
        this.req_id = req_id;
        this.user_id = user_id;
        this.lat = lat;
        this.lon = lon;
        this.comment = comment;
        this.vn = vn;
        this.request_status = request_status;
        this.user_image = image;
        this.username = username;
        this.address = address;
        this.phone = phone;
       // this.date_time = date_time;
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

    public String getReq_id() {
        return req_id;
    }

    public String image(){
        return user_image;    }

    public String getUser_id() {
        return user_id;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public String getComment() {
        return comment;
    }

    public String getVn() {
        return vn;
    }

    public String getRequest_status() {
        return request_status;
    }


}

package com.example.dark.a99app.Employee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dark.a99app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by abd on 21-Mar-18.
 */

public class RequestsAdapter extends BaseAdapter {

    ArrayList<RequestsModel> requests = new ArrayList<>();
    Context context;

    TextView emp_name, emp_phone,emp_address;
    ImageView emp_image;
    Button check_req;

    public RequestsAdapter(ArrayList<RequestsModel> requests, Context context) {
        this.requests = requests;
        this.context = context;
    }

    @Override
    public int getCount() {
        return requests.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.req_list,null,false);
        initiliaze(view);

        emp_name.setText(requests.get(i).getUsername());
        emp_phone.setText(requests.get(i).getPhone());
        emp_address.setText(requests.get(i).getAddress());

        Picasso.with(context).load(requests.get(i).image()).into(emp_image);

        return view;
    }



    private void initiliaze(View view) {

        emp_name  = (TextView)view.findViewById(R.id.emp_list_name);
        emp_phone = (TextView)view.findViewById(R.id.emp_list_phone);
        emp_address = (TextView)view.findViewById(R.id.emp_list_address);
        emp_image = (ImageView)view.findViewById(R.id.emp_list_image);

        check_req = (Button)view.findViewById(R.id.check_req);
        check_req.setFocusable(false);
    }
}

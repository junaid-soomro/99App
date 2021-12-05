package com.example.dark.a99app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dark.a99app.Admin.EmployeeModel;
import com.example.dark.a99app.R;

import java.util.ArrayList;

/**
 * Created by abd on 26-Mar-18.
 */

public class EmployeeAdapter extends BaseAdapter {

    TextView e_name,e_username,e_phone;

    ArrayList<EmployeeModel> arrayList = new ArrayList<>();
    Context context;

    public EmployeeAdapter(ArrayList<EmployeeModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
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
        view = LayoutInflater.from(context).inflate(R.layout.emp_list_show,null,false);
        initiliaze(view);
        e_name.setText(arrayList.get(i).getE_name());
        e_phone.setText(arrayList.get(i).getE_phone());
        e_username.setText(arrayList.get(i).getE_username());

        return view;
    }

    private void initiliaze(View view) {

        e_name = (TextView)view.findViewById(R.id.e_list_name);
        e_username = (TextView)view.findViewById(R.id.e_list_username);
        e_phone = (TextView)view.findViewById(R.id.e_list_phone);
    }
}

package com.example.dark.a99app.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dark.a99app.Admin.EmployeeModel;
import com.example.dark.a99app.R;

import java.util.ArrayList;

/**
 * Created by abd on 26-Mar-18.
 */

public class DepartmentAdapter extends BaseAdapter {

    TextView dept_id,dept_name;

    ArrayList<EmployeeModel> arrayList =new ArrayList<>();
    Context context;


    public DepartmentAdapter(@NonNull Context context, ArrayList<EmployeeModel> resource) {

        this.arrayList = resource;
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


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.dept_view,null,false);
        initiliaze(convertView);
        setvalues(position);
        return convertView;

    }

    private void setvalues(int position) {
        dept_id.setText(arrayList.get(position).getDepartment_id());
        dept_name.setText(arrayList.get(position).getDepartment());
    }

    private void initiliaze(View convertView) {
        dept_id = (TextView)convertView.findViewById(R.id.dept_id_show);
        dept_name = (TextView)convertView.findViewById(R.id.dept_name_show);
    }
}

package com.example.dark.a99app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dark.a99app.R;
import com.example.dark.a99app.User.HistoryModel;

import java.util.ArrayList;

/**
 * Created by abd on 30-Apr-18.
 */

public class HistoryAdapter extends BaseAdapter {

    ArrayList<HistoryModel> arrayList = new ArrayList<>();
    Context context;

    TextView name,dept,date_time,status;

    public HistoryAdapter(ArrayList<HistoryModel> arrayList, Context context) {
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
        view = LayoutInflater.from(context).inflate(R.layout.history_view,null,false);

        name = (TextView)view.findViewById(R.id.user_list_name);
        dept = (TextView)view.findViewById(R.id.emp_list_dept);
        date_time = (TextView)view.findViewById(R.id.date_time);
        status  = (TextView)view.findViewById(R.id.req_status);

        name.setText(arrayList.get(i).getName());
        dept.setText(arrayList.get(i).getDept());
        date_time.setText(arrayList.get(i).getDate_time());
        status.setText(arrayList.get(i).getStatus());

        return view;
    }
}

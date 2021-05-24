package com.example.baitap_lab09_crud_api;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterList extends BaseAdapter {
    private int layout;
    private Context context;
    private ArrayList<Employees> arrayList;

    public AdapterList(int layout, Context context, ArrayList<Employees> arrayList) {
        this.layout = layout;
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.list_item,null);

        Employees employees = arrayList.get(position);

        TextView tvId = convertView.findViewById(R.id.tvEmpID);
        TextView tvName = convertView.findViewById(R.id.tvEmpName);
        TextView tvGen = convertView.findViewById(R.id.tvEmpGen);
        TextView tvAge = convertView.findViewById(R.id.tvEmpAge);
        EditText edtSearch = convertView.findViewById(R.id.edtSearchID);

        tvId.setText("ID: "+employees.getId()+"");
        tvName.setText("Name: "+employees.getName());
        tvGen.setText("Gender: "+employees.getGender());
        tvAge.setText("Age: " +employees.getAge()+"");

        return convertView;
    }
}

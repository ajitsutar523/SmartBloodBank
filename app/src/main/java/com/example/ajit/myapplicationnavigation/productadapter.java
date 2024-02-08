package com.example.ajit.myapplicationnavigation;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class productadapter extends BaseAdapter {
    private Context mcontext;
    private List<product> listview1;

    public productadapter(Context mcontext, List<product> listview1) {
        this.mcontext = mcontext;
        this.listview1 = listview1;
    }

    @Override
    public int getCount() {
        return listview1.size();
    }

    @Override
    public Object getItem(int position) {
        return listview1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=View.inflate(mcontext,R.layout.productlist,null);
        TextView nm=(TextView)v.findViewById(R.id.name);
        TextView mob=(TextView)v.findViewById(R.id.phone);
        TextView bg=(TextView)v.findViewById(R.id.bloodgrp);
        TextView city=(TextView)v.findViewById(R.id.city123);
        nm.setText(listview1.get(position).getName());
        mob.setText(listview1.get(position).getMobile());
        bg.setText(listview1.get(position).getBloodgroup());
        city.setText(listview1.get(position).getCity());
        v.setTag(listview1.get(position).getBloodgroup());
        return v;
    }
}

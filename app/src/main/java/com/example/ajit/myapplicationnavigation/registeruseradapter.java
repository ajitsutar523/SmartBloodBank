package com.example.ajit.myapplicationnavigation;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class registeruseradapter extends BaseAdapter {
    private Context mcontext;
    private List<registeruser1> listview1;

    public registeruseradapter(Context mcontext, List<registeruser1> listview1) {
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
        View v=View.inflate(mcontext,R.layout.listregisteruser,null);
        TextView nm=(TextView)v.findViewById(R.id.name);
        TextView mob=(TextView)v.findViewById(R.id.phoneno);
        TextView bg=(TextView)v.findViewById(R.id.bg);
        TextView addr=(TextView)v.findViewById(R.id.addreregister);
        TextView email=(TextView)v.findViewById(R.id.cityregister);
        TextView donor=(TextView)v.findViewById(R.id.donorregister);
        TextView city=(TextView)v.findViewById(R.id.emailregister);


        nm.setText(listview1.get(position).getName());
        mob.setText(listview1.get(position).getMobile());
        bg.setText(listview1.get(position).getBloodgroup());
        addr.setText(listview1.get(position).getAddr());
        email.setText(listview1.get(position).getEmail());
        donor.setText(listview1.get(position).getDonor());
        city.setText(listview1.get(position).getCity());

        v.setTag(listview1.get(position).getId());
        return v;
    }
}

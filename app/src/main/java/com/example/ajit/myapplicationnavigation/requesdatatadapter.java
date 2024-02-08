package com.example.ajit.myapplicationnavigation;

import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import static android.content.Context.CLIPBOARD_SERVICE;

public class requesdatatadapter extends BaseAdapter {
    private Context mcontext;
    private List<requestdataproduct> listview1;

    public requesdatatadapter(Context mcontext, List<requestdataproduct> listview1) {
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
        View v=View.inflate(mcontext,R.layout.requestproduct,null);

        TextView nm=(TextView)v.findViewById(R.id.name);
        TextView date=(TextView)v.findViewById(R.id.date);
        TextView bg=(TextView)v.findViewById(R.id.bg);
        TextView order1=(TextView)v.findViewById(R.id.orderid1);
        TextView phoneno=(TextView)v.findViewById(R.id.phoneno);
        TextView status=(TextView)v.findViewById(R.id.status);


        nm.setText(listview1.get(position).getNm());
        date.setText(listview1.get(position).getDate());
        bg.setText(listview1.get(position).getBg());
        order1.setText(listview1.get(position).getOrder());
        phoneno.setText(listview1.get(position).getMob());
        status.setText(listview1.get(position).getStatus());

        v.setTag(listview1.get(position).getOrder());



        return v;
    }
}

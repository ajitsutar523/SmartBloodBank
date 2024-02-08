package com.example.ajit.myapplicationnavigation;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchDonorF extends Fragment {
    public SearchDonorF() {
        // Required empty public constructor
    }
private int id1=1;

    ProgressBar pb;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    FirebaseUser user;

    ListView lv1;
    List<product> list;
    Spinner sp1;
    productadapter productadapter1;
    Button search;
    View view;
    FirebaseDatabase firebaseDatabase;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view=inflater.inflate(R.layout.fragment_search_donor,container,false);

        pb=(ProgressBar)view.findViewById(R.id.progressbar);



        sp1=(Spinner)view.findViewById(R.id.spinner13);
        ArrayAdapter<String> aa2=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.bloodgroup));
        aa2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(aa2);
        search=(Button)view.findViewById(R.id.search);
        lv1=(ListView)view.findViewById(R.id.listview1);
        list=new ArrayList<>();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                check();
                pb.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }
    public void check()
    {
        reference=FirebaseDatabase.getInstance().getReference("user");
        final String searchblood=sp1.getSelectedItem().toString();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    pb.setVisibility(View.GONE);
                    user1  user12=ds.getValue(user1.class);
                    // list.add(new product(user12.getName(),user12.getMobile(),user12.getBloodgroup(),id1));
                    String donor1=user12.getDonor();
                    String a1=user12.getName();
                    String a2=user12.getMobile();
                    String a3=user12.getBloodgroup();
                    String a4=user12.getCity();
                    if(a3.equals(searchblood) && donor1.equals("Yes")) {
                        list.add(new product(a1, a2, a3, id1,a4));
                        id1 += 1;

                    }
                }

                productadapter1=new productadapter(getContext(),list);
                lv1.setAdapter(productadapter1);
              if(lv1.getCount()==0)
              {
                  Snackbar.make(view,"Donor Not Found",Snackbar.LENGTH_LONG).show();
              }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                pb.setVisibility(View.GONE);
            }
        });


        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(Intent.ACTION_DIAL);
                String noo=view.getTag().toString();
                i.setData(Uri.parse("tel:"+noo));
                startActivity(i);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
           // Snackbar.make(view,"Your are Online",Snackbar.LENGTH_LONG).show();
            // fetch data
        } else {
            // display error

            Snackbar.make(view,"You are not connected to the network",Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onStart();
                }
            }).show();
        }
    }

}

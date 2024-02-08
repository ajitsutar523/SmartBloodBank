package com.example.ajit.myapplicationnavigation;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class history extends Fragment {
    public history() {
        // Required empty public constructor
    }

    ProgressBar pb;

    ListView lv1;
    List<requestdataproduct> list;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    FirebaseUser user;
    String id1;
    requesdatatadapter requesdatatadapter1;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_history, container, false);
        pb=(ProgressBar)view.findViewById(R.id.progressbar);
        pb.setVisibility(View.VISIBLE);
        lv1 = (ListView) view.findViewById(R.id.lv12);
        list = new ArrayList<>();

        list.clear();




        Log.d("mytag", "onCreateView: 11");
        try {
            reference = FirebaseDatabase.getInstance().getReference("bloodrequest").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            Log.d("mytag", "onCreateView: 22");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.d("mytag", "onCreateView: 33");
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {


                        String a1 = ds.child("nm").getValue().toString();
                        String a2 =  ds.child("mob").getValue().toString();
                        String a3 =  ds.child("order").getValue().toString();
                        String a4 =  ds.child("bg").getValue().toString();
                        String a5 =  ds.child("date").getValue().toString();
                        String a6 =   ds.child("status").getValue().toString();
                        list.add(new requestdataproduct(a4, a1, a5, a3,a2,a6));


                    }

                    requesdatatadapter1 = new requesdatatadapter(getContext(), list);
                    lv1.setAdapter(requesdatatadapter1);
                    pb.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    pb.setVisibility(View.GONE);
                }
            });

        }catch (Exception e)
        {
            Toast.makeText(getContext(),e.getMessage().toString(),Toast.LENGTH_LONG).show();
            Log.d("mytag", "onCreateView:"+e.getMessage().toString());
        }

            return view;

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
package com.example.ajit.myapplicationnavigation;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import android.widget.TableLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class BloodStockF extends Fragment  {
    String a,a1,ab,ab1,o,o1,b,b1;
     private ListView lv1;
    ArrayList<String> list = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    FirebaseUser user;

    ProgressBar pb;

    TextView  tvHeaderName ;
    public BloodStockF() {
        // Required empty public constructor

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_blood_stock,container,false);
        lv1=(ListView)view.findViewById(R.id.lv123);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_expandable_list_item_1, list);

        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        reference=FirebaseDatabase.getInstance().getReference().child("bloodstock");

        pb=(ProgressBar)view.findViewById(R.id.progressbar);
        pb.setVisibility(View.VISIBLE);
        return view;
        }

    @Override
    public void onStart() {
        super.onStart();
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
         //   Snackbar.make(getView(), "Your are Online", Snackbar.LENGTH_LONG).show();
            // fetch data
        } else {
            // display error

            Snackbar.make(getView(), "You are not connected to the network", Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onStart();
                }
            }).show();
        }
            pb.setVisibility(View.VISIBLE);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    adapter.clear();
                    a = dataSnapshot.child("A+").getValue().toString();
                    a1 = dataSnapshot.child("A-").getValue().toString();
                    ab = dataSnapshot.child("AB+").getValue().toString();
                    ab1 = dataSnapshot.child("AB-").getValue().toString();
                    o = dataSnapshot.child("O+").getValue().toString();
                    o1 = dataSnapshot.child("O-").getValue().toString();
                    b = dataSnapshot.child("B+").getValue().toString();
                    b1 = dataSnapshot.child("B-").getValue().toString();

                    adapter.add("       A+                                                   " + a);
                    adapter.add("       A-                                                   " + a1);

                    adapter.add("       AB+                                                 " + ab);
                    adapter.add("       AB-                                                  " + ab1);

                    adapter.add("       B+                                                   " + b);
                    adapter.add("       B-                                                   " + b1);

                    adapter.add("       O+                                                   " + o);
                    adapter.add("       O-                                                   " + o1);
                    lv1.setAdapter(adapter);


                    pb.setVisibility(View.GONE);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
                    pb.setVisibility(View.GONE);
                }
            });
        }

    }

package com.example.ajit.myapplicationnavigation;


import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.graphics.Color.rgb;


/**
 * A simple {@link Fragment} subclass.
 */
public class RequestBloodF extends Fragment {

    Calendar calendar;

    ProgressBar pb;
    SimpleDateFormat simpleDateFormat;
    Spinner sp;
    Button b1;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    FirebaseUser user;
    TextView avail;
    Button check;
    String nm, mob, bg, date1, addr, city, email;
    int orderno;
    String order = getorderno();
    View view;

    public RequestBloodF() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_request_blood, container, false);

        pb = (ProgressBar) view.findViewById(R.id.progressbar);

        sp = (Spinner) view.findViewById(R.id.spinner12);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.bloodgroup));
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(aa);
        b1 = (Button) view.findViewById(R.id.request);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        avail = (TextView) view.findViewById(R.id.checkavilable);
        check = (Button) view.findViewById(R.id.check);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                checkavail();
            }
        });
        b1.setEnabled(false);


        reference = FirebaseDatabase.getInstance().getReference("user").child(user.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mob = dataSnapshot.child("mobile").getValue().toString();
                nm = dataSnapshot.child("name").getValue().toString();
                city = dataSnapshot.child("city").getValue().toString();
                addr = dataSnapshot.child("addr").getValue().toString();
                email = dataSnapshot.child("email").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                request();
            }
        });
        return view;
    }


    public void checkavail() {

        reference = FirebaseDatabase.getInstance().getReference().child("bloodstock");
        bg = sp.getSelectedItem().toString();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String a = dataSnapshot.child(bg).getValue().toString();
                int b = Integer.parseInt(a);
                if (b > 0) {

                    avail.setText("Avilable");
                    avail.setTextColor(rgb(0, 128, 0));
                    b1.setEnabled(true);
                    pb.setVisibility(View.GONE);
                } else {
                    avail.setText("Not Avilable");
                    avail.setTextColor(rgb(255, 0, 0));
                    b1.setEnabled(false);
                    pb.setVisibility(View.GONE);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
                pb.setVisibility(View.GONE);
            }
        });


    }

    public String getorderno() {
        reference = FirebaseDatabase.getInstance().getReference("Requestid");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                order = dataSnapshot.child("Orderid").getValue().toString();
                orderno = Integer.parseInt(order);
                Log.d("mytag", "onDataChange: " + order + " " + orderno);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return order;
    }

    public void request() {

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        date1 = simpleDateFormat.format(calendar.getTime());
        order = getorderno();
        Log.d("mytag", "in request: " + order + " " + orderno);
        //order=reference.push().getKey();
        String status = "Incomplete";
        requestdata requestdata1 = new requestdata(nm, bg, mob, date1, order, status, email, addr, city);
        reference = FirebaseDatabase.getInstance().getReference("bloodrequest").child(user.getUid()).child(order);
        reference.setValue(requestdata1)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Snackbar.make(view, "Requested", Snackbar.LENGTH_LONG).show();
                            reference = FirebaseDatabase.getInstance().getReference("Requestid");
                            reference.child("Orderid").setValue(orderno + 1);
                            pb.setVisibility(View.GONE);
                        } else {
                            Snackbar.make(view, task.getException().getMessage(), Snackbar.LENGTH_LONG).show();

                            pb.setVisibility(View.GONE);
                        }
                    }
                });
    }


    @Override
    public void onStart() {
        super.onStart();
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
           // Snackbar.make(getView(), "Your are Online", Snackbar.LENGTH_SHORT).show();
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
    }
}
package com.example.ajit.myapplicationnavigation;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
public class bloodtransaction extends Fragment {
    public bloodtransaction() {
        // Required empty public constructor
    }
    ProgressBar pb;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    FirebaseUser user;
    int bottle2;
    Spinner sp1;
    Button confirm;
    TextInputLayout emaili, name, mobile, addr, city, orderid, orderda, shippinda, bottols,bg;
    List<String> orderlist;
    AutoCompleteTextView actv1;
    ArrayAdapter<String> arrayAdapter;
    int a,a1,b,b1,ab,ab1,o,o1;
    String orderid1,date1;
    View view;
    int diff;
    String userid1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_bloodtransaction, container, false);
        pb=view.findViewById(R.id.progressbar);
        bloodstock();
        actv1=view.findViewById(R.id.ordertextview);
        bg=view.findViewById(R.id.blood);
        orderid=view.findViewById(R.id.autv);
        name = view.findViewById(R.id.name);
        mobile = view.findViewById(R.id.mobile);
        emaili = view.findViewById(R.id.email);
        addr = view.findViewById(R.id.addr);
        city = view.findViewById(R.id.city);
        orderda = view.findViewById(R.id.orderdate);
        shippinda = view.findViewById(R.id.shipingdate);
        bottols = view.findViewById(R.id.bottles);
        orderid1=actv1.getText().toString();
        orderlist = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, orderlist);
        //adding order no in edittext
        reference = FirebaseDatabase.getInstance().getReference("bloodrequest");
        arrayAdapter.clear();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String id = ds.getKey();
                    reference = FirebaseDatabase.getInstance().getReference("bloodrequest").child(id);
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                String id = ds.getKey();
                                String sts=ds.child("status").getValue().toString();
                                if(sts.equals("Incomplete")) {
                                    orderlist.add(id);
                                    Log.d("mytag1", "onDataChange: " + id);
                                }
                            }
                            actv1.setAdapter(arrayAdapter);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        //orderid onclick listener
        actv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String id1= parent.getItemAtPosition(position).toString();
                reference = FirebaseDatabase.getInstance().getReference("bloodrequest");
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                           String  id12=ds.getKey();
                            reference = FirebaseDatabase.getInstance().getReference("bloodrequest").child(id12).child(id1);
                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                        String a1 = dataSnapshot.child("nm").getValue().toString();
                                        String a2 = dataSnapshot.child("mob").getValue().toString();
                                        String a3 = dataSnapshot.child("order").getValue().toString();
                                        String a4 = dataSnapshot.child("bg").getValue().toString();
                                        String a5 = dataSnapshot.child("date").getValue().toString();
                                        String a6 = dataSnapshot.child("status").getValue().toString();
                                        String a7 = dataSnapshot.child("city").getValue().toString();
                                        String a8 = dataSnapshot.child("addr").getValue().toString();
                                        String a9 = dataSnapshot.child("email").getValue().toString();
                                        stattus=a6;
                                        Log.d("mytag", "onCreateView: " + a1 + a2 + a3 + a4 + a6 + a5);
                                        name.getEditText().setText(a1);
                                        emaili.getEditText().setText(a9);
                                        mobile.getEditText().setText(a2);
                                        addr.getEditText().setText(a8);
                                        city.getEditText().setText(a7);
                                        orderda.getEditText().setText(a5);
                                        bottols.getEditText().setText("1");
                                        bg.getEditText().setText(a4);
                                        calendar=Calendar.getInstance();
                                        simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy");
                                        date1=simpleDateFormat.format(calendar.getTime());
                                        shippinda.getEditText().setText(date1);
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            });
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
        //onclick
        confirm =view.findViewById(R.id.Confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateEmail() | !validateoederid() | !validatename() | !validatmobile() | !validateaddr()| !validatecity()| !validateorerdate()| !validateshippingdate()| !validatebootle()) {
                    orderid1=actv1.getText().toString();
                    return;
                }else
                    {
                    Log.d("mytag", "in upload:");
                    pb.setVisibility(View.VISIBLE);
                    final String id, nm, email1, mo1, address1, city1, date1, date2, blood, bottl1;
                    id = actv1.getText().toString();
                    nm = name.getEditText().getText().toString();
                    email1 = emaili.getEditText().getText().toString();
                    aa = emaili.getEditText().getText().toString();
                    mo1 = mobile.getEditText().getText().toString();
                    address1 = addr.getEditText().getText().toString();
                    city1 = city.getEditText().getText().toString();
                    date1 = orderda.getEditText().getText().toString();
                    date2 = shippinda.getEditText().getText().toString();
                    blood = bg.getEditText().getText().toString();
                    bottl1 = bottols.getEditText().getText().toString();
                    bottle2 = Integer.parseInt(bottl1);
                    final String bloodgrp = bg.getEditText().getText().toString();
                    final int bottleno = Integer.parseInt(bottols.getEditText().getText().toString());
                    reference = FirebaseDatabase.getInstance().getReference("bloodstock");
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String bg1 = dataSnapshot.child(bloodgrp).getValue().toString();
                            int bg1no = Integer.parseInt(bg1);
                            Log.d("mytag", "check:" + bloodgrp + " " + bg1no + " " + bottleno);
                            diff=bg1no-bottleno;
                            if(diff<0)
                            {
                                Toast.makeText(getContext(), "blood not available", Toast.LENGTH_LONG).show();
                                pb.setVisibility(View.GONE);
                                bottols.getEditText().getText().clear();
                            }
                            else
                            {
                                    //uploading data
                                    bloodtransactionsata_Out insertdata =
                                            new bloodtransactionsata_Out(id, nm, email1, mo1, address1, city1, date1, date2, blood, bottl1);
                                    FirebaseDatabase.getInstance().getReference("BloodTransactionOut")
                                            .child(id).setValue(insertdata);
                                    //changing status of request
                                    reference = FirebaseDatabase.getInstance().getReference("user");
                                    reference.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                                if (ds.child("email").getValue().toString().equals(aa)) {
                                                    userid1 = ds.child("id").getValue().toString();
                                                    Log.d("mytag", "changestatus: " + userid1 + " " + id);
                                                    reference = FirebaseDatabase.getInstance().getReference("bloodrequest").child(userid1).child(id);
                                                    reference.child("status").setValue("Complete");
                                                    Snackbar.make(view,"transaction done Succesfully",Snackbar.LENGTH_SHORT).show();
                                                    return;
                                                }
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    });
                                    //deducting blood stock
                                    reference = FirebaseDatabase.getInstance().getReference("bloodstock");
                                    switch (blood) {
                                        case "A+":
                                            reference.child("A+").setValue(a - bottle2);
                                            break;
                                        case "A-":
                                            reference.child("A-").setValue(a1 - bottle2);
                                            break;
                                        case "B+":
                                            reference.child("B+").setValue(b - bottle2);
                                            break;
                                        case "B-":
                                            reference.child("B-").setValue(b1 - bottle2);
                                            break;
                                        case "AB+":
                                            reference.child("AB+").setValue(ab - bottle2);
                                            break;
                                        case "AB-":
                                            reference.child("AB-").setValue(ab1 - bottle2);
                                            break;
                                        case "O+":
                                            reference.child("O+").setValue(o - bottle2);
                                            break;
                                        case "O-":
                                            reference.child("O-").setValue(o1 - bottle2);
                                            break;
                                    }
                                    pb.setVisibility(View.GONE);
                                    //clear all edittext
                                    arrayAdapter.remove(actv1.getText().toString());
                                    actv1.clearListSelection();
                                    name.getEditText().getText().clear();
                                    emaili.getEditText().getText().clear();
                                    mobile.getEditText().getText().clear();
                                    addr.getEditText().getText().clear();
                                    city.getEditText().getText().clear();
                                    orderda.getEditText().getText().clear();
                                    shippinda.getEditText().getText().clear();
                                    bottols.getEditText().getText().clear();
                                    bg.getEditText().getText().clear();
                                    actv1.getText().clear();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }//else
            }//onclick
        });
        //refreshing

        return view;
    }
    String aa;
    String stattus;
    @Override
    public void onStart() {
        super.onStart();
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
        } else {
            Snackbar.make(getView(), "You are not connected to the network", Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onStart();
                }
            }).show();
        }
    }
    private boolean validateEmail() {
        String email = emaili.getEditText().getText().toString().trim();
        if (email.isEmpty()) {
            emaili.setError("Field Cannot be Empty");
            return false;
        } else {
            emaili.setError(null);
            return true;
        }
    }
    private boolean validatename() {
        String na = name.getEditText().getText().toString().trim();
        if (na.isEmpty()) {
            name.setError("Field Cannot be Empty");
            return false;
        } else {
            name.setError(null);
            return true;
        }
    }
    private boolean validatmobile() {
        String no = mobile.getEditText().getText().toString().trim();
        if (no.isEmpty()) {
            mobile.setError("Field Cannot be Empty");
            return false;
        } else {
            mobile.setError(null);
            return true;
        }
    }
    private boolean validateoederid() {
        String order = orderid.getEditText().getText().toString().trim();
        if (order.isEmpty()) {
            orderid.setError("Field Cannot be Empty");
            return false;
        } else {
            orderid.setError(null);
            return true;
        }
    }
    private boolean validateaddr() {
        String addrress = addr.getEditText().getText().toString().trim();
        if (addrress.isEmpty()) {
            addr.setError("Field Cannot be Empty");
            return false;
        } else {
            addr.setError(null);
            return true;
        }
    }
    private boolean validatecity() {
        String city1 = city.getEditText().getText().toString().trim();
        if (city1.isEmpty()) {
            city.setError("Field Cannot be Empty");
            return false;
        } else {
            city.setError(null);
            return true;
        }
    }
    private boolean validateorerdate() {
        String ordedate = orderda.getEditText().getText().toString().trim();
        if (ordedate.isEmpty()) {
            orderda.setError("Field Cannot be Empty");
            return false;
        } else {
            orderda.setError(null);
            return true;
        }
    }
    private boolean validateshippingdate() {
        String shippingdate = shippinda.getEditText().getText().toString().trim();
        if (shippingdate.isEmpty()) {
            shippinda.setError("Field Cannot be Empty");
            return false;
        } else {
            shippinda.setError(null);
            return true;
        }
    }
    private boolean validatebootle() {
        String bottl = bottols.getEditText().getText().toString().trim();
        if (bottl.isEmpty()) {
            bottols.setError("Field Cannot be Empty");
            return false;
        } else {
            bottols.setError(null);
            return true;
        }
    }
    private void bloodstock()
    {
        reference=FirebaseDatabase.getInstance().getReference("bloodstock");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                a=Integer.parseInt(dataSnapshot.child("A+").getValue().toString());
                a1=Integer.parseInt(dataSnapshot.child("A-").getValue().toString());
                b=Integer.parseInt(dataSnapshot.child("B+").getValue().toString());
                b1=Integer.parseInt(dataSnapshot.child("B-").getValue().toString());
                ab=Integer.parseInt(dataSnapshot.child("AB+").getValue().toString());
                ab1=Integer.parseInt(dataSnapshot.child("AB-").getValue().toString());
                o=Integer.parseInt(dataSnapshot.child("O+").getValue().toString());
                o1=Integer.parseInt(dataSnapshot.child("O-").getValue().toString());
                Log.d("mytag", "onDataChange: "+a+a1+b+b1+ab+ab1+o+o1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
//main bracket

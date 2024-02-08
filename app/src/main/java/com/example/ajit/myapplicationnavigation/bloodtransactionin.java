package com.example.ajit.myapplicationnavigation;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import android.support.design.widget.Snackbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class bloodtransactionin extends Fragment {
    public bloodtransactionin() {
    }

    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    Spinner sp1;
    ProgressBar pb;
    int orderno;
    String  order;

    Button confirm;

    String bg,date1;
View view;
    int a,a1,b,b1,ab,ab1,o,o1;
    int bottle2;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    TextInputLayout emaili, name, mobile, addr, city, orderid, date, shippinda, bottols;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

         view=inflater.inflate(R.layout.fragment_bloodtransactionin,container,false);
        pb=view.findViewById(R.id.progressbar);
        confirm=(Button)view.findViewById(R.id.upload);

        sp1=(Spinner)view.findViewById(R.id.spinner2);
        ArrayAdapter<String> a2=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.bloodgroup));
        a2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(a2);

        orderid=view.findViewById(R.id.orderidin);
        name = view.findViewById(R.id.name);
        emaili = view.findViewById(R.id.email);
        mobile = view.findViewById(R.id.mobile);
        addr = view.findViewById(R.id.addr);
        city = view.findViewById(R.id.city);
        date = view.findViewById(R.id.date);
        bg=sp1.getSelectedItem().toString();
        bottols = view.findViewById(R.id.bottle);

        calendar=Calendar.getInstance();
        simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy");
        date1=simpleDateFormat.format(calendar.getTime());
        date.getEditText().setText(date1);

        reference=FirebaseDatabase.getInstance().getReference("Requestid");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderid.getEditText().setText(dataSnapshot.child("Orderid").getValue().toString());
                order=dataSnapshot.child("Orderid").getValue().toString();
                orderno=Integer.parseInt(order);
                return;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        bloodstock();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateEmail()  | !validatename() | !validatmobile() | !validateaddr()| !validatecity()| !validatebootle()) {
                    return;
                }else {
                    uploaddata();
                    pb.setVisibility(View.VISIBLE);
                }
            }
        });


        return view;
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

    private void uploaddata() {
        final String id, nm, email1, mo1, address1, city1, date1, blood, bottl1;

        id = orderid.getEditText().getText().toString();
        nm = name.getEditText().getText().toString();
        email1 = emaili.getEditText().getText().toString();
        mo1 = mobile.getEditText().getText().toString();
        address1 = addr.getEditText().getText().toString();
        city1 = city.getEditText().getText().toString();
        date1 = date.getEditText().getText().toString();
        blood = sp1.getSelectedItem().toString();
        bottl1 = bottols.getEditText().getText().toString();
        bottle2 = Integer.parseInt(bottl1);


        bloodtransactionsata_In insertdata = new bloodtransactionsata_In(id, nm, email1, mo1, address1, city1, date1, blood, bottl1);
        FirebaseDatabase.getInstance().getReference("BloodTransactionIn").child(id).setValue(insertdata).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    reference = FirebaseDatabase.getInstance().getReference("bloodstock");

                    switch (blood) {
                        case "A+":
                            reference.child("A+").setValue(a + bottle2);
                            break;
                        case "A-":
                            reference.child("A-").setValue(a1 + bottle2);
                            break;
                        case "B+":
                            reference.child("B+").setValue(b + bottle2);
                            break;
                        case "B-":
                            reference.child("B-").setValue(b1 + bottle2);
                            break;
                        case "AB+":
                            reference.child("AB+").setValue(ab + bottle2);
                            break;
                        case "AB-":
                            reference.child("AB-").setValue(ab1 + bottle2);
                            break;
                        case "O+":
                            reference.child("O+").setValue(o + bottle2);
                            break;
                        case "O-":
                            reference.child("O-").setValue(o1 + bottle2);
                            break;
                    }
                    reference=FirebaseDatabase.getInstance().getReference("Requestid");
                    int id2=Integer.parseInt(id);
                    reference.child("Orderid").setValue(id2+1);

                    pb.setVisibility(View.GONE);
                    orderid.getEditText().getText().clear();
                    name.getEditText().getText().clear();
                    emaili.getEditText().getText().clear();
                    mobile.getEditText().getText().clear();
                    addr.getEditText().getText().clear();
                    city.getEditText().getText().clear();
                    date.getEditText().getText().clear();

                    bottols.getEditText().getText().clear();

                    Snackbar.make(view,"transaction done successfully",Snackbar.LENGTH_LONG).show();



                } else {
                        pb.setVisibility(View.GONE);
                    Snackbar.make(view,task.getException().getMessage() + "Error Occured",Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }

    public void bloodstock()
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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


}

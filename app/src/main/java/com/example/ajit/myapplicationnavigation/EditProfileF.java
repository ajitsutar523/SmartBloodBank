package com.example.ajit.myapplicationnavigation;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
public class EditProfileF extends Fragment  {

    ProgressBar pb;
    TextInputLayout name,mobile,addr,donor,city,email12;

    Spinner sp,sp1,sp2;

    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;

    String nm,ema,mob,add,gend,city1,donor1,bloodgrp,pwd,uid;


    public EditProfileF() {
        // Required empty public constructor
    }

View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         view=inflater.inflate(R.layout.fragment_edit_profile,container,false);

        pb=(ProgressBar)view.findViewById(R.id.progressbar);

        sp=(Spinner) view.findViewById(R.id.spinner1);
        ArrayAdapter<String> aab=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.gender));
        aab.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(aab);


        sp1=(Spinner)view.findViewById(R.id.spinner2);
        ArrayAdapter<String> aa2=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.bloodgroup));
        aa2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(aa2);

        sp2=(Spinner)view.findViewById(R.id.spinner3);
        ArrayAdapter<String> a3=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.donor));
        a3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2.setAdapter(a3);

        city=view.findViewById(R.id.city);
        name=view.findViewById(R.id.name);
        mobile=view.findViewById(R.id.mobile);
        addr=view.findViewById(R.id.addr);
        Button b1=(Button) view.findViewById(R.id.update);
        email12=view.findViewById(R.id.emailid1);

        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        reference=FirebaseDatabase.getInstance().getReference("user").child(user.getUid());


        String id111=user.getUid();
        Log.d("mytag", " id"+id111);
        try {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mob=dataSnapshot.child("mobile").getValue().toString();
                nm=dataSnapshot.child("name").getValue().toString();
                ema=dataSnapshot.child("email").getValue().toString();
                gend=dataSnapshot.child("gender").getValue().toString();
                add=dataSnapshot.child("addr").getValue().toString();
                donor1=dataSnapshot.child("donor").getValue().toString();
                city1=dataSnapshot.child("city").getValue().toString();
                bloodgrp=dataSnapshot.child("bloodgroup").getValue().toString();
                pwd=dataSnapshot.child("pwd").getValue().toString();
                uid=dataSnapshot.child("id").getValue().toString();
                email12.getEditText().setText(ema);

                name.getEditText().setText(nm);
                mobile.getEditText().setText(mob);
                addr.getEditText().setText(add);
                city.getEditText().setText(city1);

                switch (gend)
                {
                    case"Male":
                        sp.setSelection(0);
                        break;
                    case"Female":
                        sp.setSelection(1);
                        break;
                    case"Other":
                        sp.setSelection(2);
                        break;
                }
                sp1.setFocusable(true);
                switch (bloodgrp)
                {
                    case"A+":
                        sp1.setSelection(0);
                        break;
                    case"A-":
                        sp1.setSelection(1);
                        break;
                    case"B+":
                        sp1.setSelection(2);
                        break;
                    case"B-":
                        sp1.setSelection(3);
                        break;
                    case"AB+":
                        sp1.setSelection(4);
                        break;
                    case"AB-":
                        sp1.setSelection(5);
                        break;
                    case"O+":
                        sp1.setSelection(6);
                        break;
                    case"O-":
                        sp1.setSelection(7);
                        break;
                }
                switch (donor1)
                {
                    case"Yes":
                        sp2.setSelection(0);
                        break;
                    case"No":
                        sp2.setSelection(1);
                        break;

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Snackbar.make(view,databaseError.getMessage(),Snackbar.LENGTH_LONG).show();
            }
        });
        }catch (Exception e)
        {
            Snackbar.make(view,e.getMessage(),Snackbar.LENGTH_LONG).show();


        }



        b1.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                if(validatename() | !validatmobile()) {


                    nm=name.getEditText().getText().toString().trim();
                    mob=mobile.getEditText().getText().toString().trim();
                    gend=sp.getSelectedItem().toString().trim();
                    bloodgrp=sp1.getSelectedItem().toString().trim();
                    add=addr.getEditText().getText().toString().trim();
                    city1=city.getEditText().getText().toString().trim();
                    donor1=sp2.getSelectedItem().toString();
                    registerdata registerdata1 = new registerdata(ema, pwd, nm, mob, donor1, add, city1, uid, gend, bloodgrp);
                    reference=FirebaseDatabase.getInstance().getReference("user").child(uid);
                    reference.setValue(registerdata1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                pb.setVisibility(View.GONE);

                                Snackbar.make(view,"Data Updated",Snackbar.LENGTH_LONG).show();
                            }
                            else
                            {
                                pb.setVisibility(View.GONE);
                                Snackbar.make(view,task.getException().getMessage(),Snackbar.LENGTH_LONG).show();

                            }

                        }
                    });





                    return;
                }



            }
        });


        return view;
    }


    private boolean validatename()
    {
        String na=name.getEditText().getText().toString().trim();
        if(na.isEmpty()){
            name.setError("Field Cannot be Empty");
            return false;
        }
        else
        {
            name.setError(null);
            return true;
        }
    }
    private boolean validatmobile()
    {
        String no=mobile.getEditText().getText().toString().trim();
        if(no.isEmpty()){
            mobile.setError("Field Cannot be Empty");
            return false;
        }
        else
        {
            mobile.setError(null);
            return true;
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
          //  Snackbar.make(getView(), "Your are Online", Snackbar.LENGTH_SHORT).show();
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




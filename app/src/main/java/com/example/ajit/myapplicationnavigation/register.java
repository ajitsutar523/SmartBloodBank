package com.example.ajit.myapplicationnavigation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class register extends AppCompatActivity implements View.OnClickListener {
    TextInputLayout emailinput,username,pwd,name,mobile,donor,addr,city;
    Spinner sp,sp1,sp2;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference database;
    private FirebaseUser user;
    ProgressBar pb;
    View view;

    public View getView() {
        return view;
    }

    String email1,username1,password1,name1,mobileno1,donor1,address1,city1,id1,gender1,bloodgrp1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Register ");

         sp=(Spinner)findViewById(R.id.spinner1);
        ArrayAdapter<String> a1=new ArrayAdapter<String>(register.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.gender));
        a1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(a1);

         sp1=(Spinner)findViewById(R.id.spinner2);
        ArrayAdapter<String> a2=new ArrayAdapter<String>(register.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.bloodgroup));
        a2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(a2);

        sp2=(Spinner)findViewById(R.id.spinner3);
        ArrayAdapter<String> a3=new ArrayAdapter<String>(register.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.donor));
        a3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2.setAdapter(a3);

        emailinput=findViewById(R.id.email);

        pwd=findViewById(R.id.pwd);
        name=findViewById(R.id.name);
        mobile=findViewById(R.id.mobile);
        addr=findViewById(R.id.addr);
        city=findViewById(R.id.city);

        pb=(ProgressBar)findViewById(R.id.progressbar);
        firebaseAuth=FirebaseAuth.getInstance();




    }
    private boolean validateEmail()
    {
        String email=emailinput.getEditText().getText().toString().trim();
        if(email.isEmpty()){
            emailinput.setError("Field Cannot be Empty");
            return false;
        }
        else
        {
            emailinput.setError(null);
            return true;
        }
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

    private boolean validatepassword()
    {
        String password=pwd.getEditText().getText().toString().trim();
        if(password.isEmpty()){
            pwd.setError("Field Cannot be Empty");
            return false;
        }
        else
        {
            pwd.setError(null);
            return true;
        }
    }
    private boolean validateaddr()
    {
        String password=addr.getEditText().getText().toString().trim();
        if(password.isEmpty()){
            addr.setError("Field Cannot be Empty");
            return false;
        }
        else
        {
            addr.setError(null);
            return true;
        }
    }
    private boolean validatecity()
    {
        String password=city.getEditText().getText().toString().trim();
        if(password.isEmpty()){
            city.setError("Field Cannot be Empty");
            return false;
        }
        else
        {
            city.setError(null);
            return true;
        }
    }


    public void confirminput(View v){

        if(!validateEmail() | !validatepassword() | !validatename() | !validatmobile()| !validateaddr() | !validatecity()) {
            return;
        }
        pb.setVisibility(View.VISIBLE);

         email1=emailinput.getEditText().getText().toString().trim();
         password1=pwd.getEditText().getText().toString().trim();
         //usern1=username.getEditText().getText().toString().trim();
         name1=name.getEditText().getText().toString().trim();
         mobileno1=mobile.getEditText().getText().toString().trim();
         gender1=sp.getSelectedItem().toString();
         bloodgrp1=sp1.getSelectedItem().toString();
         address1=addr.getEditText().getText().toString().trim();
         city1=city.getEditText().getText().toString().trim();
         donor1=sp2.getSelectedItem().toString();





        Log.d("mytag", "confirminput:bbbb ");

        firebaseAuth.createUserWithEmailAndPassword(email1,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    registerdata registerdata1 = new registerdata(email1, password1, name1, mobileno1, donor1, address1, city1, firebaseAuth.getUid().toString(), gender1, bloodgrp1);
                    FirebaseDatabase.getInstance().getReference("user").child(firebaseAuth.getUid()).setValue(registerdata1)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        pb.setVisibility(View.GONE);
                                        Toast.makeText(register.this, " register succesfull", Toast.LENGTH_SHORT).show();

                                        Intent i = new Intent(register.this, login.class);
                                        startActivity(i);

                                    } else {
                                        pb.setVisibility(View.GONE);
                                        Snackbar.make(findViewById(android.R.id.content),task.getException().getMessage(),Snackbar.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
                else
                    {
                        pb.setVisibility(View.GONE);

                        Snackbar.make(findViewById(android.R.id.content),task.getException().getMessage(),Snackbar.LENGTH_LONG).show();
                    }
            }
        });

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        ConnectivityManager connMgr = (ConnectivityManager)  getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
           // Snackbar.make(findViewById(android.R.id.content), "Your are Online", Snackbar.LENGTH_SHORT).show();
            // fetch data
        } else {
            // display error

            Snackbar.make(findViewById(android.R.id.content), "You are not connected to the network", Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onStart();
                }
            }).show();
        }
    }
}



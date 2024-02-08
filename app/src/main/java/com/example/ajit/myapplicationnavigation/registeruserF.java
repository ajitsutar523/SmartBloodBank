package com.example.ajit.myapplicationnavigation;



import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class registeruserF extends Fragment {


    public registeruserF() {
        // Required empty public constructor
    }
    View view;
    ProgressBar pb;

    ListView lv1;
    List<registeruser1> list;
    registeruseradapter registeruseradapter1;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    FirebaseUser user;
    String id1,email,pwd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_registeruser, container, false);
        pb=view.findViewById(R.id.progressbar);
        pb.setVisibility(View.VISIBLE);
        lv1 =  view.findViewById(R.id.listregister);
        list = new ArrayList<>();
        list.clear();
        load();
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                id1=view.getTag().toString();





                new AlertDialog.Builder(getContext()).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Warning")
                        .setMessage("Delete this User:")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                reference=FirebaseDatabase.getInstance().getReference("user").child(view.getTag().toString());
                                reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                        {
                                            Snackbar.make(getView(),"user deleted",Snackbar.LENGTH_LONG).show();
                                            list.clear();
                                            load();
                                        }
                                        if(task.isCanceled())
                                        {
                                            Snackbar.make(getView(),"error occured",Snackbar.LENGTH_LONG).show();
                                        }
                                    }
                                });

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                }).show();
            }
        });
        return view;
    }
    public void load()
    {
        reference=FirebaseDatabase.getInstance().getReference("user");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    pb.setVisibility(View.GONE);
                    registeruser1  register=ds.getValue(registeruser1.class);
                    String donor=register.getDonor();
                    String name=register.getName();
                    String mobile=register.getMobile();
                    String bg=register.getBloodgroup();
                    String city=register.getCity();
                    String addr=register.getAddr();
                    String email=register.getEmail();
                    String id=register.getId();
                    list.add(new registeruser1(addr,bg,city,donor,email,mobile,name,id));

                }

                registeruseradapter1=new registeruseradapter(getContext(),list);
                lv1.setAdapter(registeruseradapter1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                pb.setVisibility(View.GONE);
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

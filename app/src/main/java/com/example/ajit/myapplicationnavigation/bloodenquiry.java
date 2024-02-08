package com.example.ajit.myapplicationnavigation;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
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

import static android.content.Context.CLIPBOARD_SERVICE;

public class bloodenquiry extends Fragment {
    public bloodenquiry() {
        // Required empty public constructor
    }

    ListView lv1;

    String id123=" a";

    List<requestdataproduct> list;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    FirebaseUser user;
    String id1;
    Spinner request;
    View view;
    ProgressBar pb;
    requesdatatadapter requesdatatadapter1;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.fragment_bloodenquiry,container,false);
        lv1=(ListView)view.findViewById(R.id.lv12);
        list=new ArrayList<>();
        list.clear();
        pb=view.findViewById(R.id.progressbar);
        pb.setVisibility(View.VISIBLE);
        request=(Spinner)view.findViewById(R.id.spinnerrequest);
        ArrayAdapter<String> a1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.request));
        a1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        request.setAdapter(a1);
        reference=FirebaseDatabase.getInstance().getReference("bloodrequest");

        request.setSelection(0);
        request.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if(selectedItem.equals("Complete"))
                {
                    list.clear();
                    complete();
                  //  Toast.makeText(getContext(),"Complete",Toast.LENGTH_SHORT).show();

                }
                if(selectedItem.equals("Incomplete"))
                {
                    list.clear();
                    incomplete();
                   // Toast.makeText(getContext(),"Incomplete",Toast.LENGTH_SHORT).show();

                }
                if(selectedItem.equals("All"))
                {
                    list.clear();
                    all();
                   // Toast.makeText(getContext(),"All",Toast.LENGTH_SHORT).show();


                }
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

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


        } else {


            Snackbar.make(getView(), "You are not connected to the network", Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onStart();
                }
            }).show();
        }
    }
public void all()
{
    list.clear();

    reference=FirebaseDatabase.getInstance().getReference("bloodrequest");
    reference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


            for (DataSnapshot ds : dataSnapshot.getChildren())
            {
                String id=ds.getKey().toString();
                reference=FirebaseDatabase.getInstance().getReference("bloodrequest").child(id);
                reference.addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            requestdata request = ds.getValue(requestdata.class);


                            String a1 = request.getNm();
                            String a2 = request.getBg();
                            String a3 = request.getDate();
                            String a4 = request.getMob();
                            String a5 = request.getOrder();
                            String a6 = request.getStatus();
                            Log.d("mytag", "all "+a6);
                            list.add(new requestdataproduct(a2, a1, a3, a5, a4, a6));
                        }
                        requesdatatadapter1 = new requesdatatadapter(getContext(), list);
                        pb.setVisibility(View.GONE);
                       lv1.setAdapter(requesdatatadapter1);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Snackbar.make(view,databaseError.getMessage(),Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Snackbar.make(view,databaseError.getMessage(),Snackbar.LENGTH_LONG).show();

        }
    });
}

    public void complete()
    { list.clear();
        reference=FirebaseDatabase.getInstance().getReference("bloodrequest");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    String id=ds.getKey().toString();
                    reference=FirebaseDatabase.getInstance().getReference("bloodrequest").child(id);
                    reference.addValueEventListener(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                requestdata request = ds.getValue(requestdata.class);


                                String a1 = request.getNm();
                                String a2 = request.getBg();
                                String a3 = request.getDate();
                                String a4 = request.getMob();
                                String a5 = request.getOrder();
                                String a6 = request.getStatus();

                                if(a6.equals("Complete"))
                                {
                                    Log.d("mytag", "complete "+a6);
                                    list.add(new requestdataproduct(a2, a1, a3, a5, a4, a6));
                                }
                            }
                            requesdatatadapter1 = new requesdatatadapter(getContext(), list);
                            lv1.setAdapter(requesdatatadapter1);
                            pb.setVisibility(View.GONE);
                        //    Log.d("mytag", "complete "+lv1.getCount());
                        //    count=lv1.getCount();
                      //      Toast.makeText(getContext(), count+" Records",Toast.LENGTH_SHORT).show();

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getContext(),databaseError.getMessage(),Toast.LENGTH_LONG).show();
                        }

                    });

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),databaseError.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }
int count;
    public void incomplete()
    {
        list.clear();
       //  int count;
        reference=FirebaseDatabase.getInstance().getReference("bloodrequest");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    String id=ds.getKey().toString();
                    reference=FirebaseDatabase.getInstance().getReference("bloodrequest").child(id);
                    reference.addValueEventListener(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                requestdata request = ds.getValue(requestdata.class);


                                String a1 = request.getNm();
                                String a2 = request.getBg();
                                String a3 = request.getDate();
                                String a4 = request.getMob();
                                String a5 = request.getOrder();
                                String a6 = request.getStatus();

                                if(a6.equals("Incomplete")) {
                                    Log.d("mytag", "incomplete "+a6);
                                    list.add(new requestdataproduct(a2, a1, a3, a5, a4, a6));
                                }
                            }
                            requesdatatadapter1 = new requesdatatadapter(getContext(), list);
                            lv1.setAdapter(requesdatatadapter1);
                            pb.setVisibility(View.GONE);
                           // Log.d("mytag", "incomplete "+lv1.getCount());
                           // count=lv1.getCount();
                            //Toast.makeText(getContext(), count+" Records",Toast.LENGTH_SHORT).show();

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Snackbar.make(view,databaseError.getMessage(),Snackbar.LENGTH_LONG).show();
                        }

                    });
                }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Snackbar.make(view,databaseError.getMessage(),Snackbar.LENGTH_LONG).show();

            }
        });
    }



}


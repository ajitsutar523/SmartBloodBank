package com.example.ajit.myapplicationnavigation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class admin extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    TextView username,email;
    FirebaseUser user;
    TextView et1,et2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Blood Stock");
        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
         reference=FirebaseDatabase.getInstance().getReference().child(user.getUid());



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View navheaderview=navigationView.getHeaderView(0);
        TextView usernm=(TextView)navheaderview.findViewById(R.id.username1);
        usernm.setText("Admin");
        TextView email=(TextView)navheaderview.findViewById(R.id.emailid);
        email.setText("admin@gmail.com");

        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.adminappbar,new bloodstockadminF());
        ft.commit();
        navigationView.setCheckedItem(R.id.BloodStockadmin);





    }



    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {

            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                    .setMessage("Are you sure you want to exit?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    }).setNegativeButton("No", null).show();
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        //noinspection SimplifiableIfStatement
        if (id == R.id.about) {
          ft.replace(R.id.adminappbar,new about());
            ft.commit();
           setTitle("About");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        if (id == R.id.BloodStockadmin)
        {

            ft.replace(R.id.adminappbar,new bloodstockadminF());
            ft.commit();
            setTitle("Blood Stock");

        }
        else if (id == R.id.Requestbloodenquiry)
        {

            ft.replace(R.id.adminappbar,new bloodenquiry());
            ft.commit();
            setTitle("Blood Enquiry");
        } else if (id == R.id.bloodtransaction) {


            ft.replace(R.id.adminappbar,new bloodtransaction());
            ft.commit();
            setTitle("Blood Transaction OUT");

        }
        else if (id == R.id.bloodtransactionin) {


            ft.replace(R.id.adminappbar,new bloodtransactionin());
            ft.commit();
            setTitle("Blood Transaction IN");

        }else if (id == R.id.logoutadmin) {
            firebaseAuth.signOut();
            Intent i=new Intent(this,login.class);
            startActivity(i);
            finish();

        }else if (id == R.id.registeruser) {
            ft.replace(R.id.adminappbar,new registeruserF());
            ft.commit();
            setTitle("Register Users");

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

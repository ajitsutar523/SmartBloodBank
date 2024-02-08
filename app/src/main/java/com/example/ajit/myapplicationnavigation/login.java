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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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


public class login extends AppCompatActivity {
    Button login, acc;
    TextInputLayout email, pwd;
    private DatabaseReference database;
    private FirebaseAuth firebaseAuth;
    String email1;
    Spinner usertype;
    ProgressBar  pb;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI(currentUser);
        }
        ConnectivityManager connMgr = (ConnectivityManager)  getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
           // Snackbar.make(login, "Your are Online", Snackbar.LENGTH_SHORT).show();
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
    private void updateUI(FirebaseUser currentUser) {
        if (currentUser.getEmail().equals("admin@gmail.com")) {
            Intent i = new Intent(this, admin.class);
            startActivity(i);
        } else {
            Intent i = new Intent(this, Main2Activity.class);
            startActivity(i);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");
        login = (Button) findViewById(R.id.login);
        acc = (Button) findViewById(R.id.create);
        email = findViewById(R.id.name);
        pwd = findViewById(R.id.pwd);
        database = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        usertype = (Spinner) findViewById(R.id.spinneradmin);
        ArrayAdapter<String> a1 = new ArrayAdapter<String>(login.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.usertype));
        a1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        usertype.setAdapter(a1);
        usertype.setSelection(0);
        pb=(ProgressBar)findViewById(R.id.progressbar);
    }
    private boolean validateUsername() {
        String user = email.getEditText().getText().toString().trim();
        if (user.isEmpty()) {
            email.setError("Field Cannot be Empty");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }
    private boolean validatepassword() {
        String password = pwd.getEditText().getText().toString().trim();
        if (password.isEmpty()) {
            pwd.setError("Field Cannot be Empty");
            return false;
        } else {
            pwd.setError(null);
            return true;
        }
    }
    @Override
    public void onBackPressed() {
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
    public void confirminput(View v) {
        if (!validatepassword() | !validateUsername()) {

            return;
        }
        pb.setVisibility(View.VISIBLE);
        email1 = email.getEditText().getText().toString().trim();
        String password = pwd.getEditText().getText().toString().trim();
        String user = usertype.getSelectedItem().toString();
        if (user.equals("Admin")) {
            if (email1.equals("admin@gmail.com")) {
                firebaseAuth.signInWithEmailAndPassword(email1, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                                pb.setVisibility(View.GONE);

                            Snackbar.make(login,"Login Succesfull",Snackbar.LENGTH_LONG).show();
                            Intent i = new Intent(login.this, admin.class);
                            startActivity(i);

                        } else {
                            pb.setVisibility(View.GONE);

                            Snackbar.make(login,task.getException().getMessage(),Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
            } else {

                Snackbar.make(login,"You Are Not A Admin User",Snackbar.LENGTH_LONG).show();
                pb.setVisibility(View.GONE);
            }
        }
        if (user.equals("User")) {
            if (email1.equals("admin@gmail.com"))
            {
                Snackbar.make(login,"You Are Not A Register User",Snackbar.LENGTH_LONG).show();
                pb.setVisibility(View.GONE);
            }
            else
                {
                firebaseAuth.signInWithEmailAndPassword(email1, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            pb.setVisibility(View.GONE);
                            Toast.makeText(login.this,"Login Successfull",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(login.this, Main2Activity.class);
                            startActivity(i);
                        } else {
                            pb.setVisibility(View.GONE);
                            Snackbar.make(login,task.getException().getMessage(),Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }
    }
        public void openregister (View v)
        {
            Intent i = new Intent(this, register.class);
            startActivity(i);
        }
    }


package com.example.ventilatormonitoring.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ventilatormonitoring.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.ventilatormonitoring.User.UserRegister;

public class UserDashboard extends AppCompatActivity {
    CardView machines,reports,profile,helpfeed,settings,logout;
    FirebaseAuth auth;
    TextView welcome,username;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        welcome=findViewById(R.id.welcome);
        username=findViewById(R.id.et_username);
        machines=findViewById(R.id.machines);
        reports=findViewById(R.id.reports);
        profile=findViewById(R.id.profile);
         helpfeed= findViewById(R.id.helpfeed);
       settings=findViewById(R.id.settings);
        logout=findViewById(R.id.logout);
        auth=FirebaseAuth.getInstance();
        if(auth == null){
            Intent intent = new Intent(getApplicationContext(),UserLoginActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            String userId = auth.getCurrentUser().getUid();
            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

            usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String displayName = dataSnapshot.child("Username").getValue(String.class);
                        username.setText(displayName);
                    } else {
                        Toast.makeText(UserDashboard.this, "User data does not exist in the database", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    Toast.makeText(UserDashboard.this, "Failed to retrieve user data", Toast.LENGTH_SHORT).show();
                }
            });
        }
        machines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDashboard.this, Machines.class);
                startActivity(intent);
            }
        });
        reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDashboard.this, Reports.class);
                startActivity(intent);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDashboard.this,Profile.class);
                startActivity(intent);
            }
        });
        helpfeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDashboard.this, Helpfeedback.class);
                startActivity(intent);
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDashboard.this, Settings.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent i=new Intent(getApplicationContext(),UserLoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}



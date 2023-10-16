package com.example.ventilatormonitoring.Admin;

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

import com.example.ventilatormonitoring.Admin.AdMachines;
import com.example.ventilatormonitoring.Admin.AdProfile;
import com.example.ventilatormonitoring.Admin.Users;
import com.example.ventilatormonitoring.Admin.AdSettings;
import com.example.ventilatormonitoring.Admin.AdminLoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.ventilatormonitoring.Admin.AdminRegister;

public class AdminDashboard extends AppCompatActivity {
    CardView machines,users,profile,settings,logout;
    FirebaseAuth auth;
    TextView welcome,username;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        welcome=findViewById(R.id.welcome);

        machines=findViewById(R.id.machines);
        users=findViewById(R.id.users);
        profile=findViewById(R.id.profile);

        settings=findViewById(R.id.settings);
        logout=findViewById(R.id.logout);
        auth=FirebaseAuth.getInstance();
        if(auth == null){
            Intent intent = new Intent(getApplicationContext(), AdminLoginActivity.class);
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
                        Toast.makeText(com.example.ventilatormonitoring.Admin.AdminDashboard.this, "User data does not exist in the database", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    Toast.makeText(com.example.ventilatormonitoring.Admin.AdminDashboard.this, "Failed to retrieve user data", Toast.LENGTH_SHORT).show();
                }
            });
        }
        machines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com.example.ventilatormonitoring.Admin.AdminDashboard.this, AdMachines.class);
                startActivity(intent);
            }
        });
        users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com.example.ventilatormonitoring.Admin.AdminDashboard.this, Users.class);
                startActivity(intent);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com.example.ventilatormonitoring.Admin.AdminDashboard.this, AdProfile.class);
                startActivity(intent);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com.example.ventilatormonitoring.Admin.AdminDashboard.this, AdSettings.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent i=new Intent(getApplicationContext(),AdminLoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}


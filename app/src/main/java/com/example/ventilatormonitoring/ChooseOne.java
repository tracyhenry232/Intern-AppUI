package com.example.ventilatormonitoring;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ventilatormonitoring.Admin.AdminLoginActivity;
import com.example.ventilatormonitoring.User.UserLoginActivity;

public class ChooseOne extends AppCompatActivity {

    Button User,Admin;
    Intent intent;
    String type;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);



        User = (Button)findViewById(R.id.user);

        Admin = (Button)findViewById(R.id.admin);


        User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent loginuser  = new Intent(ChooseOne.this, UserLoginActivity.class);
                    startActivity(loginuser);
                    finish();

            }
        });

        Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent adminuser  = new Intent(ChooseOne.this, AdminLoginActivity.class);
                startActivity(adminuser);
                finish();

            }
        });


    }
}
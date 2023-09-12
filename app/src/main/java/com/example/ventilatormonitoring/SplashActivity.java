package com.example.ventilatormonitoring;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.example.ventilatormonitoring.User.UserLoginActivity;

public class SplashActivity extends AppCompatActivity {


    ImageView imageViewlogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        imageViewlogo = findViewById(R.id.logoImageview);
        imageViewlogo.setAlpha(0f);


        Intent intent = new Intent(SplashActivity.this,ChooseOne.class);



        imageViewlogo.animate().alpha(1).setDuration(3000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        },4000);

    }
}
